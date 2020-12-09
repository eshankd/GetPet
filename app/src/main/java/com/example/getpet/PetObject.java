package com.example.getpet;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class PetObject {


    // declaring all variables that will be used in the functions below
    String petID, name, type,breed,gender,description,userEmail;
    int age;

    public PetObject() { }

    //default constructor while creating a pet object
    public PetObject(String petID,String name,String userEmail, String type, String breed, String gender, int age, String description)   {

        this.petID = petID;
        this.name = name;
        this.userEmail = userEmail;
        this.type = type;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    //function that gets the petID of the pet
    public String getPetID(){return petID;    }

    //function that gets the name of the pet
    public String getName() { return name; }

    //function that gets the type of the pet
    public String getType() {return type;}

    //function that gets the userEmail of the owner of the pet
    public  String getUserEmail() {return userEmail;}

    //function that gets the breed of the pet
    public String getBreed() {
        return breed;
    }

    //function that gets the gender of the pet
    public String getGender() {
        return gender;
    }

    // function that gets the Age of the pet
    public int getAge() {  return age;    }

    //function that gets the description of the pet profile
    public String getDescription(){return description;}

    //function that sets the name of the pet
    public void setName(String name) { this.name = name; }

    // function that sets the Gender of the pet
    public void setGender(String gender) {
        this.gender = gender;
    }
}
