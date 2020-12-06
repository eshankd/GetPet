package com.example.getpet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationObjectAdapter extends ArrayAdapter<NotificationObject>    {

    private Context mContext;
    private List<NotificationObject> notificationObjectList;

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

        FirebaseStorage storage = FirebaseStorage.getInstance();

        TextView fromUser = listItem.findViewById(R.id.fromUser);
            fromUser.setText(currentNotificationCard.getFromUser());

        TextView notiication = listItem.findViewById(R.id.notification);
            notiication.setText(currentNotificationCard.getNotification());


        return listItem;
    }
    }
