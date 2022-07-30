package com.example.latihan_mandiri.dao;

import com.example.latihan_mandiri.entity.Dosen;
import com.example.latihan_mandiri.util.DaoService;
import com.example.latihan_mandiri.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DosenDaoImpl implements DaoService<Dosen> {
    @Override
    public int addData(Dosen object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO tbDosenWali(id_dosen,nama_dosen) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdDosen());
        ps.setString(2,object.getNamaDosen());
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
    public int deleteData(Dosen object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM tbDosenWali WHERE id_dosen = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdDosen());
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
    public int updateData(Dosen object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE tbDosenWali SET nama_dosen = ? WHERE id_dosen = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getNamaDosen());
        ps.setInt(2,object.getIdDosen());
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
        List<Dosen> dosens = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT id_dosen, nama_dosen FROM tbDosenWali";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Dosen dosen = new Dosen();
            dosen.setIdDosen(rs.getInt("id_dosen"));
            dosen.setNamaDosen(rs.getString("nama_dosen"));
            dosens.add(dosen);
        }
        rs.close();
        ps.close();
        connection.close();
        return dosens;
    }
}
