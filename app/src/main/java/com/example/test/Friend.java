package com.example.test;
import android.os.Parcelable;
import com.google.firebase.Timestamp;
public class Friend extends FriendID {
    private String Friend_id;
    private String Friend_image;
    //FIREBASE日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public Friend() {
    }
    public Friend(String friend_id,String friend_image) {
        Friend_id = friend_id;
    }
    public String getFriend_id() { return Friend_id; }
    public void setFriend_id(String friend_id) { Friend_id = friend_id; }
    public String getFriend_image() { return Friend_image; }
    public void setFriend_image(String friend_image) { Friend_image = friend_image; }

}