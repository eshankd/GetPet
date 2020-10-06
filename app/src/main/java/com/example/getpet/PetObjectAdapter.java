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

public class PetObjectAdapter extends ArrayAdapter<PetObject> {
    private Context mContext;
    private List<PetObject> petObjectList;

    public PetObjectAdapter(Context context, ArrayList<PetObject> list) {
        super(context, 0, list);
        mContext = context;
        petObjectList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.dog_list_layout, parent, false);

        PetObject currentPet = petObjectList.get(position);

        TextView name = listItem.findViewById(R.id.petName);
        name.setText(currentPet.getName());

        TextView age = listItem.findViewById(R.id.petAge);
        age.setText(Integer.toString(currentPet.getAge()));

        TextView gender = listItem.findViewById(R.id.petGender);
        gender.setText(currentPet.getGender());

        TextView breed = listItem.findViewById(R.id.petBreed);
        breed.setText(currentPet.getBreed());

        return listItem;
    }
}
