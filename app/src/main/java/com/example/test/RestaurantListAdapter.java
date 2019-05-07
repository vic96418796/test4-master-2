package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.Calendar;
import java.util.List;

import java.util.List;



public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private static final String TAG = "TEST";
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore db;
    public List<Restaurant>RestaurantList;
    public Context context;
    public String com_date;
    public String date;
    private FirebaseAuth auth;
    private String userId;
    int a = 0;

    public RestaurantListAdapter(Context applicationContext, List<Restaurant> RestaurantList) {
        this.RestaurantList = RestaurantList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_cardview, parent,false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.restaurant_id.setText(RestaurantList.get(position).getRestaurant_id());
        holder.restaurant_add.setText(RestaurantList.get(position).getRestaurant_add());
        String Image =RestaurantList.get(position).getRestaurant_image();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference picReference = storageReference.child("Restaurant/"+Image);
        Glide.with(holder.restaurant_image.getContext())
                .using(new FirebaseImageLoader())
                .load(picReference)
                .into(holder.restaurant_image);

        final String restaurant_id = RestaurantList.get(position).restaurantId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent();
                intent.setClass(context,RestaurantInformation.class);
                intent.putExtra("RestaurantId", restaurant_id);
                Log.d(TAG,"Id: "+restaurant_id);
                context.startActivity(intent);

            }
        });
        //按鈕變色
        holder.restaurant_like.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.restaurant_like.setImageDrawable(context.getDrawable(R.drawable.love));
            }
        });

    }
    @Override
    public int getItemCount() {
        return RestaurantList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView restaurant_id;
        public TextView restaurant_add;
        public ImageView restaurant_image;
        public ImageButton restaurant_like;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            restaurant_id = (TextView)mView.findViewById(R.id.restaurant_id);
            restaurant_add = (TextView)mView.findViewById(R.id.restaurant_add);
            restaurant_image = (ImageView)mView.findViewById(R.id.restaurant_image);
            restaurant_like = (ImageButton)mView.findViewById(R.id.restaurant_like);
        }
    }
}
