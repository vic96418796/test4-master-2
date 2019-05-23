package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RestaurantMemeListAdapter extends RecyclerView.Adapter<RestaurantMemeListAdapter.ViewHolder> {

    private static final String TAG = "TEST";
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore db;
    public List<RestaurantMeme> RestaurantMemeList;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView user_id;
        TextView restaurant_meme;
        ImageView user_image;



        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            user_id = (TextView)mView.findViewById(R.id.user_id);
            restaurant_meme = (TextView)mView.findViewById(R.id.restaurant_meme);
            user_image = (ImageView)mView.findViewById(R.id.image);
        }
    }


    public RestaurantMemeListAdapter(Context applicationContext, List<RestaurantMeme> RestaurantMemeList) {
        this.RestaurantMemeList = RestaurantMemeList;
        this.context = applicationContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_meme_cardview, parent,false);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.restaurant_meme.setText(RestaurantMemeList.get(position).getRestaurant_meme());
        holder.user_id.setText(RestaurantMemeList.get(position).getUser_id());
    }
    @Override
    public int getItemCount() {
        return RestaurantMemeList.size();
    }
}
