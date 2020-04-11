package com.sun.marinedunia.Activities;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sun.marinedunia.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    MaterialEditText Email;
    MaterialEditText Password;
    Button SignUp_Btn;
    TextView login_text;
    FirebaseAuth auth;
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    private Dialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        SignUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();
                Pattern pattern = Pattern.compile(regex);
                Matcher emailmatcher = pattern.matcher(email);

                if(emailmatcher.matches() && password.length()>=6) {
                   loadingdialog.show();
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                       } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(SignUpActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                        }
                                       else{
                                        Toast.makeText(getApplicationContext(), "E-mail or password is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                       loadingdialog.dismiss();
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter valid email and passworld lenth minimum 6",Toast.LENGTH_SHORT).show();

                }

                }



            });
        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }


    public void init()
    {
        Email = findViewById(R.id.edtemail);
        Password = findViewById(R.id.edtNewPassword);
        SignUp_Btn = findViewById(R.id.btn_signup);
        login_text = findViewById(R.id.login_text);
        auth = FirebaseAuth.getInstance();
        loadingdialog = new Dialog(SignUpActivity.this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingdialog.setCancelable(false);
    }




}
