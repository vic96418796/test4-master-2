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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_dialog_search extends AppCompatDialogFragment {
    private EditText editTextRestaurant_id;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_search,null);

        builder.setView(view)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("搜尋美食")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String search_restaurant = editTextRestaurant_id.getText().toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> Restaurant = new HashMap<>();

                    }
                });

        editTextRestaurant_id = view.findViewById(R.id.search_restaurant);
        return builder.create();
    }
}

