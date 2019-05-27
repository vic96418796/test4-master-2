package com.example.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class add_comment extends AppCompatDialogFragment {
    private EditText edit_restaurant_meme;
    private add_commentListener listener;

    public add_comment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_meme,null);
        builder.setView(view)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("設定")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String restaurant_meme = edit_restaurant_meme.getText().toString();
                        listener.applyTexts(restaurant_meme);
                    }
                });
        edit_restaurant_meme = view.findViewById(R.id.edit_user_name);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (add_commentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement set_profileListener");
        }
    }

    public interface  add_commentListener {
        void applyTexts(String restaurant_meme);
    }
}
