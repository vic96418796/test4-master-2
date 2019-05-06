package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class RestaurantInformation extends AppCompatActivity {

    private static final String TAG ="Products";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TextView restaurantName;
    public TextView restaurantAdd;
    public TextView restaurantPhone;
    public TextView restaurantTime;
    public ImageView restaurantImage;
    public Button restaurantFB;
    public Button restaurantIG;
    public Button restaurantGOOGLE;


    private DrawerLayout drawer;
    private NavigationView navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information);

        Intent intent = this.getIntent();//取得傳遞過來的資料
        String restaurantId = intent.getStringExtra("RestaurantId");

        restaurantName=(TextView)findViewById(R.id.restaurant_name);
        restaurantAdd=(TextView)findViewById(R.id.restaurant_add);
        restaurantPhone=(TextView) findViewById(R.id.restaurant_phone);
        restaurantImage=(ImageView) findViewById(R.id.restaurant_image);
        restaurantTime=(TextView) findViewById(R.id.restaurant_time);
        restaurantFB=(Button) findViewById(R.id.restaurant_fb);
        restaurantIG=(Button) findViewById(R.id.restaurant_ig);
        restaurantGOOGLE=(Button) findViewById(R.id.restaurant_google);


        Task<DocumentSnapshot> documentSnapshotTask = db.collection("Restaurant").document(restaurantId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        restaurantName.setText(document.get("Restaurant_id").toString());
                        restaurantAdd.setText(document.get("Restaurant_add").toString());
                        restaurantPhone.setText(document.get("Restaurant_phone").toString());
                        restaurantTime.setText(document.get("Restaurant_time").toString());
                        String image = document.get("Restaurant_image").toString();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference picReference = storageReference.child("Restaurant/"+image);
                        Glide.with(restaurantImage.getContext())
                                .using(new FirebaseImageLoader())
                                .load(picReference)
                                .into(restaurantImage);

                        restaurantFB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse("https://www.facebook.com/leibaycafe/?__tn__=%2Cd%2CP-R&eid=ARDEUskerrvj-PzoEFgBM5RKZ0Rv8GMqwHHdcwvxbGKFLyX82WpqMoZwO_ytypq_T6z2S73_vDD7F6xt");
                                Intent web = new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(web);
                            }
                        });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(RestaurantInformation.this,profile.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(RestaurantInformation.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(RestaurantInformation.this,main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( RestaurantInformation.this,RestaurantList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(RestaurantInformation.this,FriendList.class);
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
}
