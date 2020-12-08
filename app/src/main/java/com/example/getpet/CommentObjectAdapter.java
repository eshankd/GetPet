package com.example.getpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.List;


public class CommentObjectAdapter extends ArrayAdapter<CommentObject> {

    User user = User.getInstance();
    private Context mContext;
    private List<CommentObject> commentObjectList;
    private Button postComment;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public CommentObjectAdapter(Context context, ArrayList<CommentObject> list) {
        super(context, 0, list);
        mContext = context;
        commentObjectList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.comments_layout, parent, false);

        CommentObject currentCommentCard = commentObjectList.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();


        TextView commenter = listItem.findViewById(R.id.commenter);
        commenter.setText(currentCommentCard.getFromUser());

        TextView comment = listItem.findViewById(R.id.comment);
        comment.setText(currentCommentCard.getMessage());

        return listItem;
    }
}

