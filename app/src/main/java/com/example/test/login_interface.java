package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login_interface extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_interface);
        Button email_register = findViewById(R.id.email_register);
        Button sign_in = findViewById(R.id.sign_in);
        Button sign_in_fb = findViewById(R.id.sign_in_fb);
        email_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_interface.this, com.example.test.email_register.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_interface.this,email_login.class);
                startActivity(intent);
            }
        });
        sign_in_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_interface.this, fb_login.class);
                startActivity(intent);
            }
        });
    }
}
