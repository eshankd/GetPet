package com.example.getpet;

import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PetObject {

    ImageReader petPic;
    String name;
    String breed;
    String gender;
    int age;

    public PetObject(ImageReader petPic,String name, String breed, String gender, int age)   {
        this.petPic = petPic;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }

    public ImageReader getPetPic() { return petPic; }

    public String getName() { return name; }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) { this.name = name; }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPetPic(ImageReader petPic) {
        this.petPic = petPic;
    }
}
