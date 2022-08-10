package com.example.file_io.entity;

public class User {
    public String username;
    public String comment;

    public User(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return username + " - " + comment;
    }
}
