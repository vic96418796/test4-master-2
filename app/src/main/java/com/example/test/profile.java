package com.example.test;



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

import android.content.Intent;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class profile extends AppCompatActivity implements set_profile.set_profileListener{
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private TextView textViewUsername;
    public FirebaseAuth auth;
    public FirebaseFirestore db;
    private TextView user_email;
    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,  R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(profile.this, FriendList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(profile.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(profile.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( profile.this,RestaurantList.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        final Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(profile.this)
                        .setIcon(R.mipmap.ic_launcher)
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
        textViewUsername = (TextView) findViewById(R.id.user_name_profile);
        Button set = findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
    }
    public void openDialog() {
        set_profile set_profile = new set_profile();
        set_profile.show(getSupportFragmentManager(),"set_profile");

    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.isDrawerOpen(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public void applyTexts(String user_name) {
        textViewUsername.setText(user_name);

    }
}
