package com.sun.marinedunia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sun.marinedunia.R;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {
private TextView obtained_tv,total_tv;
private Button Done_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        obtained_tv = findViewById(R.id.obtained_score_tv);
        total_tv=findViewById(R.id.total_tv);
        Done_btn = findViewById(R.id.done_btn);
        loadAds(); //admob
        obtained_tv.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total_tv.setText("OUT OF "+String.valueOf(getIntent().getIntExtra("total",0)));

        Done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
