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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
public class RestaurantList extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigation_view;
    private static final String TAG = "FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    SearchView searchView;
    private ArrayList<Restaurant> list;
    private RestaurantListAdapter RestaurantListAdapter;
    private List<Restaurant> RestaurantList;
    private FirebaseAuth auth;
    private String userId;
//搜尋
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.restaurant_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                RestaurantListAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        RestaurantList = new ArrayList<>();
        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList);
        mMainList = (RecyclerView) findViewById(R.id.recyclerView_restaurant);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(RestaurantListAdapter);
        final String currentUserID = auth.getCurrentUser().getUid();
        db.collection("Restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());
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
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (RestaurantList.this,edit_text.class);
                startActivity(intent);
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
                    Intent intent = new Intent(RestaurantList.this,profile.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_maps) {
                    Intent intent = new Intent(RestaurantList.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_favorite) {
                    Intent intent = new Intent(RestaurantList.this, favorite_main_interface.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.nav_friend) {
                    Intent intent = new Intent( RestaurantList.this,FriendList.class);
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
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String restaurant_id = doc.getDocument().getId();
                            Restaurant restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
                            list.add(restaurant);
                        }
                    }
                    RestaurantListAdapter RestaurantListAdapter = new RestaurantListAdapter(RestaurantList.this,list);
                    mMainList.setAdapter(RestaurantListAdapter);
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
        for(Restaurant object : RestaurantList)
        {
            if(object.getRestaurant_name().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        RestaurantListAdapter RestaurantListAdapter = new RestaurantListAdapter(RestaurantList.this, myList);
        mMainList.setAdapter(RestaurantListAdapter);
    }
}
