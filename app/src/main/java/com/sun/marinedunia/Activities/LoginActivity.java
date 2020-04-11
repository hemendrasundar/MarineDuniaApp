package com.sun.marinedunia.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sun.marinedunia.Adapters.QuizSetAdapter;
import com.sun.marinedunia.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
MaterialEditText Email_et;
MaterialEditText Password_et;
Button Login_Btn;
TextView signup_text;
TextView forgotpassword;
FirebaseAuth auth;
String email;
private Dialog loadingdialog;
String password;
String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
             init();


        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), QuizStartActivity.class));
        }
        Login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email_et.getText().toString();
                password = Password_et.getText().toString();

                    Pattern pattern = Pattern.compile(regex);
                    Matcher emailmatcher = pattern.matcher(email);



                if (emailmatcher.matches() && password.length()>=6) {
                    loadingdialog.show();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), QuizStartActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "E-mail or password is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid email and passworld lenth minimum 6", Toast.LENGTH_SHORT).show();

                }
        loadingdialog.dismiss();
            }

        });

    signup_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(in);
        finish();
    }
    });

    forgotpassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent in = new Intent(getApplicationContext(),ForGotPassword.class);
           startActivity(in);
           finish();
        }
    });

    }


    public void init()
    {
        Email_et = findViewById(R.id.edtUserName);
        Password_et = findViewById(R.id.edtPassword);
        Login_Btn = findViewById(R.id.btn_login_ip);
        signup_text = findViewById(R.id.signup_text);
        forgotpassword = findViewById(R.id.forgotpassword);
        auth = FirebaseAuth.getInstance();
        loadingdialog = new Dialog(LoginActivity.this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingdialog.setCancelable(false);
    }
}
