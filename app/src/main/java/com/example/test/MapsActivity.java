package com.example.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private FirebaseFirestore db;

    private static final String TAG = "MapsActivity";
    private RestaurantListAdapter RestaurantListAdapter;

    Restaurant restaurant = new Restaurant();
    private GoogleMap mMap;
    float zoom;
    private LocationManager locMGR;
    ArrayList<Double> lat;
    ArrayList<Double> lat1;
    ArrayList<Double> lat3;
    ArrayList<String>namelst;
    ArrayList<String>num;
    String bestProv;
    ArrayList<String>restaurantList1;
    ArrayList<String>clat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        restaurantList1 = new ArrayList<>();
        restaurantList1 = (ArrayList<String>)getIntent().getSerializableExtra("restaurantList1");
        clat = new ArrayList<>();
        clat = (ArrayList<String>) getIntent().getSerializableExtra("clat");
        lat = new ArrayList<>();
        lat = (ArrayList<Double>) getIntent().getSerializableExtra("lat");
        namelst=new ArrayList<>();
        namelst = (ArrayList<String>) getIntent().getSerializableExtra("namelst");
        num = new ArrayList<>();
        num= (ArrayList<String>) getIntent().getSerializableExtra("num");
        lat1 = new ArrayList<>();
        lat1 = (ArrayList<Double>) getIntent().getSerializableExtra("lat1");
        lat3 = new ArrayList<>();
        lat3 = (ArrayList<Double>) getIntent().getSerializableExtra("lat3");
        for(int i =0;i<lat.size();i++){
            Log.d(TAG,"lat: "+lat.get(i));
        }
        for(int ii =0;ii<lat1.size();ii++){
            Log.d(TAG,"lat1: "+lat1.get(ii));
        }
        for (int r = 0;r<namelst.size();r++){
            Log.d(TAG,"namelst" + namelst.get(r));
        }
        for (int rr = 0;rr<num.size();rr++){
            Log.d(TAG,"num" + num.get(rr));
        }
        for(int iii =0;iii<lat3.size();iii++){
            Log.d(TAG,"lat3: "+lat3.get(iii));
        }
        for(int iq =0;iq<restaurantList1.size();iq++){
            Log.d(TAG,"qq: "+restaurantList1.get(iq));
        }
        for(int iqq =0;iqq<clat.size();iqq++){
            Log.d(TAG,"cclat: "+clat.get(iqq));
        }
//        RestaurantListAdapter = new RestaurantListAdapter(getApplicationContext(),RestaurantList1);
        db= FirebaseFirestore.getInstance();
//        db.collection("Restaurant"t(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
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
//                        ).addSnapshotListener(new EventListener<QuerySnapshot>() {
////            @Override
////            public void onEven}
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
        int y = 0;
        int yy = 0;
        int yyy = 0;
        //全部餐廳
        for (int l = 0;l<=lat.size()/2-1;l++){
            LatLng res = new LatLng(lat.get(y),lat.get(y+1));
            y+=2;
            mMap.addMarker(new MarkerOptions().position(res).title(namelst.get(l)).snippet(num.get(l)).icon(BitmapDescriptorFactory.fromResource(R.drawable.fotojet)));
        }
        //個人收藏
        for (int ll = 0;ll<=lat1.size()/2-1;ll++){
            LatLng res = new LatLng(lat1.get(yy),lat1.get(yy+1));
            yy+=2;
            mMap.addMarker(new MarkerOptions().position(res).icon(BitmapDescriptorFactory.fromResource(R.drawable.foodiconlike)));
        }
        //好友共同擁有
        for (int lll=0;lll<=lat3.size()/2-1;lll++){
            LatLng res = new LatLng(lat3.get(yyy),lat3.get(yyy+1));
            yyy+=2;
            mMap.addMarker(new MarkerOptions().position(res).icon(BitmapDescriptorFactory.fromResource(R.drawable.foodiconfriendnew)));
        }




//        LatLng Taipei101 = new LatLng(25.033611,121.56500);
//        zoom = 17;
//        mMap.addMarker(new MarkerOptions().position(Taipei101).title("Taipei101").snippet("a205238@gmail.com來過!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Taipei101,zoom));
//
//        LatLng Myhome = new LatLng(25.033705,121.431425);
//        mMap.addMarker(new MarkerOptions().position(Myhome).title("Myhome").icon(BitmapDescriptorFactory.fromResource(R.drawable.fotojet)));

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

