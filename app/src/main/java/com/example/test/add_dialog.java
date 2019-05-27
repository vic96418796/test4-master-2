package com.example.test;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class add_dialog extends AppCompatDialogFragment {
    private EditText editTextFriend_id;
    private String userId;
    private FirebaseAuth auth;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        builder.setView(view)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("新增好友")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String edit_friend_email = editTextFriend_id.getText().toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> Friend = new HashMap<>();
                        Friend.put("Friend_id", edit_friend_email);
                        Friend.put("Friend_name",edit_friend_email);
                        db.collection("User/"+userId+"/Friend")
                                .add(Friend);
                    }
                });
        editTextFriend_id = view.findViewById(R.id.edit_friend_email);
        return builder.create();
    }
}