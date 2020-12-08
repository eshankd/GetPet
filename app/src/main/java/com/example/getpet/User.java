package com.example.getpet;

import java.util.ArrayList;

public class User {

    private String userID;
    private String fName;
    private String lName;
    private ArrayList<String> petsOwned;
    private int numPetsOwned;
    private String email;



    private static User single_instance = null;

    User () {
        fName = "Guest";
        lName = "User";
        numPetsOwned = -1;
    }

    public static User getInstance(){
        if (single_instance == null)
            single_instance = new User();

        return single_instance;
    }

    public void setData(String userID ,String fName, String lName, ArrayList<String> petsOwned, String email) {

        this.userID =userID;
        this.fName = fName;
        this.lName = lName;
        this.petsOwned = petsOwned;
        this.email = email;
        numPetsOwned = petsOwned.size();
    }

    // Getter/setter

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

    public void adoptPet(String pID) {

        petsOwned.add(pID);
        numPetsOwned++;
    }

    public boolean isOwned(String pID)
    {
        return petsOwned.contains(pID);
    }




}