package com.bean;

/**
 * Created by Vorringer on 2016/9/7.
 */
public class Shop {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An autogenerated id (unique for each Shop in the db)

    private long id;

    //The Shop's status

    private int status;

    //The Shop's info
    private String info;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Shop() { }

    public Shop(long id) {
        this.id = id;
    }

    public Shop(short status,String info) {
        this.status = status;
        this.info=info;
    }

    // Getter and setter methods

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String value) {
        this.info = value;
    }

} // class Shop