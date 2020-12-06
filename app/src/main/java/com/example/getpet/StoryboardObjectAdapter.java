package com.example.getpet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryboardObjectAdapter extends ArrayAdapter<StoryboardObject> {

    User user = User.getInstance();

    private Context mContext;
    private List<StoryboardObject> storyboardObjectList;

    private Button likeBtn;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public StoryboardObjectAdapter(Context context, ArrayList<StoryboardObject> list) {
        super(context, 0, list);
        mContext = context;
        storyboardObjectList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.storyboard_layout, parent, false);

        StoryboardObject currentStoryCard = storyboardObjectList.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Log.d("postID", currentStoryCard.getPostID());

        StorageReference reference = storage.getReference().child("Storyboard Thumbnails/" + currentStoryCard.getPostID() + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(currentStoryCard.getPostID(), "jpg");
            View finalListItem = listItem;
            reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                ((ImageView) finalListItem.findViewById(R.id.postImage)).setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView userName = listItem.findViewById(R.id.userName);
        userName.setText(currentStoryCard.getName());

        TextView caption = listItem.findViewById(R.id.caption);
        caption.setText(currentStoryCard.getCaption());

//        TextView timeAgo = listItem.findViewById(R.id.timeAgo);
//        timeAgo.setText(currentStoryCard.getTime());

        TextView likes = listItem.findViewById(R.id.likes);
        likes.setText(Integer.toString(currentStoryCard.getLikes()));

        likeBtn = listItem.findViewById(R.id.likeButton);
        final Button localButton = likeBtn;
        if(currentStoryCard.isLiked){
            likeBtn.setText("Unlike");
        } else {
            likeBtn.setText("Like");
        }


        likeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                DocumentReference docRef = fStore.collection("Posts").document(currentStoryCard.getPostID());

                if(!currentStoryCard.isLiked){
                    docRef.update("Likes", FieldValue.arrayUnion(user.getEmail()));
                    localButton.setText("Unlike");
                    currentStoryCard.like();
                } else {
                    docRef.update("Likes", FieldValue.arrayRemove(user.getEmail()));
                    localButton.setText("Like");
                    currentStoryCard.unlike();
                }
                likes.setText(Integer.toString(currentStoryCard.getLikes()));


//                {
//                    Log.d("postID", currentStoryCard.getPostID());
//                    currentStoryCard.addLikes();
//                    int temp = (currentStoryCard.getLikes());
//                    likes.setText(Integer.toString(temp));
//                }
            }
        });
        return listItem;
    }
}
