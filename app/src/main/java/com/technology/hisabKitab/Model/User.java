package com.technology.hisabKitab.Model;

public class User {

    private String fname;
    private String lname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public User(String id,String fname, String lname) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
    }
    public User() {
        this.fname = fname;
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }



}
