package com.example.quiztaker;

import java.io.Serializable;

// Class that represents a user
public class User implements Serializable {

    private String username;
    private String password;
    private String email;
    private String phone;
    private int id;


    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(int id, String username) {
        this.username = username;
        this.id = id;
    }

    public int getId() { return id; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
