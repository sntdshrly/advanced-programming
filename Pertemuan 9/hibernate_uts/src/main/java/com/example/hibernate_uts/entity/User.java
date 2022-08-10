package com.example.hibernate_uts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUser")
    private int idUser;
    @Basic
    @Column(name = "UserName")
    private String userName;
    @Basic
    @Column(name = "UserPassword")
    private String userPassword;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser && Objects.equals(userName, user.userName) && Objects.equals(userPassword, user.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, userName, userPassword);
    }

    public User(int idUser, String userName, String userPassword) {
        this.idUser = idUser;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    public User() {
    }

    @Override
    public String toString() {
        return userName;
    }
}
