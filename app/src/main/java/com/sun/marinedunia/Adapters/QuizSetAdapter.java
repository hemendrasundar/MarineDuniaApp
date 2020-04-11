package com.sun.marinedunia.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sun.marinedunia.Activities.QuizQuestionsActivity;
import com.sun.marinedunia.R;

public class QuizSetAdapter extends BaseAdapter {
    private int sets;
    private String category;
    private InterstitialAd mInterstitialAd;

    public QuizSetAdapter(int sets, String category, InterstitialAd mInterstitialAd) {

        this.sets = sets;
        this.category = category;
        this.mInterstitialAd=mInterstitialAd;

    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
       View view;
       if(convertView == null)
       {
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_gv_item,parent,false);

       }
       else{
           view = convertView;
       }
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               mInterstitialAd.setAdListener(new AdListener(){
                   @Override
                   public void onAdClosed() {
                       super.onAdClosed();
                       mInterstitialAd.loadAd(new AdRequest.Builder().build());
                       Intent questionIntent = new Intent(parent.getContext(), QuizQuestionsActivity.class);
                       questionIntent.putExtra("category",category);
                       questionIntent.putExtra("setno",position+1);
                       parent.getContext().startActivity(questionIntent);
                   }
               });
               if(mInterstitialAd.isLoaded()){
                   mInterstitialAd.show();
                   return;
               }
               Intent questionIntent = new Intent(parent.getContext(), QuizQuestionsActivity.class);
               questionIntent.putExtra("category",category);
               questionIntent.putExtra("setno",position+1);
               parent.getContext().startActivity(questionIntent);
           }
       });
        ((TextView)view.findViewById(R.id.set_tv)).setText(String.valueOf(position+1));
        return view;
    }
}
