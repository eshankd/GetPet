package com.example.getpet;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class StoryboardObject {

    String postID;
    String name;
    String caption;
    String authorEmail;
    ArrayList<String> usersLiked;
    Long time;
    int likes;
    boolean isLiked;

    public StoryboardObject(String postID, String name, String caption, String authorEmail, ArrayList<String> usersLiked, boolean isLiked,
                            Long time) {
        this.postID = postID;
        this.name = name;
        this.caption = caption;
        this.authorEmail = authorEmail;
        this.usersLiked = usersLiked;
        this.likes = usersLiked.size();
        this.isLiked = isLiked;
        this.time = time;
    }

    // gets the post ID to reference it for images or anything else it may be used for.
    public String getPostID() { return postID;}

    //gets the name of the user adding the story
    public String getName() { return name; }

    //gets the Caption of the post
    public String getCaption() { return caption; }

    //gets the logged in users Email
    public String getAuthorEmail() { return authorEmail; }


    //Function that gets the time of the post so it can register the time it had been posted.
    public String getTime() {
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        Log.d("time", currentTime.toString()+" "+time.toString());
        long difference = (currentTime-time)/(1000*60*60);
        if (difference==0)
            return("Just now");
        else
            return (Long.toString(difference)+"h ago");
    }

    public int getLikes() { return likes; }


    // // Function to like the post - increments the number of likes and sets is liked to false
    public void like() {
        if(!isLiked)    {
            likes++;
            isLiked = true;
        }
    }

    // Function to unlike the post - decrements the number of likes and sets is liked to false

    public void unlike(){
        if(isLiked) {
            likes--;
            isLiked = false;
        }
    }

}
