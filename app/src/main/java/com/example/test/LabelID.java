package com.example.test;
import android.support.annotation.NonNull;
import com.google.firebase.firestore.Exclude;
public class LabelID {
    @Exclude
    public
    String labelId;
    public<T extends LabelID> T withId(@NonNull final String id) {
        this.labelId = id;
        return (T) this;
    }
}

