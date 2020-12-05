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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryboardObjectAdapter extends ArrayAdapter<StoryboardObject> {

    private Context mContext;
    private List<StoryboardObject> storyboardObjectList;

    public StoryboardObjectAdapter(Context context, ArrayList<StoryboardObject> list) {
        super(context, 0, list);
        mContext = context;
        storyboardObjectList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.storyboard_layout, parent, false);

        StoryboardObject currentStoryCard = storyboardObjectList.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child("Storyboard Thumbnails/" + currentStoryCard.getPostID() + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(currentStoryCard.getPostID(), "jpg");
            View finalListItem = listItem;
            reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                ((ImageView)finalListItem.findViewById(R.id.postImage)).setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView userName = listItem.findViewById(R.id.userName);
        userName.setText(currentStoryCard.getName());

        TextView caption = listItem.findViewById(R.id.caption);
        caption.setText(currentStoryCard.getCaption());

        TextView likes = listItem.findViewById(R.id.likes);
        likes.setText(Integer.toString(currentStoryCard.getLikes()));

        return listItem;
    }
}
