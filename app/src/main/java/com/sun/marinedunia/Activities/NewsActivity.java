package com.sun.marinedunia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sun.marinedunia.Adapters.BooksAdapter;
import com.sun.marinedunia.Adapters.NewsAdapter;
import com.sun.marinedunia.Interfaces.Api;
import com.sun.marinedunia.Models.EBooks;
import com.sun.marinedunia.Models.Ebook;
import com.sun.marinedunia.Models.News;
import com.sun.marinedunia.Models.NewsItem;
import com.sun.marinedunia.R;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView rv_news;
    private Dialog loadingdialog;
    private InterstitialAd mInterstitialAd;
    RecyclerView.LayoutManager layoutManager;
    NewsAdapter adapter;
    ArrayList<NewsItem> itemlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

      //  loadAds();

        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingdialog.setCancelable(false);
        rv_news = findViewById(R.id.rv_news);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(RecyclerView.VERTICAL);
        rv_news.setLayoutManager(layoutmanager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<News> call = api.getNews();
        loadingdialog.show();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                News heroList = response.body();



                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.getItems().size(); i++) {

                    itemlist.add(heroList.getItems().get(i));

                }
                 Collections.reverse(itemlist);
                adapter = new NewsAdapter(itemlist, NewsActivity.this);
                layoutManager = new LinearLayoutManager(getApplicationContext());
//                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv_news.setLayoutManager(layoutManager);
                rv_news.setItemAnimator(new DefaultItemAnimator());
                rv_news.setNestedScrollingEnabled(false);
                rv_news.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                initListner();
                loadingdialog.dismiss();

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

    private void initListner()
    {
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListner() {
                                           @Override
                                           public void onItemClick(View view, int position) {
                                          Intent intent = new Intent(NewsActivity.this,NewsDetailActivity.class);
                                          NewsItem newsItem = itemlist.get(position);
                                          intent.putExtra("newsurl",newsItem.getNewsurl());
                                               intent.putExtra("title",newsItem.getTitle());
                                               intent.putExtra("imageurl",newsItem.getImageurl());
                                               startActivity(intent);

                                           }
                                       }

        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void loadAds() {
//        AdView mAdView = findViewById(R.id.adView_news);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }
}