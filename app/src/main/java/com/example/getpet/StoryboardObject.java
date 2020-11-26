package com.example.getpet;

public class StoryboardObject {

    String postID;
    String name;
    String caption;
    int time;
    int likes;
    boolean isLiked;

    public StoryboardObject(String postID,String name, String caption, int likes) {
        this.postID = postID;
        this.name = name;
        this.caption = caption;
        this.likes = likes;
    }

    public String getPostID() { return postID;}

    public String getName() { return name; };

    public String getCaption() { return caption; };

    public int getTime() { return time; };

    public int getLikes() { return likes; };

    public boolean isLiked() { return isLiked; };

}
