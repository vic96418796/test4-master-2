package com.example.test;

import android.support.v7.widget.RecyclerView;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    ArrayList<Restaurant> list;
    public AdapterClass(ArrayList<Restaurant> list)
    {
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_cardview,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,int i){
        myViewHolder.name.setText(list.get(i).getRestaurant_name());
        myViewHolder.tag.setText(list.get(i).getRestaurant_tags());
        String imgUrl = "Restaurant/"+list.get(i).getRestaurant_image();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imgUrl);;
        Glide.with(myViewHolder.image.getContext())
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(myViewHolder.image);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,tag;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurant_name);
            tag = itemView.findViewById(R.id.restaurant_tags);
            image =itemView.findViewById(R.id.restaurant_image);
        }

    }
}
