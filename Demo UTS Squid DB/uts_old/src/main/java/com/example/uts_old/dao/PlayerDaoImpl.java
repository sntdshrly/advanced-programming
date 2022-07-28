package com.example.uts_old.dao;

import com.example.uts_old.entity.Player;
import com.example.uts_old.util.DaoService;
import com.example.uts_old.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements DaoService<Player> {
    @Override
    public int addData(Player object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO Player(id,Nama,Umur,Keahlian) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdPlayer());
        ps.setString(2,object.getNama());
        ps.setInt(3,object.getUmur());
        ps.setString(4,object.getKeahlian());
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
    public int deleteData(Player object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM Player WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdPlayer());
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
    public int updateData(Player object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE Player SET Nama = ? , Umur = ? , Keahlian = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getNama());
        ps.setInt(2,object.getUmur());
        ps.setString(3,object.getKeahlian());
        ps.setInt(4,object.getIdPlayer());
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
    public List fetchAll() throws SQLException, ClassNotFoundException {
        List<Player> players = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT id, Nama, Umur, Keahlian FROM Player";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Player player = new Player();
            player.setIdPlayer(rs.getInt("id"));
            player.setNama(rs.getString("Nama"));
            player.setUmur(rs.getInt("Umur"));
            player.setKeahlian(rs.getString("Keahlian"));
            players.add(player);
        }
        rs.close();
        ps.close();
        connection.close();
        return players;
    }
}
