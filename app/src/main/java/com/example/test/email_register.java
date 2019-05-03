package com.example.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.support.v7.app.AlertDialog;

public class email_register extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_register);
        Button register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();


    }

    public void createUser1(View v) {
        final String email1 = ((EditText)findViewById(R.id.edit_mail))
                .getText().toString();
        final String password1 = ((EditText)findViewById(R.id.edit_pw))
                .getText().toString();
        auth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String message =
                                        task.isComplete() ? "註冊成功" : "註冊失敗";
                                new AlertDialog.Builder(email_register.this)
                                        .setMessage(message)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setPositiveButton("OK", null)
                                        .show();
                                Intent intent = new Intent(email_register.this,email_login.class);
                                startActivity(intent);
                            }
                        });
    }
}
