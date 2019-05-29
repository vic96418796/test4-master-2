package com.example.test;

import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Label extends LabelID {
    private String Label_tags;
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public Label() {
    }
    public Label(String label_tags) { Label_tags = label_tags; }
    public String getLabel_tags() { return Label_tags; }
    public void setLabel_tags(String label_tags) { Label_tags = label_tags; }


}
