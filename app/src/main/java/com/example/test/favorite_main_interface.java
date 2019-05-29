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
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;

import com.firebase.client.snapshot.Index;
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

public class favorite_main_interface extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private static final String TAG = "FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private RestaurantListAdapter RestaurantListAdapter;
//    private RestaurantListAdapter RestaurantListAdapter1;
    private Restaurant restaurant;
    private List<Restaurant> RestaurantList;
    private FirebaseAuth auth;
    private String userId;
    private ArrayList<String> restaurantList;
    private ArrayList<Double> lat;
    private ArrayList<String> namelst;
    private ArrayList<String> num;
    private ArrayList<Double>lat1;
    private ArrayList<String>lat11;
    private ArrayList<Double>lat2;
    private ArrayList<String>clat;
    private ArrayList<Double>lat3;
    private ArrayList<Double>lat4;
    private ArrayList<String>useridd;
    private ArrayList<String>userid2;
    private ArrayList<String>record;
    private ArrayList<String>restaurant2;
    private ArrayList<String>friendlist2;
    private ArrayList<Double>latc;
    private ArrayList<String>restaurantList1;
    ArrayList<String>friendlist1;
    ArrayList<Double>lat6;
    ArrayList<String>namelst12;
    ArrayList<String>namelst123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_main_interface);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //收藏
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        RestaurantList = new ArrayList<>();
        lat = new ArrayList<>();
        latc = new ArrayList<>();
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
        lat4 = new ArrayList<>();
        lat6 = new ArrayList<>();
        namelst12 = new ArrayList<>();
        namelst123 = new ArrayList<>();
        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList);
//        RestaurantListAdapter1 = new RestaurantListAdapter(getApplicationContext(),RestaurantList);
        mMainList = (RecyclerView)findViewById(R.id.recyclerView_restaurant);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(RestaurantListAdapter);
        db.collection("User/"+ userId +"/Favorite_restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                                lat1.add(restaurant.getRestaurant_lat());
                                                lat1.add(restaurant.getRestaurant_long());
                                                lat11.add(restaurant_id);
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
                                    userid2.add(doc.getDocument().getId());
                                }
                            }
                            for(int iii =0;iii<userid2.size();iii++){
                                Log.d(TAG,"userid2: "+userid2.get(iii));
                            }
                        }
                    }
                });
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//這裡是抓全部USER
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        User user = documentSnapshot.toObject(User.class);
                        useridd.add(user.getUser_id());//抓完useridd
                    }
                    for(int iii =0;iii<useridd.size();iii++){
                        Log.d(TAG,"useridd: "+useridd.get(iii));
                    }
                    db.collection("Friend").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//抓完USER後抓FriendList
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot : task.getResult()){
                                    String friend_id = documentSnapshot.getId();
                                    Friend friend = documentSnapshot.toObject(Friend.class).withId(friend_id);
                                    friendlist1.add(friend.getFriend_id());//抓完friendlist

                                }
                                for(int iii =0;iii<friendlist1.size();iii++){
                                    Log.d(TAG,"friendlist1: "+friendlist1.get(iii));
                                }
                            }
                            for(int zx = 0;zx<useridd.size();zx++){//比對
                                if (friendlist1.contains(useridd.get(zx))){
                                    record.add(useridd.get(zx));
                                    Log.d(TAG,"record: "+record.get(zx));
                                }
                            }
                            for (int qwe = 0;qwe<record.size();qwe++){//放這裡是因為異構性問題
                                db.collection("User")//這裡抓出來是比對出來後的email
                                        .whereEqualTo("User_id",record.get(qwe))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if(task.isSuccessful()){
                                                   String UserWithFriendDocId = null;
                                                   for(DocumentSnapshot documentSnapshot:task.getResult()){
                                                        UserWithFriendDocId = documentSnapshot.getId();
                                                   }
                                                   db.collection("User")
                                                           .document(UserWithFriendDocId)
                                                           .collection("Favorite_restaurant")
                                                           .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                           if(task.isSuccessful()){
                                                               for(DocumentSnapshot documentSnapshot:task.getResult()){
                                                                   String restaurant_id = documentSnapshot.getString("Restaurant_id");
                                                                   restaurantList1.add(restaurant_id);
                                                                   Log.d(TAG,"finaldB: "+restaurant_id);
                                                               }
                                                               for(int r =0;r<restaurantList1.size();r++){
                                                                   if(lat11.contains(restaurantList1.get(r))){
                                                                       clat.add(restaurantList1.get(r));
                                                                   }
                                                               }
                                                               for(int re =0;re<clat.size();re++){
                                                                   Log.d(TAG,"finaldre: "+clat.get(re));
                                                               }
                                                               db.collection("Restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                       if(task.isSuccessful()){
                                                                           for(DocumentSnapshot documentSnapshot : task.getResult()){
                                                                               String restaurant_id = documentSnapshot.getId();
                                                                               Restaurant restaurant = documentSnapshot.toObject(Restaurant.class).withId(restaurant_id);
                                                                               for (int re =0;re<clat.size();re++){
                                                                                   if (clat.get(re).equalsIgnoreCase(restaurant_id)){
//                                                                                       lat3.add(restaurant.getRestaurant_lat());
//                                                                                       lat3.add(restaurant.getRestaurant_long());
//                                                                                       Log.d(TAG,"finaldza: "+lat3.get(0));
//                                                                                       Log.d(TAG,"finaldza: "+lat3.get(1));
                                                                                   }
                                                                               }
                                                                               for(int re =0;re<lat3.size();re++){
                                                                                   Log.d(TAG,"finaldlll: "+lat3.get(re));
                                                                               }
                                                                           }
                                                                           for(int iii =0;iii<friendlist1.size();iii++){
                                                                               Log.d(TAG,"friendlist1: "+friendlist1.get(iii));
                                                                           }
                                                                       }
                                                                   }

                                                               });
                                                           }
                                                       }
                                                   });
                                               }
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });
        db.collection("Restaurant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        restaurant = documentSnapshot.toObject(Restaurant.class);
                        String restaurant_id = documentSnapshot.getId();
                        lat.add(restaurant.getRestaurant_lat());
                        lat.add(restaurant.getRestaurant_long());
                        namelst.add(restaurant.getRestaurant_name());
                        num.add(restaurant.getRestaurant_phone());
                        restaurant2.add(restaurant_id);
                        Log.d(TAG,"finaldc: "+restaurant_id);
                    }
                }
            }
        });
        FloatingActionButton add_restaurant = findViewById(R.id.add_restaurant);
        add_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(favorite_main_interface.this,edit_text.class);
                startActivity(intent);
            }
        });
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
                    Intent intent = new Intent(favorite_main_interface.this, FriendList.class);
                    startActivity(intent);
                    intent.putExtra("lat1",lat1);
                    return true;
                }
                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(favorite_main_interface.this,profile.class);
                    intent.putExtra("user_id",userId);

                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(favorite_main_interface.this,MapsActivity.class);
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
                if (id == R.id.nav_restaurant) {
                    Intent intent = new Intent( favorite_main_interface.this,edit_text.class);
                    intent.putExtra("lat",lat);
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
            new AlertDialog.Builder(favorite_main_interface.this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("確認視窗")
                    .setMessage("確定要登出嗎?")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(favorite_main_interface.this, login_interface.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
        return true;
    }
}
