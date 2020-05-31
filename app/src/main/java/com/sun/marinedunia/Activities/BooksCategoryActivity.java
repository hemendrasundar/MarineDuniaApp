package com.sun.marinedunia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sun.marinedunia.Adapters.BooksCategoryAdapter;
import com.sun.marinedunia.Interfaces.Api;
import com.sun.marinedunia.R;
import com.sun.marinedunia.Models.BookCategories;
import com.sun.marinedunia.Models.BookCategory;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sun.marinedunia.Models.BookCategory.CategorynameComparator;

public class BooksCategoryActivity extends AppCompatActivity {


    private RecyclerView rv_bookCategories;
    private Dialog loadingdialog;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_category);
        Toolbar toolbar = findViewById(R.id.books_category_toolbar);
        loadAds();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingdialog.setCancelable(false);
        rv_bookCategories = findViewById(R.id.category_books_rv);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(RecyclerView.VERTICAL);
        rv_bookCategories.setLayoutManager(layoutmanager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<BookCategories> call = api.getBookCategory();



        loadingdialog.show();
        call.enqueue(new Callback<BookCategories>() {
            @Override
            public void onResponse(Call<BookCategories> call, Response<BookCategories> response) {

                BookCategories heroList = response.body();

                final ArrayList<BookCategory> itemlist = new ArrayList<>();

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.getItems().size(); i++) {
                    itemlist.add(heroList.getItems().get(i));
                }
                     Collections.sort(itemlist,CategorynameComparator);
                final BooksCategoryAdapter adapter = new BooksCategoryAdapter(itemlist);
                rv_bookCategories.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                loadingdialog.dismiss();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<BookCategories> call, Throwable t) {
                loadingdialog.dismiss();

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
