package com.example.getpet;

import java.util.ArrayList;

// User class that is created once the user lands on the homepage for the first time
// Either contains user data or numPetsOwned = -1 for guest Users

public class User {

    private String userID;
    private String fName;
    private String lName;
    private ArrayList<String> petsOwned;
    private int numPetsOwned;
    private String email;



    private static User single_instance = null;

//    Default constructor
    User () {
        fName = "Guest";
        lName = "User";
        numPetsOwned = -1;
    }

//    Function called when user signs out
    public void reset(){
        fName = "Guest";
        lName = "User";
        numPetsOwned = -1;
    }


//    Function to check if there is an existing instance of User
    public static User getInstance(){
        if (single_instance == null)
            single_instance = new User();

        return single_instance;
    }

//    Function used to populate singleton on first initialization
    public void setData(String userID ,String fName, String lNamee, ArrayList<String> petsOwned, String email) {

        this.userID =userID;
        this.fName = fName;
        this.lName = lName;
        this.petsOwned = petsOwned;
        this.email = email;
        numPetsOwned = petsOwned.size();
    }

    // Getters/setters

    public String getUserID(){return userID;}

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public int getPetsOwned() {
        return numPetsOwned;
    }

    public String getEmail() {
        return email;
    }

//    Function called when a user adopts a pet to update the objects variables
    public void adoptPet(String pID) {

        petsOwned.add(pID);
        numPetsOwned++;
    }

    public boolean isOwned(String pID)
    {
        return petsOwned.contains(pID);
    }




}