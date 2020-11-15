package com.example.getpet;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class PetObject {

    String petID;
    String name;
    String breed;
    String gender;
    String description;
    int age;


    public PetObject(String petID,String name, String breed, String gender, int age, String description)   {

        this.petID = petID;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    public String getPetID(){return petID;    }

    public String getName() { return name; }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {  return age;    }

    public String getDescription(){return description;}

    public void setPetID(String petID){this.petID = petID;}

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

    public void setDescription(String description){this.description = description;}
}
