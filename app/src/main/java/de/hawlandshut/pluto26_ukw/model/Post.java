package de.hawlandshut.pluto26_ukw.model;

import android.util.Log;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;

public class Post {
    private static String TAG = "xxx Post (Class)";
    public String uid;
    public String email;
    public String title;
    public String body;
    public Date createdAt;
    public String firestoreKey;
    public HashMap sys;

    public Post(String uid,
                String email,
                String title,
                String body,
                String firestoreKey,
                Date createdAt,
                HashMap sys
    ) {
        this.uid = uid;
        this.email = email;
        this.title = title;
        this.body = body;
        this.firestoreKey = firestoreKey;
        this.createdAt = createdAt;
        this.sys = sys;
    }

    // document is reveice from FireStore
    // converted to an instance of Post
    public static Post fromDocument(DocumentSnapshot document) {
        Log.d(TAG, "Converting : " + document.getData());
        String uid = (String) document.get("uid");
        String email = (String) document.get("email");
        String title = (String) document.get("title");
        String firebaseKey = document.getId();
        String end = firebaseKey.substring(firebaseKey.length() - 4);
        String body = (String) document.get("body") + " (.." + end + ")"; // Test

        Date createdAt;
        Timestamp h_date = (Timestamp) document.get("createdAt");
        if (h_date == null) {
            //Use local date as substitute until servertime is here
            createdAt = new Date();
        } else {
            createdAt = h_date.toDate();
        }
        return new Post(uid, email,  title, body, firebaseKey, createdAt, null);
    }
}
