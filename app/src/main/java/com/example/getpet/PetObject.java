package com.example.getpet;

public class PetObject {
    String name;
    String breed;
    String gender;
    int age;

    public PetObject(String name, String breed, String gender, int age)   {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
