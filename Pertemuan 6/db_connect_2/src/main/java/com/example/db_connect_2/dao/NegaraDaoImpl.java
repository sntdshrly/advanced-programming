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

public class NegaraDaoImpl implements DaoService<Negara> {
    @Override
    public List<Negara> fetchAll() throws SQLException, ClassNotFoundException {
        List<Negara> negaras = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT idNegara, Negara, ButuhVisa FROM negara";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Negara negara = new Negara();
            negara.setNegaraId(rs.getInt("idNegara"));
            negara.setNegara(rs.getString("Negara"));
            negara.setNegaraVisa(rs.getBoolean("ButuhVisa"));
            negaras.add(negara);
        }
        rs.close();
        ps.close();
        connection.close();
        return negaras;
    }

    @Override
    public int addData(Negara object) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int updateData(Negara object) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int deleteData(Negara object) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public List<Negara> fetchFilter(Negara object) throws SQLException, ClassNotFoundException {
        return null;
    }

    // ini fungsi bikin baru bukan dari daoservice
    public List<Penerbangan> fetchFilters(Negara object) throws SQLException, ClassNotFoundException {
        List<Penerbangan> penerbangans = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT p.idPenerbangan,p.HargaPenerbangan,p.Negara_idAsal AS negara_asal_id,p.Negara_idTujuan AS negara_tujuan_id,p.Maskapai,n.Negara AS negara_asal,nn.Negara AS negara_tujuan " +
                "FROM penerbangan p JOIN negara n ON p.Negara_idAsal = n.idNegara JOIN negara nn ON p.Negara_idTujuan = nn.idNegara " +
                "WHERE n.Negara = " + '"'+ object.getNegara() + '"' + " OR nn.Negara = " + '"'+ object.getNegara() + '"';

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
//        while(rs.next()) {
//            ps.setString(1, object.getNegaraAsal()); // FK
//            ps.setString(2, object.getNegaraTujuan()); // FK
//        }
        while(rs.next()){
            Penerbangan penerbangan = new Penerbangan();
            Negara negaraAsal = new Negara(); // buat objek negara asal soalnya kalo digabungin "negara" aja nanti dia ngefetchingnya negara asal sama negara tujuan sama valuenya.
            Negara negaraTujuan = new Negara(); // buat objek negara asal

            penerbangan.setPenerbanganId(rs.getInt("idPenerbangan"));
            penerbangan.setPenerbanganHarga(rs.getFloat("HargaPenerbangan"));
            String namaNegaraAsal = rs.getString("negara_asal");
            String namaNegaraTujuan = rs.getString("negara_tujuan");
            penerbangan.setPenerbanganMaskapai(rs.getString("Maskapai"));
            negaraAsal.setNegaraAsal(namaNegaraAsal);
            negaraTujuan.setNegaraTujuan(namaNegaraTujuan);
            penerbangan.setPenerbanganAsal(negaraAsal);
            penerbangan.setPenerbanganTujuan(negaraTujuan);
            penerbangans.add(penerbangan);

        }

        rs.close();
        ps.close();
        connection.close();
        return penerbangans;
    }
}
