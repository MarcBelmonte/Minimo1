package edu.upc.eetac.dsa;

import java.util.LinkedList;
import java.util.List;

public class Station {
    String idStation;
    private String description;
    private int max;
    private double lat;
    private double lon;
    private LinkedList<Bike> inv;

    public Station(List<Bike> inv){this.inv = new LinkedList<Bike>(inv);
    }

    public Station(String idStation, String description, int max, double lat, double lon) {
        this.idStation = idStation;
        this.description = description;
        this.max = max;
        this.lat = lat;
        this.lon = lon;
        this.inv = new LinkedList<>();
    }

    public Station() {
    }

    public Station(String idStation){
        this.idStation = idStation;
        this.inv = new LinkedList<>();
    }

    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public LinkedList<Bike> getMyBikes(){
        return this.inv;
    }
    public void addBike(Bike bike){
        this.inv.add(bike);

    }
    public List<Bike> getListaBikes(){
        return this.inv;
    }
}

