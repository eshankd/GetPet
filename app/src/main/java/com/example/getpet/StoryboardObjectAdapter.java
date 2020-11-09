package com.example.getpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
