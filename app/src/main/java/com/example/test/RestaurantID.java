package com.example.test;
import android.support.annotation.NonNull;
import com.google.firebase.firestore.Exclude;
public class RestaurantID {
    @Exclude
    public
    String restaurantId;
    public<T extends RestaurantID> T withId(@NonNull final String id) {
        this.restaurantId = id;
        return (T) this;
    }
}
