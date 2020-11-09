package com.example.getpet;

public class StoryboardObject {
    String name;
    String caption;
    int time;
    int likes;
    boolean isLiked;

    public StoryboardObject(String name, String caption, int time, int likes, boolean isLiked) {
        this.name = name;
        this.caption = caption;
        this.time = time;
        this.likes = likes;
        this.isLiked = isLiked;
    }

    public String getName() { return name; };

    public String getCaption() { return caption; };

    public int getTime() { return time; };

    public int getLikes() { return likes; };

    public boolean isLiked() { return isLiked; };

}
