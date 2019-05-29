package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class edit_text extends AppCompatActivity {
    private final String TAG = "edit_text";
    DatabaseReference ref;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    private List<Restaurant> RestaurantList;
    private RestaurantListAdapter RestaurantListAdapter;
    ArrayList<Label> label;
    ArrayList<Restaurant> list;
    SearchView searchView;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private ArrayList<String> userAll;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private ArrayList<String> num;
    private ArrayList<Double> lat1;
    private ArrayList<String> lat5;
    private ArrayList<Double>lat6;
    private Restaurant restaurant;
    private String userId;
    private ArrayList<String> lat11;
    private ArrayList<Double> lat2;
    private ArrayList<String> clat;
    private ArrayList<Double> lat3;
    private ArrayList<String> useridd;
    private ArrayList<String> userid2;
    private ArrayList<String> record;
    private ArrayList<String> restaurant2;
    private ArrayList<String> friendlist2;
    private ArrayList<String> restaurantList1;
    private ArrayList<Double> lat4;
    private ArrayList<String> namelst12;
    private ArrayList<String>namelst123;
    ArrayList<String> friendlist1;
    private ArrayList<Double> latc;
    LabelAdapter labelAdapter;
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text);
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
        label = new ArrayList<>();
        lat6 = new ArrayList<>();
        namelst12 = new ArrayList<>();
        namelst123 = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        RestaurantList = new ArrayList<>();
        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(), RestaurantList);
        recyclerView1 = findViewById(R.id.rv_zi);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(RestaurantListAdapter);
        recyclerView2 = findViewById(R.id.rv_hen);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        final String currentUserID = auth.getCurrentUser().getUid();
        db.collection("Restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
//                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String restaurant_id = doc.getDocument().getId();
                            Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                            RestaurantList.add(restaurant);
                            RestaurantListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        db.collection("Restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        restaurant = documentSnapshot.toObject(Restaurant.class);
                        String restaurant_id = documentSnapshot.getId();
                        lat.add(restaurant.getRestaurant_lat());
                        lat.add(restaurant.getRestaurant_long());
                        namelst.add(restaurant.getRestaurant_name());
                        num.add(restaurant.getRestaurant_phone());
                        restaurant2.add(restaurant_id);
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
        ref = FirebaseDatabase.getInstance().getReference("resturant");

        searchView = findViewById(R.id.searchView);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(edit_text.this, profile.class);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(edit_text.this, MapsActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("namelst", namelst);
                    intent.putExtra("num", num);
                    intent.putExtra("lat1", lat1);
                    intent.putExtra("restaurantList1", restaurantList1);
                    intent.putExtra("clat", clat);
                    intent.putExtra("lat3", lat3);
                    intent.putExtra("lat4", lat4);
                    intent.putExtra("latc", latc);
                    intent.putExtra("lat6",lat6);
                    intent.putExtra("namelst12",namelst12);
                    intent.putExtra("namelst123",namelst123);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(edit_text.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent(edit_text.this, FriendList.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        //Send value from adapter to activity
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    //Send value from adapter to activity
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            tag = intent.getStringExtra("tag");
            Log.d(TAG,"tag: "+tag);
            ArrayList<Restaurant> myList = new ArrayList<>();
            for (Restaurant object : list) {
                if (object.getRestaurant_name().toLowerCase().contains(tag.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(tag.toLowerCase())) {
                    myList.add(object);
                }
                if (object.getRestaurant_name().toLowerCase().contains(tag.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(tag.toLowerCase())) {
                    lat6.add(object.getRestaurant_lat());
                }
                if (object.getRestaurant_name().toLowerCase().contains(tag.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(tag.toLowerCase())) {
                    lat6.add(object.getRestaurant_long());
                }
                if (object.getRestaurant_name().toLowerCase().contains(tag.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(tag.toLowerCase())) {
                    namelst12.add(object.getRestaurant_name());
                }
                if (object.getRestaurant_name().toLowerCase().contains(tag.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(tag.toLowerCase())) {
                    namelst123.add(object.getRestaurant_phone());
                }
            }
            RestaurantListAdapter adapterClass = new RestaurantListAdapter(edit_text.this, myList);
            recyclerView1.setAdapter(adapterClass);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        int size = label.size();
        label.clear();
        labelAdapter.notifyItemRangeRemoved(0,size);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference docRef = db.collection("cities").document("BJ");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                resturant r = documentSnapshot.toObject(resturant.class);
            }
        });
        db.collection("Label")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Label labelObject = document.toObject(Label.class);
                                label.add(labelObject);
                            }
                            labelAdapter = new LabelAdapter(edit_text.this, label);
                            recyclerView2.setAdapter(labelAdapter);

                            for (int i = 0; i < label.size(); i++) {
                                Log.d(TAG, "label" + label.get(i).getLabel_tags());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        db.collection("Restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    // Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    list = new ArrayList<>();
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String restaurant_id = doc.getDocument().getId();
                            Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                            list.add(restaurant);
                        }
                    }
                    RestaurantListAdapter adapterClass = new RestaurantListAdapter(edit_text.this, list);
                    recyclerView1.setAdapter(adapterClass);
                }
            }
        });
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<Restaurant> myList = new ArrayList<>();
        for (Restaurant object : list) {
            if (object.getRestaurant_name().toLowerCase().contains(str.toLowerCase()) || object.getRestaurant_tags().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }
        }
        RestaurantListAdapter adapterClass = new RestaurantListAdapter(edit_text.this, myList);
        recyclerView1.setAdapter(adapterClass);
    }
}
