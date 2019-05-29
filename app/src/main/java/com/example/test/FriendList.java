package com.example.test;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
public class FriendList extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private static final String TAG = "FriendList";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private FriendListAdapter FriendListAdapter;
    private List<Friend> FriendList;
    private FirebaseAuth auth;
    private String userId;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private Restaurant restaurant;
    private ArrayList<String> num;
    private ArrayList<Double>lat1;
    private ArrayList<String>friendlist1;
    private ArrayList<String>restaurantList1;
    private User user;
    private ArrayList<String>useridd;
    private ArrayList<Integer>record;
    private ArrayList<String>userid2;
    private ArrayList<Double>lat2;
    ArrayList<Double>lat6;
    ArrayList<String>namelst12;
    ArrayList<String>namelst123;
    ArrayList<Double>lat3;
    ArrayList<Double>lat4;
    ArrayList<Double>clat;
    ArrayList<Double>latc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);
        useridd = new ArrayList<>();
        userid2 = new ArrayList<>();
        record = new ArrayList<>();
        friendlist1 = new ArrayList<>();
        lat = new ArrayList<>();
        namelst = new ArrayList<>();
        num = new ArrayList<>();
        lat1 = new ArrayList<>();
        lat2 = new ArrayList<>();
        lat4 = new ArrayList<>();
        lat3 = new ArrayList<>();
        clat = new ArrayList<>();
        lat6 = new ArrayList<>();
        latc = new ArrayList<>();
        namelst12 = new ArrayList<>();
        restaurantList1 = new ArrayList<>();
        namelst123 = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        FriendList = new ArrayList<>();
        FriendListAdapter = new FriendListAdapter(getApplicationContext(),FriendList);
        mMainList = (RecyclerView) findViewById(R.id.recyclerView_friend);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(FriendListAdapter);
        final String currentUserID = auth.getCurrentUser().getUid();
//        db.collection("Friend")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.d(TAG, "Error :" + e.getMessage());
//                } else {
//                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                        if (doc.getType() == DocumentChange.Type.ADDED) {
//                            String friend_id = doc.getDocument().getId();
//                            Friend friend = doc.getDocument().toObject(Friend.class).withId(friend_id);
//                            FriendList.add(friend);
//                            friendlist1.add(friend.getFriend_id());
//
//                            FriendListAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        });
        db.collection("User/"+userId+"/Friend").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        String friend_id = documentSnapshot.getId();
                        Friend friend = documentSnapshot.toObject(Friend.class).withId(friend_id);
                        FriendList.add(friend);
                        friendlist1.add(friend.getFriend_id());
                        FriendListAdapter.notifyDataSetChanged();
                    }
                    for(int iii =0;iii<friendlist1.size();iii++){
                        Log.d(TAG,"friendlist1: "+friendlist1.get(iii));
                    }
                }
            }
        });


        db.collection("User")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(TAG, "Error :" + e.getMessage());
                        } else {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String User_id = doc.getDocument().getId();
                                    User user = doc.getDocument().toObject(User.class).withId(userId);
                                    userid2.add(User_id);
                                }
                            }
                        }
                    }
                });
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        user = documentSnapshot.toObject(User.class);
                        useridd.add(user.getUser_id());

                    }
                }
            }
        });


        for(int zx = 0;zx<useridd.size();zx++){
            if (friendlist1.contains(useridd.get(zx))){
                record.add(zx);
            }
        }
        for (int qwe = 0;qwe<record.size();qwe++){
            db.collection("User/"+ userid2.get(record.get(qwe)) +"/Favorite_restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error :" + e.getMessage());
                    } else {
                        restaurantList1 = new ArrayList<>();
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String restaurant_id = doc.getDocument().getId();
                                Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                                restaurantList1.add(restaurant_id);
                            }
                        }

                        for (String restaurantId : restaurantList1) {
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

                                                    lat2.add(restaurant.getRestaurant_lat());
                                                    lat2.add(restaurant.getRestaurant_long());

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
        }
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
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(FriendList.this,profile.class);
                    intent.putExtra("user_id",userId);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(FriendList.this,MapsActivity.class);
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
                    Intent intent = new Intent(FriendList.this, favorite_main_interface.class);
                    intent.putExtra("friendlist1",friendlist1);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( FriendList.this,edit_text.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        FloatingActionButton add_friend = findViewById(R.id.add_friend);
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    public void openDialog() {
        add_dialog add_dialog = new add_dialog();
        add_dialog.show(getSupportFragmentManager(),"Add Friend");
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
