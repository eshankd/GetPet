package com.example.getpet;

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

    public String getPostID() { return postID;}

    public String getName() { return name; }

    public String getCaption() { return caption; }

    public String getAuthorEmail() { return authorEmail; }

    public String getTime() {
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        long difference = (currentTime-time)/(1000*60*60);
        if (difference==0)
            return("Just now");
        else
            return (Long.toString(difference)+"h ago");
    }

    public int getLikes() { return likes; }

    public void like() {
        if(!isLiked)    {
            likes++;
            isLiked = true;
        }
    }

    public void unlike(){
        if(isLiked) {
            likes--;
            isLiked = false;
        }
    }

}
