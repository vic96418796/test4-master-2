package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View v) {
                if (v.getId() == R.id.register) {
                    new AlertDialog.Builder(Main2Activity.this)
                            .setTitle("註冊結果")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("恭喜您註冊成功")
                            .setPositiveButton("關閉視窗", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent (Main2Activity.this,Main5Activity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }


        });
    }
}
