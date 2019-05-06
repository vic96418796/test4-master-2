package com.example.test;
import android.support.annotation.NonNull;
import com.google.firebase.firestore.Exclude;
public class FriendID {
    @Exclude
    public
    String friendId;
    public<T extends FriendID> T withId(@NonNull final String id) {
        this.friendId = id;
        return (T) this;
    }
}
