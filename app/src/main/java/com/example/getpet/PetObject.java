package com.example.getpet;

public class PetObject {

    String petID;
    String name;
    String type;
    String breed;
    String gender;
    String description;
    String userEmail;
    int age;


    public PetObject()
    {

    }
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

    public String getPetID(){return petID;    }

    public String getName() { return name; }

    public String getType() {return type;}

    public  String getUserEmail() {return userEmail;}

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
