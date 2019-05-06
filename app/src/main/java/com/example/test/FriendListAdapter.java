package com.example.test;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private static final String TAG = "TEST";
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore db;
    public List<Friend>FriendList;
    public Context context;
    public String com_date;
    public String date;
    private FirebaseAuth auth;
    private String userId;
    public FriendListAdapter(Context applicationContext, List<Friend> FriendList) {
        this.FriendList = FriendList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_cardview, parent,false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.friend_id.setText(FriendList.get(position).getFriend_id());
    }
    @Override
    public int getItemCount() {
        return FriendList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView friend_id;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            friend_id = (TextView)mView.findViewById(R.id.friend_id);
        }
    }
}
