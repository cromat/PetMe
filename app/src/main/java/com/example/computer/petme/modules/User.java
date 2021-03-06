package com.example.computer.petme.modules;

/**
 * Created by computer on 18.4.2015..
 */

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String lokacija;

    public User() {
    }

    public User(int id, String name, String username, String email, String phone, String lokacija) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.lokacija = lokacija;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLokacija() {return lokacija;}

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}