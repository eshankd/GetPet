package com.example.getpet;

import java.util.Calendar;

public class NotificationObject {


    // declaring all the variables that will be used in the functions below
        String fromUser,toUser,fromName, notification,sourceID, origin,notifID;
        boolean isRead;
        Long time;

        public NotificationObject(String notifID,  String fromName, String fromUser, String toUser,String notification, String sourceID, String origin, boolean isRead,Long time) {

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

        //function to get the time of when the notification is being received.
    public String getTime() {
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        long difference = (currentTime-time)/(1000*60*60);
        if (difference==0)
            return("Just now");
        else
            return (Long.toString(difference)+"h ago");
    }


    // function to get the notificatoinID for referencing the notification
        public String getNotifId() { return notifID; }
// function to get the sender of the notification
        public String getFromUser() { return fromUser;}
// function to get the name of the sender of the notification
        public String getFromName() {return fromName;}
// function to get the user who the notification is being sent to
        public String getToUser() { return toUser; };
// function to get the notification itself or message
        public String getNotification() { return notification; }
//function to get the source ID to display the image
        public String getSourceID() { return sourceID; }
// function to get the origin to find out where the notification is coming from which activity
        public String getOrigin() { return origin; }
// function to check if the notification is read or not by the user
        public boolean getIsRead() { return isRead; }

    }

