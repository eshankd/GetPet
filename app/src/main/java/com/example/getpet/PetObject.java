package com.example.getpet;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class PetObject {

    String petID;
    String name;
    String type;
    String breed;
    String gender;
    String description;
    int age;


    public PetObject()
    {

    }


    public PetObject(String petID,String name,String type, String breed, String gender, int age, String description)   {


        this.petID = petID;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    public String getPetID(){return petID;    }

    public String getName() { return name; }

    public String getType() {return type;}

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {  return age;    }

    public String getDescription(){return description;}

    public void setName(String name) { this.name = name; }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
