package com.example.getpet;

public class User {
    private String fName;
    private String lName;
    private int petsOwned;
    private String email;

    //private static final User instance = new User();

    private static User single_instance = null;

//    public static User getInstance() {
//        return instance;
//    }

    User () { }

    public static User getInstance(){
        if (single_instance == null)
            single_instance = new User();

        return single_instance;
    }

    public void setData(String fName, String lName, int petsOwned, String email) {
        this.fName = fName;
        this.lName = lName;
        this.petsOwned = petsOwned;
        this.email = email;
    }

    // Getter/setter
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
        return petsOwned;
    }

    public String getEmail() {
        return email;
    }
}