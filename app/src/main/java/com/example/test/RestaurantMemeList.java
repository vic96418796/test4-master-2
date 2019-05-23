package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantMemeList extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private static final String TAG = "RestaurantMemeList";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private RestaurantMemeListAdapter RestaurantMemeListAdapter;
    private RestaurantMeme RestaurantMeme;
    private List<RestaurantMeme> RestaurantMemeList;
    private FirebaseAuth auth;
    private String userId;
    private ArrayList<String> restaurantMemeList;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private ArrayList<String> num;
    private ArrayList<Double> lat1;
    private Restaurant restaurant;
    private String restaurantId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_meme_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        userEmail = auth.getCurrentUser().getEmail();
        RestaurantMemeList = new ArrayList<>();
        lat = new ArrayList<>();
        namelst = new ArrayList<>();
        num = new ArrayList<>();
        lat1 = new ArrayList<>();
        Intent intent = getIntent();
        restaurantId = intent.getStringExtra("restaurantId");
        Log.d(TAG, "restaurantId: " + restaurantId);


        RestaurantMemeListAdapter = new RestaurantMemeListAdapter(getApplicationContext(), RestaurantMemeList);
        mMainList = (RecyclerView) findViewById(R.id.recyclerView_restaurant_meme);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(RestaurantMemeListAdapter);


        db.collection("Restaurant")
                .document(restaurantId)
                .collection("Meme")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String restaurant_id = document.getId();
                                RestaurantMeme restaurantmeme = document.toObject(RestaurantMeme.class).withId(restaurant_id);
                                RestaurantMemeList.add(restaurantmeme);
                                Log.d(TAG, "restaurantMeme: " + restaurantmeme.getRestaurant_meme());
                                RestaurantMemeListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
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
                    Intent intent = new Intent(RestaurantMemeList.this, FriendList.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(RestaurantMemeList.this, profile.class);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(RestaurantMemeList.this, MapsActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("namelst", namelst);
                    intent.putExtra("num", num);
                    intent.putExtra("lat1", lat1);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent(RestaurantMemeList.this, RestaurantList.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        FloatingActionButton add_comment = findViewById(R.id.add_comment);
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openDialog();
                dialog();
            }
        });

    }

    private void dialog() {
        LayoutInflater inflater = LayoutInflater.from(RestaurantMemeList.this);
        final View yourCustomView = inflater.inflate(R.layout.dialog_meme, null);

        final TextView etName = (EditText) yourCustomView.findViewById(R.id.edit_restaurant_meme);
        AlertDialog dialog = new AlertDialog.Builder(RestaurantMemeList.this)
                .setTitle("新增評論")
                .setView(yourCustomView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String comment = etName.getText().toString();
                        Log.d(TAG, "comment: " + comment);
                        Map<String, Object> Restaurant = new HashMap<>();
                        Restaurant.put("User_id", userEmail);
                        Restaurant.put("Restaurant_meme", comment);
                        db.collection("Restaurant")
                                .document(restaurantId)
                                .collection("Meme")
                                .add(Restaurant)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Log.d(TAG,"setDataFinish");
                                        setData();
                                    }
                                });



                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    private void setData() {

        int adapterSize = RestaurantMemeList.size();
        RestaurantMemeList.clear();
        RestaurantMemeListAdapter.notifyItemRangeRemoved(0,adapterSize);

        db.collection("Restaurant")
                .document(restaurantId)
                .collection("Meme")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String restaurant_id = document.getId();
                                RestaurantMeme restaurantmeme = document.toObject(RestaurantMeme.class).withId(restaurant_id);
                                RestaurantMemeList.add(restaurantmeme);
                                Log.d(TAG, "restaurantMeme: " + restaurantmeme.getRestaurant_meme());
                                RestaurantMemeListAdapter.notifyDataSetChanged();
                            }
//                            int size = RestaurantMemeList.size();
//                            RestaurantMemeListAdapter.notifyItemRangeInserted(0,size);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void openDialog() {
        add_dialog_meme add_dialog_meme = new add_dialog_meme();
        add_dialog_meme.show(getSupportFragmentManager(), "add_dialog_meme");

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.isDrawerOpen(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
