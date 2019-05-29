package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
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
    private static final String TAG = "LabelAdapter";
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

    private LabelAdapter.transPageListener mTransPageListener;//adapter跳轉fragment

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_label_cardview, parent, false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String label_tags = edit_text.get(position).labelId;
        holder.label.setText(edit_text.get(position).getLabel_tags());

        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = edit_text.get(position).getLabel_tags();
                Log.d(TAG, "tag: " + tag);
                //Send value from adapter to activity
                Intent intent = new Intent("custom-message");
                intent.putExtra("tag", tag);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

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
            label = (Button) mView.findViewById(R.id.label);
        }
    }

    public interface transPageListener {
        public void onTransPageClick(String tag);
    }//adapter跳轉fragment並攜帶需要的資料

    public void setOnTransPageClickListener(transPageListener transPageListener) {
        this.mTransPageListener = transPageListener;
    }//adapter跳轉fragment
}
