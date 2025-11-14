package com.example.jedyinstrumenttm.data.model;

public class User {
    public String username, email, password, phoneNumber;
    public int userID;

    public User (int userID, String username, String email, String password, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
