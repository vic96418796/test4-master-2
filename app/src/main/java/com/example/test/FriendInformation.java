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

public class FriendInformation extends AppCompatActivity {
    private static final String TAG ="Friends";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TextView friend_name;
    public TextView friend_id;
    public ImageView friend_image;
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
        setContentView(R.layout.friend_information);;
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        lat4 = new ArrayList<>();
        lat = new ArrayList<>();
        lat5 = new ArrayList<>();
        namelst = new ArrayList<>();
        num = new ArrayList<>();
        lat1 = new ArrayList<>();

        RestaurantList = new ArrayList<>();
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
        Intent intent = this.getIntent();//取得傳遞過來的資料
        String friendId = intent.getStringExtra("FriendId");
        friend_name=(TextView)findViewById(R.id.user_name_profile);
        friend_id=(TextView)findViewById(R.id.user_id);
        friend_image=(ImageView)findViewById(R.id.friend_image);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userId!=null){
            Task<DocumentSnapshot> documentSnapshotTask = db.collection("User/"+userId+"/Friend").document(friendId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            friend_name.setText(document.get("Friend_name").toString());
                            friend_id.setText(document.get("Friend_id").toString());
                            Log.d(TAG,"friendName: "+document.get("Friend_name").toString()+"friendId: "+document.get("Friend_id").toString());
                            String image = document.get("Friend_image").toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            StorageReference picReference = storageReference.child("Friend/"+image);
                            Glide.with(friend_image.getContext())
                                    .using(new FirebaseImageLoader())
                                    .load(picReference)
                                    .into(friend_image);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
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
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        RestaurantList = new ArrayList<>();
        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList);
        mMainList = (RecyclerView)findViewById(R.id.recyclerView_friend_restaurant);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(RestaurantListAdapter);
        userAll = new ArrayList<>();
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
                                                lat5.add(restaurant_id);
                                                lat1.add(restaurant.getRestaurant_lat());
                                                lat1.add(restaurant.getRestaurant_long());
                                                lat11.add(restaurant_id);
                                                Log.d(TAG,"12345"+lat1);
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
                                                                                        lat3.add(restaurant.getRestaurant_lat());
                                                                                        lat3.add(restaurant.getRestaurant_long());
                                                                                        Log.d(TAG,"finaldza: "+lat3.get(0));
                                                                                        Log.d(TAG,"finaldza: "+lat3.get(1));
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
                                                restaurantList.add(restaurant_id);//restaurant id by personal favorite

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
                                                                    Log.d(TAG,"zxc "+lat5);
                                                                    if (lat5.contains(restaurant_id)){
                                                                        lat4.add(restaurant.getRestaurant_lat());
                                                                        lat4.add(restaurant.getRestaurant_long());
                                                                        Log.d(TAG,"comeon: "+lat4);
                                                                    }



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
