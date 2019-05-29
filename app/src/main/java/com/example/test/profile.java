package com.example.test;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.content.Intent;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class profile extends AppCompatActivity implements set_profile.set_profileListener{
    private static final String TAG ="Users";
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private TextView textViewUsername;
    public FirebaseAuth auth;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView user_email;
    private ImageView user_image;
    private String userId;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private ArrayList<String> num;
    private ArrayList<Double>lat1;
    private ArrayList<String>lat5;
    private Restaurant restaurant;
    private ArrayList<String>lat11;
    private ArrayList<Double>lat2;
    private ArrayList<String>clat;
    private ArrayList<Double>lat3;
    private ArrayList<String>useridd;
    private ArrayList<String>userid2;
    private ArrayList<String>record;
    private ArrayList<String>restaurant2;
    private ArrayList<String>friendlist2;
    private ArrayList<String>restaurantList1;
    private ArrayList<Double>lat4;
    ArrayList<String>friendlist1;
    private ArrayList<Double> latc;
    private ArrayList<String> restaurantList;
    private List<Restaurant> RestaurantList;
    private RestaurantListAdapter RestaurantListAdapter;
    ArrayList<Double>lat6;
    ArrayList<String>namelst12;
    ArrayList<String>namelst123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        auth = FirebaseAuth.getInstance();
        lat4 = new ArrayList<>();
        lat = new ArrayList<>();
        lat5 = new ArrayList<>();
        namelst = new ArrayList<>();
        num = new ArrayList<>();
        lat1 = new ArrayList<>();
        lat11 = new ArrayList<>();
        restaurant2 = new ArrayList<>();
        clat = new ArrayList<>();
        lat2 = new ArrayList<>();
        friendlist1 = new ArrayList<>();
        useridd = new ArrayList<>();
        userid2 = new ArrayList<>();
        record = new ArrayList<>();
        friendlist2 = new ArrayList<>();
        namelst = new ArrayList<>();
        num = new ArrayList<>();
        restaurantList1 = new ArrayList<>();
        lat1 = new ArrayList<>();
        lat3 = new ArrayList<>();
        latc = new ArrayList<>();
        lat6 = new ArrayList<>();
        namelst12 = new ArrayList<>();
        namelst123 = new ArrayList<>();
//        設定USER_ID
        Intent intent = this.getIntent();
//        final String userId = intent.getStringExtra("user_id");
        userId = auth.getCurrentUser().getUid();
        Log.d(TAG,"userId: "+userId);
        Task<DocumentSnapshot> documentSnapshotTask = db.collection("User").document(userId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_email.setText(document.get("User_id").toString());
                        String image = document.get("User_image").toString();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference picReference = storageReference.child("User/"+image);
                        Glide.with(user_image.getContext())
                                .using(new FirebaseImageLoader())
                                .load(picReference)
                                .into(user_image);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        db.collection("Restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        restaurant = documentSnapshot.toObject(Restaurant.class);
                        lat.add(restaurant.getRestaurant_lat());
                        lat.add(restaurant.getRestaurant_long());
                        namelst.add(restaurant.getRestaurant_name());
                        num.add(restaurant.getRestaurant_phone());
                    }
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
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(profile.this, FriendList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(profile.this,MapsActivity.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("namelst",namelst);
                    intent.putExtra("num",num);
                    intent.putExtra("lat1",lat1);
                    intent.putExtra("restaurantList1",restaurantList1);
                    intent.putExtra("clat",clat);
                    intent.putExtra("lat3",lat3);
                    intent.putExtra("lat4",lat4);
                    intent.putExtra("latc",latc);
                    intent.putExtra("lat6",lat6);
                    intent.putExtra("namelst12",namelst12);
                    intent.putExtra("namelst123",namelst123);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(profile.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( profile.this,edit_text.class);
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
                                    Intent intent = new Intent (profile.this, login_interface.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
        textViewUsername = (TextView) findViewById(R.id.user_name_profile);
        user_email = (TextView) findViewById(R.id.user_id);
        user_image = (ImageView) findViewById(R.id.user_image);
        Button set = findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        //get User Info
        DocumentReference docRef = db.collection("User").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String user_name = document.getString("user_name");
                        textViewUsername.setText(user_name);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
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
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("user_name", user_name);


// Add a new document with a generated ID
        db.collection("User").document(userId)
                .update(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"test");
                    }
                });


    }
}
