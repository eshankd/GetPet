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
        Log.d("postID", currentStoryCard.getPostID());
        StorageReference reference = storage.getReference().child("Storyboard Images/" + currentStoryCard.getPostID() + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(currentStoryCard.getPostID(), "jpg");
            View finalListItem = listItem;
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(mContext, "Picture Retrieved", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView)finalListItem.findViewById(R.id.postImage)).setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView name = listItem.findViewById(R.id.petName);
        name.setText(currentStoryCard.getName());

        TextView userName = listItem.findViewById(R.id.userName);
        userName.setText(currentStoryCard.getCaption());

        TextView timeAgo = listItem.findViewById(R.id.timeAgo);
        timeAgo.setText(currentStoryCard.getTime());

        TextView numberOfLikes = listItem.findViewById(R.id.numberOfLikes);
        numberOfLikes.setText(currentStoryCard.getLikes());

        return listItem;
    }
}