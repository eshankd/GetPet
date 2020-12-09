package com.example.getpet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationObjectAdapter extends ArrayAdapter<NotificationObject>    {

    private Context mContext;
    private List<NotificationObject> notificationObjectList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;

    public NotificationObjectAdapter(Context context, ArrayList<NotificationObject> list) {
        super(context, 0, list);
        mContext = context;
        notificationObjectList = list;
    }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.notifications_layout, parent, false);

            NotificationObject currentNotificationCard = notificationObjectList.get(position);

            storageRef = storage.getReference().child(currentNotificationCard.getOrigin()+"/"+ currentNotificationCard.getSourceID() + ".jpg");

            TextView fromUser = listItem.findViewById(R.id.fromUser);
            fromUser.setText(currentNotificationCard.getFromName());

            TextView notification = listItem.findViewById(R.id.notification);

            if(!currentNotificationCard.getIsRead()) {
//                notification.getChild(-1).setBackgroundColor(Color.BLACK);
                notification.setTextColor(Color.RED);
                fromUser.setTextColor(Color.RED);
            }


            Log.d("notif",currentNotificationCard.getNotifId());

            DocumentReference docRef = fStore.collection("Notifications").document(currentNotificationCard.getNotifId());
            docRef.update("isRead", true);

            notification.setText(currentNotificationCard.getNotification());



            final File localFile;
            try {
                localFile = File.createTempFile(currentNotificationCard.getSourceID(), "jpg");
                View finalListItem = listItem;
                storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) finalListItem.findViewById(R.id.notifPic)).setImageBitmap(bitmap);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        return listItem;
    }
    }
