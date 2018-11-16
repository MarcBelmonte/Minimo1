package edu.upc.eetac.dsa;

import java.util.LinkedList;

public class User {
    String idUser;
    String name;
    String surname;
    private LinkedList<Bike> bikeList;

    public User(String idUser, String name, String surname) {
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.bikeList = new LinkedList<>();
    }
    public User(String idUser){
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.bikeList = new LinkedList<>();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LinkedList<Bike> getBikeList() {
        return bikeList;
    }

    public void addBike(Bike b){
        bikeList.add(b);
    }
}
