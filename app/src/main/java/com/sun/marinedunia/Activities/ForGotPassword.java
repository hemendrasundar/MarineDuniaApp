package com.sun.marinedunia.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sun.marinedunia.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForGotPassword extends AppCompatActivity {
    MaterialEditText Email_et;
    Button Reset_Btn;
    FirebaseAuth auth;
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);
        init();
        Reset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email_et.getText().toString();
                Pattern pattern = Pattern.compile(regex);
                Matcher emailmatcher = pattern.matcher(email);
                if(emailmatcher.matches())
                {
                    auth.sendPasswordResetEmail("user@example.com")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Reset Passowrd Link sent successfully to your email",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Error while sending Reset Link....",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please provide valid email id",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void init() {
        Email_et = findViewById(R.id.resetemail);
        Reset_Btn =  findViewById(R.id.btn_reset);
        auth = FirebaseAuth.getInstance();
    }
}
