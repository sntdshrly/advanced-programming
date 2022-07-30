package com.example.latihan_mandiri.dao;

import com.example.latihan_mandiri.entity.Dosen;
import com.example.latihan_mandiri.entity.Mahasiswa;
import com.example.latihan_mandiri.util.DaoService;
import com.example.latihan_mandiri.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDaoImpl implements DaoService<Mahasiswa> {
    @Override
    public int addData(Mahasiswa object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO tbMahasiswa(id_mahasiswa,nama_mahasiswa,alamat_mahasiswa,tbDosenWali_id_dosen) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdMahasiswa());
        ps.setString(2,object.getNamaMahasiswa());
        ps.setString(3,object.getAlamatMahasiswa());
        ps.setInt(4,object.getDosen().getIdDosen());
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
    public int deleteData(Mahasiswa object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM tbMahasiswa WHERE id_mahasiswa = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdMahasiswa());
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
    public int updateData(Mahasiswa object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE tbMahasiswa SET nama_mahasiswa= ? ,alamat_mahasiswa = ? , tbDosenWali_id_dosen = ? WHERE id_mahasiswa = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getNamaMahasiswa());
        ps.setString(2,object.getAlamatMahasiswa());
        ps.setInt(3,object.getDosen().getIdDosen());
        ps.setInt(4,object.getIdMahasiswa());
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
    public List<Mahasiswa> fetchAll() throws SQLException, ClassNotFoundException {
        List<Mahasiswa> mahasiswas = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT m.id_mahasiswa, m.nama_mahasiswa, m.alamat_mahasiswa, m.tbDosenWali_id_dosen, d.id_dosen,d.nama_dosen FROM tbMahasiswa m JOIN tbdosenwali d ON m.tbDosenWali_id_dosen = d.id_dosen";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setIdMahasiswa(rs.getInt("id_mahasiswa"));
            mahasiswa.setNamaMahasiswa(rs.getString("nama_mahasiswa"));
            mahasiswa.setAlamatMahasiswa(rs.getString("alamat_mahasiswa"));
            Dosen dosen = new Dosen(); // Bikin objek player
            dosen.setNamaDosen(rs.getString("nama_dosen")); // ngambil nama dosen
            mahasiswa.setDosen(dosen);
            mahasiswas.add(mahasiswa);
        }
        rs.close();
        ps.close();
        connection.close();
        return mahasiswas;
    }
}
