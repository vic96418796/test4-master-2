package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {
    private static final String TAG = "TEST";
    public FirebaseFirestore db;
    public List<Label> label;
    public List<Label> edit_text;
    public Context context;
    private FirebaseAuth auth;
    private String userId;
    public LabelAdapter(Context applicationContext, List<Label> edit_text) {
        this.edit_text = edit_text;
        this.context = context;
        this.label = label;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_label_cardview, parent,false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String label_tags = edit_text.get(position).labelId;
        holder.label.setText(edit_text.get(position).getLabel_tags());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                做那個篩選的動作
//                Context context = v.getContext();
//                Intent intent = new Intent();
//                intent.setClass(context,FriendInformation.class);
//                intent.putExtra("FriendId", friend_id);
//                Log.d(TAG,"Id: "+friend_id);
//                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return edit_text.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public Button label;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            label = (Button)mView.findViewById(R.id.label);
        }
    }
}
