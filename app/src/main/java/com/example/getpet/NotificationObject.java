package com.example.getpet;

public class NotificationObject {

        String fromUser,toUser,fromName;
        String time;
        String notification;
        String sourceID;
        String origin;

        public NotificationObject(String fromUser, String fromName, String toUser,String notification, String sourceID, String origin) {
            this.fromUser = fromUser;
            this.fromName = fromName;
            this.toUser = toUser;
            this.notification = notification;
            this.sourceID = sourceID;
            this.origin = origin;
        }

        public String getFromUser() { return fromUser;}

        public String getFromName() {return fromName;}

        public String getToUser() { return toUser; };

        public String getNotification() { return notification; }

        public String getSourceID() { return sourceID; }

        public String getOrigin() { return origin; }

    }

