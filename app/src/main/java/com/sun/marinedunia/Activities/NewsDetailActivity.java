package com.sun.marinedunia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sun.marinedunia.R;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private ImageView imageView;
    private TextView title;
    private boolean isHideTolbarView = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mUrl, mImg, mTitle, mDate, mSource, mAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById(R.id.toolbar_news_detail);
       // getSupportActionBar().setTitle("");
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbarLayout =  findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
     //   appbar_title = findViewById(R.id.title_on_appbar);
      //  appbar_subtitle = findViewById(R.id.subtitle_on_appbar);
        appBarLayout =findViewById(R.id.appbar);
        imageView = findViewById(R.id.backdrop);
        appBarLayout.addOnOffsetChangedListener(this);
         title = findViewById(R.id.title_news);

        Intent intent = getIntent();
        mUrl =intent.getStringExtra("newsurl");
        mTitle = intent.getStringExtra("title");
        mImg = intent.getStringExtra("imageurl");


        Glide.with(this)
                .load(mImg)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
       // appbar_subtitle.setText(mUrl);
        title.setText(mTitle);
        initWebView(mUrl);

    }

    private void initWebView(String url)
    {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticaloffset) {
         int maxScroll = appBarLayout.getTotalScrollRange();
         float percentage = (float) Math.abs(verticaloffset) /(float) maxScroll;

         if(percentage == 1f && isHideTolbarView)
         {
             titleAppbar.setVisibility(View.VISIBLE);
             isHideTolbarView = !isHideTolbarView;
         }
         else if(percentage < 1f && isHideTolbarView)
         {
             titleAppbar.setVisibility(View.VISIBLE);
             isHideTolbarView = !isHideTolbarView;
         }
    }
}
