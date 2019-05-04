package com.example.test;

import android.os.Parcelable;
import com.google.firebase.Timestamp;



public class friend extends friend_id {
    private String friend_mail;
    private String friend_id;
    private Number friend_phone;

    public static final Parcelable.Creator<Timestamp> CREATOR = null;

    public friend() {

    }

    public friend(String friend_id, String friend_mail,Number friend_phone) {
        friend_id = friend_id;
        friend_mail = friend_mail;
        friend_phone = friend_phone;
    }
    public String getfriend_id() { return friend_id; }

    public void setfriend_id(String friend_id) { friend_id = friend_id; }

    public String getfriend_mail() {
        return friend_mail;
    }

    public void setfriend_mail(String friend_mail) {
        friend_mail = friend_mail;
    }

    public Number getfriend_phone() {
        return friend_phone;
    }

    public void setfriend_phone(Number friend_phone) {
        friend_phone = friend_phone;
    }



}