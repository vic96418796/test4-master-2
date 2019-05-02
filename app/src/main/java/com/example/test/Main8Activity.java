package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        Button leibay_google = findViewById(R.id.leibay_google);
        Button leibay_ig = findViewById(R.id.leibay_ig);
        Button leibay_fb = findViewById(R.id.leibay_fb);

        leibay_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.walkerland.com.tw/article/view/147835");
                Intent web = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(web);
            }
        });
        leibay_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/leibaycafe/?__tn__=%2Cd%2CP-R&eid=ARDEUskerrvj-PzoEFgBM5RKZ0Rv8GMqwHHdcwvxbGKFLyX82WpqMoZwO_ytypq_T6z2S73_vDD7F6xt");
                Intent web = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(web);
            }
        });
        leibay_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/explore/locations/288833261519250/leibay-cafe/?hl=zh-tw");
                Intent web = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(web);
            }
        });
    }
}
