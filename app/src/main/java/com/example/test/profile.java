package com.example.test;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class profile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);







        ImageButton collection = findViewById(R.id.collection);
        final Button logout = findViewById(R.id.logout);
        ImageButton map = findViewById(R.id.map);

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (profile.this, main_interface.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(profile.this)
                        .setTitle("登出")
                        .setMessage("確定要登出嗎?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (logout.callOnClick()){
                                    Intent intent = new Intent (profile.this, email_login.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (profile.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
