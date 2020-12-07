package com.example.getpet;

public class NotificationObject {

        String fromUser,toUser,fromName;
        String time;
        String notification;
        String sourceID;
        String origin;
        Boolean isRead;
        String notifID;

        public NotificationObject(String notifID,  String fromName, String fromUser, String toUser,String notification, String sourceID, String origin, Boolean isRead) {
            this.notifID = notifID;
            this.fromUser = fromUser;
            this.fromName = fromName;
            this.toUser = toUser;
            this.notification = notification;
            this.sourceID = sourceID;
            this.origin = origin;
            this.isRead = isRead;
        }

        public String getNotifId() { return notifID; }

        public String getFromUser() { return fromUser;}

        public String getFromName() {return fromName;}

        public String getToUser() { return toUser; };

        public String getNotification() { return notification; }

        public String getSourceID() { return sourceID; }

        public String getOrigin() { return origin; }

        public boolean getIsRead() { return isRead; }

    }

