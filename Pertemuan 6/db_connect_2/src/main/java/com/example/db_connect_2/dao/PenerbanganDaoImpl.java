package com.example.db_connect_2.dao;

import com.example.db_connect_2.entity.Negara;
import com.example.db_connect_2.entity.Penerbangan;
import com.example.db_connect_2.util.DaoService;
import com.example.db_connect_2.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenerbanganDaoImpl implements DaoService<Penerbangan> {
    @Override
    public List<Penerbangan> fetchAll() throws SQLException, ClassNotFoundException {
        List<Penerbangan> penerbangans = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT p.idPenerbangan,p.HargaPenerbangan,p.Negara_idAsal,p.Negara_idTujuan,p.Maskapai,n.Negara AS negara_asal,nn.Negara AS negara_tujuan FROM penerbangan p JOIN negara n ON p.Negara_idAsal = n.idNegara JOIN negara nn ON p.Negara_idTujuan = nn.idNegara";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Negara negara_asal = new Negara();
            Negara negara_tujuan = new Negara();
            negara_asal.setNegaraAsal(rs.getString("negara_asal"));
            negara_tujuan.setNegaraTujuan(rs.getString("negara_tujuan"));

            Penerbangan penerbangan = new Penerbangan();
            penerbangan.setPenerbanganId(rs.getInt("idPenerbangan"));
            penerbangan.setPenerbanganAsal(negara_asal); // FK
            penerbangan.setPenerbanganTujuan(negara_tujuan); // FK
            penerbangan.setPenerbanganHarga(rs.getFloat("HargaPenerbangan"));
            penerbangan.setPenerbanganMaskapai(rs.getString("Maskapai"));

            penerbangans.add(penerbangan);
        }
        rs.close();
        ps.close();
        connection.close();
        return penerbangans;
    }
    // fetchAllMaskapai dibikin distrinct supaya tdk ada duplicate value tidak perlu pakai @override
    // dipakai di TambahPenerbanganController supaya maskapainya gak double
    // toString di penerbangan --> penerbanganMaskapai

    public List<Penerbangan> fetchAllMaskapai() throws SQLException, ClassNotFoundException {
        List<Penerbangan> penerbangans = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        // group by nama maskapai
        String query = "SELECT DISTINCT p.idPenerbangan,p.HargaPenerbangan,p.Negara_idAsal,p.Negara_idTujuan,p.Maskapai,n.Negara AS negara_asal,nn.Negara AS negara_tujuan FROM penerbangan p JOIN negara n ON p.Negara_idAsal = n.idNegara JOIN negara nn GROUP BY p.Maskapai";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Negara negara_asal = new Negara();
            Negara negara_tujuan = new Negara();
            negara_asal.setNegaraAsal(rs.getString("negara_asal"));
            negara_tujuan.setNegaraTujuan(rs.getString("negara_tujuan"));

            Penerbangan penerbangan = new Penerbangan();
            penerbangan.setPenerbanganId(rs.getInt("idPenerbangan"));
            penerbangan.setPenerbanganAsal(negara_asal); // FK
            penerbangan.setPenerbanganTujuan(negara_tujuan); // FK
            penerbangan.setPenerbanganHarga(rs.getFloat("HargaPenerbangan"));
            penerbangan.setPenerbanganMaskapai(rs.getString("Maskapai"));

            penerbangans.add(penerbangan);
        }
        rs.close();
        ps.close();
        connection.close();
        return penerbangans;
    }
    @Override
    public int addData(Penerbangan object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO penerbangan(HargaPenerbangan, Negara_idAsal, Negara_idTujuan, Maskapai) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setFloat(1,object.getPenerbanganHarga());
        ps.setInt(2,object.getPenerbanganAsal().getNegaraId()); // FK
        ps.setInt(3,object.getPenerbanganTujuan().getNegaraId()); // FK
        ps.setString(4,object.getPenerbanganMaskapai());
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
    public int updateData(Penerbangan object) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int deleteData(Penerbangan object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM Penerbangan WHERE idPenerbangan = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getPenerbanganId());
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
    public List<Penerbangan> fetchFilter(Penerbangan object) throws SQLException, ClassNotFoundException {
        return null;
    }
}
