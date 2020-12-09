package com.example.getpet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class PetObjectAdapter extends ArrayAdapter<PetObject> {
    private Context mContext;
    private List<PetObject> petObjectList;


    // object adapter constructor
    public PetObjectAdapter(Context context, ArrayList<PetObject> list) {
        super(context, 0, list);
        mContext = context;
        petObjectList = list;
    }


    //function to get the view of all the pets from the array list of pets
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
           listItem = LayoutInflater.from(mContext).inflate(R.layout.pet_list_layout, parent, false);

        PetObject currentPet = petObjectList.get(position);


        //retrieving images from firebase to be used to display for all the pets in the pet list view
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("Pet Images/" + currentPet.getPetID() + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(currentPet.getPetID(), "jpg");
            View finalListItem = listItem;
            reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                ((ImageView)finalListItem.findViewById(R.id.petImage)).setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

// displaying all the retieved information into the layout inflator and returning the list item
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
