package com.sun.marinedunia.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.sun.marinedunia.Adapters.BooksAdapter;
import com.sun.marinedunia.Interfaces.Api;
import com.sun.marinedunia.Models.EBooks;
import com.sun.marinedunia.Models.Ebook;
import com.sun.marinedunia.Models.News;
import com.sun.marinedunia.Models.NewsItem;
import com.sun.marinedunia.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizStartActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private Button startBtn;
    private Button EbooksBtn;
    private ImageView more_menu;
    private Button NewsBtn;
    private Dialog loadingdialog;
    FirebaseAuth auth;
    private TextView signout;
    private  SliderLayout sliderLayout;
    public static HashMap<String,String> url_maps;
    News newslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        MobileAds.initialize(this); //admob
        loadAds();

        url_maps = new HashMap<String, String>();
        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingdialog.setCancelable(false);
        startBtn = findViewById(R.id.start_btnn);
        EbooksBtn = findViewById(R.id.ebooks_btn);
        sliderLayout = (SliderLayout) findViewById(R.id.sliderLayout);
        NewsBtn = findViewById(R.id.News_btn);
        more_menu = findViewById(R.id.iv_menu);
        auth = FirebaseAuth.getInstance();
        SliderImage();

        NewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(QuizStartActivity.this, NewsActivity.class);
                startActivity(categoryIntent);
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(QuizStartActivity.this, CategoryActivity.class);
                startActivity(categoryIntent);
            }
        });
        EbooksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(QuizStartActivity.this,BooksCategoryActivity.class);
                startActivity(in);
            }
        });

        more_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent in = new Intent(getApplicationContext(),MoreActivity.class);
             startActivity(in);
             }
        });
    }

    private void SliderImage() {

        loadingdialog.show();
        loadingdialog.setCanceledOnTouchOutside(false);
        final ArrayList arraylist = new ArrayList<HashMap<String, String>>();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<News> call = api.getNews();
       // loadingdialog.show();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                try {
                    Log.d("JSON", response.toString());
                    loadingdialog.dismiss();
                    newslist = response.body();
                    Collections.reverse(newslist.getItems());
                    int size;
                    if(newslist.getItems().size()>=5)
                    {
                        size =5;
                    }
                    else {
                        size =newslist.getItems().size();
                    }
                    for (int i = 0; i < size; i++) {
                      final String  newsurl =newslist.getItems().get(i).getNewsurl();
                     final String  imgurl =newslist.getItems().get(i).getImageurl();
                     final String  Title =newslist.getItems().get(i).getTitle();

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(newslist.getItems().get(i).getTitle(), newslist.getItems().get(i).getImageurl());
                        arraylist.add(map);
                        for (String name : map.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(QuizStartActivity.this);
                            textSliderView
                                    .description(name)
                                    .image(map.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(
                                    new BaseSliderView.OnSliderClickListener() {
                                        @Override
                                        public void onSliderClick(BaseSliderView slider) {
                                            Intent intent = new Intent(QuizStartActivity.this,NewsDetailActivity.class);
                                            intent.putExtra("newsurl",newsurl);
                                            intent.putExtra("title",Title);
                                            intent.putExtra("imageurl",imgurl);
                                            startActivity(intent);
                                        }
                                    }
                            );


                            sliderLayout.addSlider(textSliderView);
                        }
                        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        sliderLayout.setCustomAnimation(new DescriptionAnimation());
                        sliderLayout.setDuration(4000);
                    }



                } catch (Exception e) {
                Log.d("Tag", e.getMessage());

            }

               //loadingdialog.dismiss();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                loadingdialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }


}
