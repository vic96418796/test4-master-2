package com.example.test;
import android.os.Parcelable;
import com.google.firebase.Timestamp;
public class User extends UserID {
    private String User_name;



    //FIREBASE日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public User() {
    }
    public User(String user_name) {
        User_name = user_name;

    }
    public String getUser_name() { return User_name; }
    public void setUser_name(String user_name) { User_name = user_name; }

}
