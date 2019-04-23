package com.example.test;

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
import android.content.Intent;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class Main7Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawer(GravityCompat.START);

                int id = menuItem.getItemId();

                if ( id == R.id.try1 ) {
                    Toast.makeText(Main7Activity.this, "try1", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if ( id == R.id.try2 ) {
                    Toast.makeText(Main7Activity.this,"try2", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if ( id == R.id.try3 ) {
                    Toast.makeText(Main7Activity.this,"try3", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        ImageButton collection = findViewById(R.id.collection);
        Button logout = findViewById(R.id.logout);

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Main7Activity.this,Main3Activity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( Main7Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
