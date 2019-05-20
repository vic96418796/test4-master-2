package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> implements Filterable {

    private List<Restaurant> RestaurantListFull;
     private static final String TAG = "TEST";
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore db;
    public List<Restaurant> RestaurantList;
    public Context context;
    public String com_date;
    public String date;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView restaurant_name;
        TextView restaurant_add;
        ImageView restaurant_image;
        ImageButton restaurant_like;
        TextView restaurant_tags;



        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            restaurant_name = (TextView)mView.findViewById(R.id.restaurant_name);
            restaurant_add = (TextView)mView.findViewById(R.id.restaurant_add);
            restaurant_image = (ImageView)mView.findViewById(R.id.restaurant_image);
            restaurant_like = (ImageButton)mView.findViewById(R.id.restaurant_like);
            restaurant_tags = (TextView)mView.findViewById(R.id.restaurant_tags);
        }
    }


    public RestaurantListAdapter(Context applicationContext, List<Restaurant> RestaurantList) {
        this.RestaurantList = RestaurantList;
        RestaurantListFull = new ArrayList<>(RestaurantList);
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_cardview, parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String userId = firebaseAuth.getCurrentUser().getUid();
        holder.restaurant_name.setText(RestaurantList.get(position).getRestaurant_name());
        holder.restaurant_tags.setText(RestaurantList.get(position).getRestaurant_tags());

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
        //按鈕變色,收藏
        if (firebaseAuth.getCurrentUser() != null) {
            db.collection("User/" + userId + "/Favorite_restaurant")
                    .document(restaurant_id)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot.exists() && e == null) {
                                holder.restaurant_like.setImageResource(R.drawable.love32);
                            } else {
                                holder.restaurant_like.setImageResource(R.drawable.nonlove32);
                            }
                        }
                    });
        }
        if (firebaseAuth.getCurrentUser() != null) {
            holder.restaurant_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("User/" + userId + "/Favorite_restaurant")
                            .document(restaurant_id).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().exists()) {
                                    Map<String,String> favorite = new HashMap<>();
                                    favorite.put("Restaurant_id",restaurant_id);
                                    db.collection("User/" + userId + "/Favorite_restaurant")
                                            .document(restaurant_id).set(favorite);
                                } else {
                                    db.collection("User/" + userId + "/Favorite_restaurant")
                                            .document(restaurant_id).delete();
                                }
                            } else {
                                Toast.makeText(context, "ERROR" + task.getException()
                                        .getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return RestaurantList.size();
    }
//搜尋
    @Override
    public Filter getFilter() {
        return RestaurantFilter;
    }

    private Filter RestaurantFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence searchString) {
                List<Restaurant> filteredList = new ArrayList<>();
                if (searchString == null || searchString.length() == 0) {
                    filteredList.addAll(RestaurantList);
                } else {
                    String filterPattern = searchString.toString().toLowerCase().trim();
                    for (Restaurant item : RestaurantList) {
                        if (item.getRestaurant_name().toLowerCase().contains(filterPattern) || item.getRestaurant_tags().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            RestaurantList.clear();
            RestaurantList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
