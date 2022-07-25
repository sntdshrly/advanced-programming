package com.example.db_connect.dao;

import com.example.db_connect.entity.KategoriTransaksi;
import com.example.db_connect.util.DaoService;
import com.example.db_connect.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriTransaksiDaoImpl implements DaoService<KategoriTransaksi> {
    @Override
    public List<KategoriTransaksi> fetchAll() throws SQLException, ClassNotFoundException {
        List<KategoriTransaksi> kategoritransactions = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT id,nama,tipe_kategori FROM tbkategoritransaksi";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            KategoriTransaksi kategoritransaction = new KategoriTransaksi();
            kategoritransaction.setId(rs.getInt("id"));
            kategoritransaction.setNama(rs.getString("nama"));
            kategoritransaction.setTipeKategori(rs.getInt("tipe_kategori"));
            kategoritransactions.add(kategoritransaction);
        }
        rs.close();
        ps.close();
        connection.close();
        return kategoritransactions;
    }

    @Override
    public int addData(KategoriTransaksi object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO tbkategoritransaksi(nama,tipe_kategori) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getNama());
        ps.setInt(2,object.getTipeKategori());
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
    public int deleteData(KategoriTransaksi object) throws SQLException, ClassNotFoundException {
        return 0;
    }
}
