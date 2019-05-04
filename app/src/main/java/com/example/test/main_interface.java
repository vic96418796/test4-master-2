package com.example.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class main_interface extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_interface);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,  R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ImageButton account = findViewById(R.id.account);
        ImageButton food4 = findViewById(R.id.food4);
        ImageButton map = findViewById(R.id.map);



        food4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_interface.this, food_leibay.class);
                startActivity(intent);
            }
        });
        Button search = findViewById(R.id.search);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (main_interface.this, profile.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_interface.this, search_label.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (main_interface.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_add) {
                    Intent intent = new Intent(main_interface.this,friend_list.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.isDrawerOpen(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(main_interface.this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("確認視窗")
                    .setMessage("確定要登出嗎?")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(main_interface.this, email_login.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
        return true;

    }

}
