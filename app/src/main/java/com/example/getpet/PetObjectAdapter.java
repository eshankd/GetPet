package com.example.getpet;

import android.content.Context;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.storage.StorageReference;
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
           listItem = LayoutInflater.from(mContext).inflate(R.layout.pet_list_layout, parent, false);

        PetObject currentPet = petObjectList.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("D001.jpg");

        ImageView petImage = listItem.findViewById(R.id.petImage);
        Glide.with(mContext)
                .load(reference)
                .into(petImage);


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
