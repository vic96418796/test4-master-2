package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class food_leibay extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_leibay);

        final Button pass = findViewById(R.id.pass);
        if ( a == 0 ) {
            pass.setSelected(false); }
        else {
            pass.setSelected(true); }
        pass.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( a == 0 ) {
                    pass.setSelected(true);
                    a++;
                }
                else {
                    pass.setSelected(false);
                    a--;
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,  R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(food_leibay.this, FriendList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(food_leibay.this,profile.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(food_leibay.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(food_leibay.this,main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( food_leibay.this,RestaurantList.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

}
