package com.sun.marinedunia.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sun.marinedunia.Adapters.QuizSetAdapter;
import com.sun.marinedunia.R;

public class QuizSetsActivity extends AppCompatActivity {
    private GridView gridview;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_sets);
        Toolbar toolbar = findViewById(R.id.sets_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
             loadAds();
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        gridview = findViewById(R.id.sets_gv);
        QuizSetAdapter setadapter = new QuizSetAdapter(getIntent().getIntExtra("sets",0),getIntent().getStringExtra("title"),mInterstitialAd);
        gridview.setAdapter(setadapter);



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
