package com.example.getpet;

public class CommentObject {

    String message;
    String fromUser;
    String toUser;

    public CommentObject(String fromUser, String toUser, String message) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getMessage() {
        return message;
    }

}

