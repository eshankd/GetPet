package com.example.getpet;

public class StoryboardObject {

    String postID;
    String userEmail;
    String name;
    String caption;
    int time;
    int likes;
    boolean isLiked;

    public StoryboardObject(String postID,String name,String userEmail, String caption, int likes) {
        this.postID = postID;
        this.name = name;
        this.caption = caption;
        this.likes = likes;
        this.userEmail = userEmail;
    }

    public String getPostID() { return postID;}

    public String getName() { return name; };

    public String getUserEmail(){return userEmail;}

    public String getCaption() { return caption; };

    public int getLikes() { return likes; };



}
