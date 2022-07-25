package com.example.db_connect.dao;

import com.example.db_connect.entity.KategoriTransaksi;
import com.example.db_connect.entity.Transaksi;
import com.example.db_connect.util.DaoService;
import com.example.db_connect.util.MySQLConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDaoImpl implements DaoService<Transaksi> {
    @Override
    public List<Transaksi> fetchAll() throws SQLException, ClassNotFoundException {
        List<Transaksi> transactions = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT t.id,t.nama,t.jumlah,t.tbKategoriTransaksi_id,k.nama AS nama_kategori FROM tbtransaksi t JOIN tbkategoritransaksi k ON t.tbKategoriTransaksi_id = k.id";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            KategoriTransaksi kategoriTransaksi = new KategoriTransaksi();
            kategoriTransaksi.setTipeKategori(rs.getInt("tbKategoriTransaksi_id"));
            kategoriTransaksi.setNama(rs.getString("nama_kategori"));

            Transaksi transaction = new Transaksi();
            transaction.setId(rs.getInt("id"));
            transaction.setNama(rs.getString("nama"));
            transaction.setJumlah(rs.getInt("jumlah"));
            transaction.setKategoriTransaksi(kategoriTransaksi);

            transactions.add(transaction);
        }
        rs.close();
        ps.close();
        connection.close();
        return transactions;
    }

    @Override
    public int addData(Transaksi object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO tbtransaksi(nama, jumlah, tbKategoriTransaksi_id) VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getNama());
        ps.setInt(2,object.getJumlah());
        ps.setInt(3,object.getKategoriTransaksi().getId());
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
    public int deleteData(Transaksi object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM tbtransaksi WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getId());
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
}
