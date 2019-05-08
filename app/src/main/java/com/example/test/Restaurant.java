package com.example.test;
import android.os.Parcelable;
import com.google.firebase.Timestamp;
public class Restaurant extends RestaurantID {
    private String Restaurant_name;
    private String Restaurant_add;
    private String Restaurant_image;
    private String Restaurant_tags;
    //FIREBASE日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;
    public Restaurant() {
    }
    public Restaurant(String restaurant_name,String restaurant_add,String restaurant_image,String restaurant_tags) {
        Restaurant_name = restaurant_name;
        Restaurant_add = restaurant_add;
        Restaurant_image = restaurant_image;
        Restaurant_tags = restaurant_tags;
    }
    public String getRestaurant_name() { return Restaurant_name; }
    public void setRestaurant_name(String restaurant_name) { Restaurant_name = restaurant_name; }
    public String getRestaurant_add() { return Restaurant_add; }
    public void setRestaurant_add(String restaurant_add) { Restaurant_add = restaurant_add; }
    public String getRestaurant_image() {
        return Restaurant_image;
    }
    public void setRestaurant_image(String restaurant_image) { Restaurant_image = restaurant_image; }
    public String getRestaurant_tags() { return Restaurant_tags; }
    public void setRestaurant_tags(String restaurant_tags) { Restaurant_tags = restaurant_tags; }
}
