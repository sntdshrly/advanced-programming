package com.example.uts_old.dao;

import com.example.uts_old.entity.Hutang;
import com.example.uts_old.entity.Player;
import com.example.uts_old.util.DaoService;
import com.example.uts_old.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HutangDaoImpl implements DaoService<Hutang> {
    @Override
    public int addData(Hutang object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO hutang(Player_id,PemberiUtang,Jumlah) VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getPlayer().getIdPlayer());
        ps.setString(2,object.getPemberiUtang());
        ps.setDouble(3,object.getJumlah());
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
    public int deleteData(Hutang object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM hutang WHERE id = ?";
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

    @Override
    public int updateData(Hutang object) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public List fetchAll() throws SQLException, ClassNotFoundException {
        List<Hutang> hutangs = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
/**        SELECT h.id, h.PemberiUtang, h.jumlah, h.player_id,
 *         p.id,p.Nama AS namaPlayer
 *         FROM hutang h
 *         JOIN player p
 *         ON h.Player_id = p.id **/
        String query = "SELECT h.id, h.PemberiUtang, h.jumlah, h.player_id, p.id,p.Nama AS namaPlayer FROM hutang h JOIN player p ON h.Player_id = p.id";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Hutang hutang = new Hutang();
            hutang.setId(rs.getInt("id"));
            hutang.setPemberiUtang(rs.getString("PemberiUtang"));
            hutang.setJumlah(rs.getInt("Jumlah"));
            Player player = new Player(); // Bikin objek player
            player.setIdPlayer(rs.getInt("id")); // ngambil id player
            hutang.setPlayer(player); // FK
            hutangs.add(hutang);
        }
        rs.close();
        ps.close();
        connection.close();
        return hutangs;
    }
}
