package com.example.test;

import android.support.annotation.NonNull;
import com.google.firebase.firestore.Exclude;

public class friend_id {
    @Exclude
    public
    String friendID;

    public<T extends friend_id> T withId(@NonNull final String id) {
        this.friendID = id;
        return (T) this;
    }
}
