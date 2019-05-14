package com.example.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.app.PendingIntent.getActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private FirebaseFirestore db;
    private List<Restaurant> restaurantList1;
    private static final String TAG = "MapsActivity";

    private RestaurantListAdapter RestaurantListAdapter;
    private List<Restaurant> RestaurantList1;
    Restaurant restaurant = new Restaurant();





    private GoogleMap mMap;
    float zoom;
    private LocationManager locMGR;
    ArrayList<Double> lat;
    String bestProv;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lat = new ArrayList<>();
        lat = (ArrayList<Double>) getIntent().getSerializableExtra("lat");
        for(int i =0;i<lat.size();i++){
            Log.d(TAG,"lat: "+lat.get(i));
        }

//        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList1);
        db= FirebaseFirestore.getInstance();



//        db.collection("Restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.d(TAG, "Error :" + e.getMessage());
//                } else {
//                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                        if (doc.getType() == DocumentChange.Type.ADDED) {
//                            String restaurant_id = doc.getDocument().getId();
//                            restaurant = doc.getDocument().toObject(Restaurant.class).withId(restaurant_id);
//                            lat.add(restaurant.getRestaurant_lat());
//                            lat.add(restaurant.getRestaurant_long());
//                            RestaurantList1.add(restaurant);
//                            RestaurantListAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        });
//        db.collection("Restaruant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot documentSnapshot : task.getResult()){
//                        restaurant = documentSnapshot.toObject(Restaurant.class);
//                    }
//                    isCheck = true;
//                }
//            }
//        });













        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int l = 0;l<=lat.size()/2;l++){
            LatLng res = new LatLng(lat.get(l),lat.get(l+1));
            mMap.addMarker(new MarkerOptions().position(res));
        }



        LatLng Taipei101 = new LatLng(25.033611,121.56500);
        zoom = 17;
        mMap.addMarker(new MarkerOptions().position(Taipei101).title("Taipei101"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Taipei101,zoom));

        LatLng Myhome = new LatLng(25.033705,121.431425);
        mMap.addMarker(new MarkerOptions().position(Myhome).title("Myhome"));

        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

        }
        setMyLocation();
    }





    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setMyLocation();
            } else {
                Toast.makeText(this, "未取得授權", Toast.LENGTH_SHORT).
                        show();
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setMyLocation() throws SecurityException {
        mMap.setMyLocationEnabled(true);
    }

    public void onLocationChanged(Location location) {
        String x = "緯=" + Double.toString(location.getLatitude());
        String y = "經=" + Double.toString(location.getLongitude());
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        zoom = 17;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        Toast.makeText(this,x+"\n"+y,Toast.LENGTH_LONG).show();

    }

    protected void onResume() {
        super.onResume();
        locMGR = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProv = locMGR.getBestProvider(criteria,true);
        if (locMGR.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locMGR.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
                locMGR.requestLocationUpdates(bestProv,1000,1,this);
            }
        }
        else {
            Toast.makeText(this,"請開啟定位服務",Toast.LENGTH_LONG).show();
        }

    }

    protected void onPause() {
        super.onPause();
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
            locMGR.removeUpdates((LocationListener)this);
        }
    }

    public void onStatusChanged (String provide,int status,Bundle extras) {
        Criteria creteria = new Criteria();
        bestProv = locMGR.getBestProvider(creteria,true);
    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }
}

