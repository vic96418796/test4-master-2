package com.example.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FriendInformation extends AppCompatActivity {
    private static final String TAG ="Friends";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TextView friend_name;
    public TextView friend_id;
    public FirebaseAuth auth;
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private RecyclerView mMainList;
    private RestaurantListAdapter RestaurantListAdapter;
    private List<Restaurant> RestaurantList;
    private ArrayList<String> restaurantList;
    private ArrayList<String> userAll;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private Restaurant restaurant;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_information);;

        lat = new ArrayList<>();
        namelst = new ArrayList<>();
        Intent intent = this.getIntent();//取得傳遞過來的資料
        String friendId = intent.getStringExtra("FriendId");
        friend_name=(TextView)findViewById(R.id.user_name_profile);
        friend_id=(TextView)findViewById(R.id.user_id);
        Task<DocumentSnapshot> documentSnapshotTask = db.collection("Friend").document(friendId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        friend_name.setText(document.get("Friend_name").toString());
                        friend_id.setText(document.get("Friend_id").toString());
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


                    }
                }
            }
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        RestaurantList = new ArrayList<>();
        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList);
        mMainList = (RecyclerView)findViewById(R.id.recyclerView_friend_restaurant);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(RestaurantListAdapter);

        userAll = new ArrayList<>();
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                userAll.add(document.getId());
                            }
                            for(String user:userAll){
                                Log.d(TAG,"userAll: "+user);
                            }
                            int i = (int) (Math.random()*userAll.size());
                            db.collection("User/"+userAll.get(i)+"/Favorite_restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.d(TAG, "Error :" + e.getMessage());
                                    } else {
                                        restaurantList = new ArrayList<>();
                                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                                String restaurant_id = doc.getDocument().getId();
                                                Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                                                restaurantList.add(restaurant_id);
                                                RestaurantListAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        for (String restaurantId : restaurantList) {
                                            db.collection("Restaurant")
                                                    .document(restaurantId)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot doc = task.getResult();
                                                                if (doc.exists()) {
                                                                    String restaurant_id = doc.getId();
                                                                    Restaurant restaurant = doc.toObject(Restaurant.class).withId(restaurant_id);
                                                                    RestaurantList.add(restaurant);
                                                                    RestaurantListAdapter.notifyDataSetChanged();
                                                                    Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                                                                } else {
                                                                    Log.d(TAG, "No such document");
                                                                }
                                                            } else {
                                                                Log.d(TAG, "get failed with ", task.getException());
                                                            }
                                                        }


                                                    });
                                        }
                                    }
                                }
                            });


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
        final String currentUserID = auth.getCurrentUser().getUid();




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
                    Intent intent = new Intent(FriendInformation.this,profile.class);
                    intent.putExtra("user_id",userId);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(FriendInformation.this,MapsActivity.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("namelst",namelst);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(FriendInformation.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( FriendInformation.this,RestaurantList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(FriendInformation.this,FriendList.class);
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
