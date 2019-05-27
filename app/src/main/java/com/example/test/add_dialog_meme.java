package com.example.test;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class add_dialog_meme extends AppCompatDialogFragment {
    private EditText editTextRestaurant_meme;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String restaurantId ;
    private String userId;
    private FirebaseAuth auth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_meme,null);
        userId = auth.getCurrentUser().getUid();
        builder.setView(view)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("新增評論")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String edit_restaurant_meme = editTextRestaurant_meme.getText().toString();
                        userId = auth.getCurrentUser().getUid();
                        Map<String, Object> Restaurant = new HashMap<>();
                        Restaurant.put("User_id", userId);
                        Restaurant.put("Restaurant_meme",edit_restaurant_meme);
                        db.collection("Restaurant"+restaurantId+"Meme")
                                .add(Restaurant);
                    }
                });
        editTextRestaurant_meme = view.findViewById(R.id.edit_friend_email);
        return builder.create();
    }
}