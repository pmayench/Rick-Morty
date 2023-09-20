package com.dam2.m08.rickmortyapp;

public class Character
{
    //region Variables
    String name, status, specie, type, gender, image;
    int id;
    //endregion

    //region Constructors
    public Character(String name, String status, String specie, String type, String gender, String image, int id)
    {
        this.name = name;
        this.status = status;
        this.specie = specie;
        this.type = type;
        this.gender = gender;
        this.image = image;
        this.id = id;
    }
    public Character(){}
    //endregion

    //region Getters i Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getSpecie() {return specie;}
    public void setSpecie(String specie) {this.specie = specie;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    //endregion
}
