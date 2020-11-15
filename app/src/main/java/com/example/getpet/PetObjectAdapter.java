package com.example.getpet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
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
import androidx.core.provider.FontsContractCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
        Log.d("petID", currentPet.getPetID());
        StorageReference reference = storage.getReference().child("Dog Thumbnails/" + currentPet.getPetID() + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(currentPet.getPetID(), "jpg");
            View finalListItem = listItem;
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(mContext, "Picture Retrieved", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView)finalListItem.findViewById(R.id.petImage)).setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView name = listItem.findViewById(R.id.petName);
        name.setText(currentPet.getName());

        TextView age = listItem.findViewById(R.id.petAge);
        age.setText(Integer.toString(currentPet.getAge()));

        TextView gender = listItem.findViewById(R.id.petGender);
        gender.setText(currentPet.getGender());

        TextView breed = listItem.findViewById(R.id.petBreed);
        breed.setText(currentPet.getBreed());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listItem;
    }
}
