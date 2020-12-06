package com.example.getpet;

public class NotificationObject {

        String fromUser,toUser,fromName;
        String time;
        String notification;

        public NotificationObject(String fromUser, String fromName, String toUser,String notification) {
            this.fromUser = fromUser;
            this.fromName = fromName;
            this.toUser = toUser;
            this.notification = notification;
        }

        public String getFromUser() { return fromUser;}

        public String getFromName(){return fromName;}

        public String getToUser() { return toUser; };

        public String getNotification(){return notification;}

    }

