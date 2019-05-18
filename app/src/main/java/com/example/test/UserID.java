package com.example.test;
import android.support.annotation.NonNull;
import com.google.firebase.firestore.Exclude;
public class UserID {
    @Exclude
    public
    String userId;
    public<T extends UserID> T withId(@NonNull final String id) {
        this.userId = id;
        return (T) this;
    }
}
