package com.sun.marinedunia.Adapters;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sun.marinedunia.Activities.QuizQuestionsActivity;
import com.sun.marinedunia.Activities.ScoreActivity;
import com.sun.marinedunia.BuildConfig;
import com.sun.marinedunia.R;
import com.sun.marinedunia.Models.Ebook;
import com.sun.marinedunia.Utils.FileDownloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.viewholder> {
    private List<Ebook> categoryModelList;
     Context context;
    private ProgressDialog progressDialog;
    int DOWNLOAD_NOTIFICATION_ID = 232;
    boolean isDownloading = false;

    public BooksAdapter(List<Ebook> categoryModelList, Context context) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books_item, viewGroup, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int position) {
        viewholder.setData(categoryModelList.get(position).getThumbnail(), categoryModelList.get(position).getItemName(), categoryModelList.get(position).getBrand(), position,context);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();


    }

    class viewholder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView title;
        private ImageView download_iv;
        final InterstitialAd mInterstitialAd;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.books_circle_iv);
            title = itemView.findViewById(R.id.books_title_tv);
            download_iv = itemView.findViewById(R.id.download_icn);
             mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitialAd));

        }

        private void setData(final String url, final String title, final String fileUrl, final int position,Context context) {
            Glide.with(itemView.getContext()).load(url).placeholder(R.drawable.ic_picture_as_pdf_black_24dp).into(imageView);
            this.title.setText(title);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    startDownload(fileUrl,position,title);
                }
            });
            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/MarineDunia/" + categoryModelList.get(position).getItemName());
            if (pdfFile.exists()) {
                this.download_iv.setImageResource(R.drawable.ic_eye);
                this.download_iv.setTag("view");

            } else {
                this.download_iv.setImageResource(R.drawable.ic_multimedia_option);
                this.download_iv.setTag("download");
            }
            this.download_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (download_iv.getTag().toString().equals("download")) {

                        if(mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }else{
                            startDownload(fileUrl,position,title);
                        }


                    }
                    if(download_iv.getTag().toString().equals("view"))
                    {
                        OpenPDF(position,title);


                    }
                    //Download Functonality
                }
            });
        }
    }

    private void startDownload(final String fileUrl, final int position,final String Title) {

       File pdfFile = new File(Environment.getExternalStorageDirectory() + "/MarineDunia/" + Title);
       if (pdfFile.exists()) {
           Toast.makeText(context,"Already Downloaded.....",Toast.LENGTH_LONG).show();
       }
       else {
           new DownloadFile().execute(fileUrl, Title);
           notifyDataSetChanged();
        }

    }




    private void OpenPDF(int position,String Title)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/MarineDunia/" + Title);
        if (pdfFile.exists()) {

            try {
                Uri path = Uri.fromFile(pdfFile);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    intent.setDataAndType(path, "application/pdf");
                } else {
                    Uri uri = Uri.parse(String.valueOf(pdfFile));
                    File file = new File(uri.getPath());
                    if (file.exists()) {
                        //File file1 = new File(getActivity().getFilesDir() + File.separator + "PDF" + File.separator + "eFSIS" + File.separator + "InspectionOrder.pdf");

                        uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                intent = Intent.createChooser(intent, "Open With");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
            }
        }else{


            Toast.makeText(context,"Please Download File First.....",Toast.LENGTH_LONG).show();
        }
    }

private class DownloadFile extends AsyncTask<String, Void, Void> {
    int progress = 0;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    int id = 10;
    File pdfFile;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        progressDialog.show();




    }


    @Override
    protected Void doInBackground(String... strings) {
        String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String fileName = strings[1];  // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "MarineDunia");
        if(!folder.exists()) {
            folder.mkdir();
        }
        pdfFile = new File(folder, fileName);

        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        FileDownloader.downloadFile(fileUrl, pdfFile);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try{
            if(pdfFile!= null) {

                progressDialog.dismiss();
                Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
            }

            else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);



            }
        } catch (Exception e) {
            e.printStackTrace();

            //Change button text if exception occurs

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000);


        }


        super.onPostExecute(result);
    }
}




    }

