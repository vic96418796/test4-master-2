package com.example.test;
import android.os.Parcelable;
import com.google.firebase.Timestamp;
public class RestaurantMeme extends RestaurantID {
    private String User_id;
    private String Restaurant_meme;



    //FIREBASE日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public RestaurantMeme() {
    }
    public RestaurantMeme(String restaurant_meme,String user_id) {
        Restaurant_meme = restaurant_meme;
        User_id = user_id;

    }
    public String getRestaurant_meme() { return Restaurant_meme; }
    public void setRestaurant_meme(String restaurant_meme) { Restaurant_meme = restaurant_meme; }
    public String getUser_id() { return User_id; }
    public void setUser_id(String user_id) { User_id = user_id; }
}
