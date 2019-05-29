package com.example.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;



public class edit_text extends AppCompatActivity {
    DatabaseReference ref;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    ArrayList<Label> label;
    ArrayList<Restaurant> list;
    SearchView searchView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private ArrayList<String> userAll;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private ArrayList<String> num;
    private ArrayList<Double>lat1;
    private ArrayList<String>lat5;
    private Restaurant restaurant;
    private String userId;
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
        lat = new ArrayList<>();
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,  R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        ref = FirebaseDatabase.getInstance().getReference("resturant");
        recyclerView1 = findViewById(R.id.rv_zi);
        recyclerView2 = findViewById(R.id.rv_hen);
        searchView = findViewById(R.id.searchView);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                int id = menuItem.getItemId();
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(edit_text.this,profile.class);
                    intent.putExtra("user_id",userId);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(edit_text.this,MapsActivity.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("namelst",namelst);
                    intent.putExtra("num",num);
                    intent.putExtra("lat1",lat1);
                    intent.putExtra("restaurantList1",restaurantList1);
                    intent.putExtra("clat",clat);
                    intent.putExtra("lat3",lat3);
                    intent.putExtra("lat4",lat4);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(edit_text.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent( edit_text.this,FriendList.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
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

            db.collection("Restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        // Log.d(TAG, "Error :" + e.getMessage());
                    } else {
                        list = new ArrayList<>();
                        label = new ArrayList<>();
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String restaurant_id = doc.getDocument().getId();
                                Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                                list.add(restaurant);
                            }
                        }
                        LabelAdapter LabelAdapter = new LabelAdapter(edit_text.this,label);
                        RestaurantListAdapter adapterClass = new RestaurantListAdapter(edit_text.this,list);
                        recyclerView1.setAdapter(adapterClass);
                        recyclerView2.setAdapter(LabelAdapter);
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
    private void search(String str)
    {
        ArrayList<Restaurant>  myList = new ArrayList<>();
        for(Restaurant object : list)
        {
            if(object.getRestaurant_name().toLowerCase().contains(str.toLowerCase())||object.getRestaurant_tags().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        RestaurantListAdapter adapterClass = new RestaurantListAdapter(edit_text.this,myList);
        recyclerView1.setAdapter(adapterClass);
    }
}
