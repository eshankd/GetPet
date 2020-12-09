package com.example.getpet;

import java.util.Calendar;

public class NotificationObject {

        String fromUser,toUser,fromName;
        String notification;
        String sourceID;
        String origin;
        boolean isRead;
        String notifID;
        Long time;

        public NotificationObject(String notifID,  String fromName, String fromUser, String toUser,String notification, String sourceID, String origin, boolean isRead,
                                  Long time) {
            this.notifID = notifID;
            this.fromUser = fromUser;
            this.fromName = fromName;
            this.toUser = toUser;
            this.notification = notification;
            this.sourceID = sourceID;
            this.origin = origin;
            this.isRead = isRead;
            this.time = time;
        }

    public String getTime() {
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        long difference = (currentTime-time)/(1000*60*60);
        if (difference==0)
            return("Just now");
        else
            return (Long.toString(difference)+"h ago");
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

