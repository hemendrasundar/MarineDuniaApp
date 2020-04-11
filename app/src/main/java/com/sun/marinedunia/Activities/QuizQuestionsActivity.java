package com.sun.marinedunia.Activities;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sun.marinedunia.R;
import com.sun.marinedunia.Models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionsActivity extends AppCompatActivity {
    private TextView Question, noindicator;
    private FloatingActionButton bookmarkbtn;
    private LinearLayout OptionsContainer;
    private Button sharebtn, nextbtn;
    private int count = 0;
    private List<QuestionModel> QuestionList;
    private int position = 0;
    private int score = 0;
    private String category;
    private int setno;
    private Dialog loadingdialog;
    private InterstitialAd mInterstitialAd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference MyRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);
        Toolbar toolbar = findViewById(R.id.questions_toolbar);
        setSupportActionBar(toolbar);
        Question = findViewById(R.id.Question_tv);
        noindicator = findViewById(R.id.no_indicator_tv);
        bookmarkbtn = findViewById(R.id.bookmark_fab);
        OptionsContainer = findViewById(R.id.answer_ll);
        sharebtn = findViewById(R.id.share_btn);
        nextbtn = findViewById(R.id.next_btn);
        category = getIntent().getStringExtra("category");
        setno = getIntent().getIntExtra("setno", 1);
        loadAds(); //admob
        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));

        loadingdialog.setCancelable(false);
        QuestionList = new ArrayList<>();
        loadingdialog.show();
            MyRef.child("Sets").child(category).child("Questions").orderByChild("setno").equalTo(setno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        QuestionList.add(dataSnapshot1.getValue(QuestionModel.class));

                    }
                    if(QuestionList.size()>0)
                    {
                        for (int i = 0; i < 4; i++) {
                            OptionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(((Button) v));

                                }
                            });

                        }
                        playanim(Question,0,QuestionList.get(position).getQuestion());
                        nextbtn.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick (View v){
                                nextbtn.setEnabled(false);
                                nextbtn.setAlpha(0.7f);
                                EnableOption(true);
                                position++;
                                if (position == QuestionList.size()) {
                                    if(mInterstitialAd.isLoaded())
                                    {
                                        mInterstitialAd.show();
                                        return;
                                    }
                                    Intent scoreintent = new Intent(QuizQuestionsActivity.this,ScoreActivity.class);
                                    scoreintent.putExtra("score",score);
                                    scoreintent.putExtra("total",QuestionList.size());
                                    startActivity(scoreintent);
                                    finish();

                                    return;
                                }
                                count = 0;
                                playanim(Question, 0, QuestionList.get(position).getQuestion());
                            }
                        });

                    }else{
                        finish();
                        Toast.makeText(QuizQuestionsActivity.this,"NO Questions",Toast.LENGTH_SHORT).show();
                    }
                    loadingdialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(QuizQuestionsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT);
                    loadingdialog.dismiss();
                    finish();
                }
            });




}

    private void playanim(final View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = QuestionList.get(position).getOptionA();
                    } else if (count == 1) {
                        option = QuestionList.get(position).getOptionB();
                    } else if (count == 2) {
                        option = QuestionList.get(position).getOptionC();
                    } else if (count == 3) {
                        option = QuestionList.get(position).getOptionD();
                    }

                    playanim(OptionsContainer.getChildAt(count), 0, option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //data change

                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        noindicator.setText(position + 1 + "/" + QuestionList.size());
                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playanim(view, 1, data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button SelectedOption) {
        EnableOption(false);
        nextbtn.setEnabled(true);
        nextbtn.setAlpha(1);
        if (SelectedOption.getText().toString().equals(QuestionList.get(position).getCorrectAns())) {
            //correct
            SelectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            score++;
        } else {
            //incorrect
            SelectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption = (Button) OptionsContainer.findViewWithTag(QuestionList.get(position).getCorrectAns());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }

    }

    private void EnableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            OptionsContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                OptionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

            }
        }
    }
    private void loadAds() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Intent scoreintent = new Intent(QuizQuestionsActivity.this,ScoreActivity.class);
                scoreintent.putExtra("score",score);
                scoreintent.putExtra("total",QuestionList.size());
                startActivity(scoreintent);
                finish();
            }
        });

    }
}
