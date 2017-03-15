package com.bean;

import android.os.MessageQueue;

/**
 * Created by Vorringer on 2016/7/8.
 */
public class Position {
    private int ID;
    private double longitude;
    private double latitude;

    public Position(int ID,double longitude,double latitude) {
        this.ID=ID;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public int getID(){return ID;}
    public void setID(int ID){this.ID=ID;}
    public double getLongitude(){return longitude;}
    public void setLongitude(double longitude){this.longitude=longitude;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double latitude){this.latitude=latitude;}
}
