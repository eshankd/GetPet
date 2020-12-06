package com.example.getpet;

import android.util.Log;

import java.util.ArrayList;

public class StoryboardObject {

    String postID;
    String name;
    String caption;
    ArrayList<String> usersLiked;
    int time;
    int likes;
    boolean isLiked;

    public StoryboardObject(String postID,String name, String caption, ArrayList<String> usersLiked, boolean isLiked) {
        this.postID = postID;
        this.name = name;
        this.caption = caption;
        this.usersLiked = usersLiked;
        this.likes = usersLiked.size();
        this.isLiked = isLiked;
    }

    public String getPostID() { return postID;}

    public String getName() { return name; }

    public String getCaption() { return caption; }

    public int getLikes() { return likes; }

    public void like() {
        if(!isLiked)    {
            likes++;
            isLiked = true;
        }
    }

    public void unlike()    {
        if(isLiked) {
            likes--;
            isLiked = false;
        }
    }

}
