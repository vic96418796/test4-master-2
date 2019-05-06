package com.example.test;
import android.os.Parcelable;
import com.google.firebase.Timestamp;
public class Restaurant extends RestaurantID {
    private String Restaurant_id;
    private String Restaurant_add;
    private String Restaurant_image;
    //FIREBASE日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public Restaurant() {
    }
    public Restaurant(String restaurant_id,String restaurant_add,String restaurant_image) {
        Restaurant_id = restaurant_id;
        Restaurant_add = restaurant_add;
        Restaurant_image = restaurant_image;
    }
    public String getRestaurant_id() { return Restaurant_id; }
    public void setRestaurant_id(String restaurant_id) { Restaurant_id = restaurant_id; }
    public String getRestaurant_add() { return Restaurant_add; }
    public void setRestaurant_add(String restaurant_add) { Restaurant_add = restaurant_add; }
    public String getRestaurant_image() {
        return Restaurant_image;
    }
    public void setRestaurant_image(String restaurant_image) { Restaurant_image = restaurant_image; }
}
