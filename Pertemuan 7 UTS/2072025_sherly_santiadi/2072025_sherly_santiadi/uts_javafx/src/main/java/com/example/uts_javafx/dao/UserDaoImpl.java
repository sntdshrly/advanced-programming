package com.example.uts_javafx.dao;

import com.example.uts_javafx.entity.Movie;
import com.example.uts_javafx.entity.User;
import com.example.uts_javafx.util.DaoService;
import com.example.uts_javafx.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements DaoService<User> {
    @Override
    public int addData(User object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO User(idUser,Username,UserPassword) VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdUser());
        ps.setString(2,object.getUserName());
        ps.setString(3,object.getUserPassword());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public int deleteData(User object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM User WHERE idUser = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdUser());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public int updateData(User object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE User SET UserName=? , UserPassword=? WHERE idUser = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getUserName());
        ps.setString(2,object.getUserPassword());
        ps.setInt(3,object.getIdUser());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public List<User> fetchAll() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT * FROM User";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            User user = new User();
            user.setIdUser(rs.getInt("idUser"));
            user.setUserName(rs.getString("UserName"));
            user.setUserPassword(rs.getString("UserPassword"));
            users.add(user);
        }
        rs.close();
        ps.close();
        connection.close();
        return users;
    }
}
