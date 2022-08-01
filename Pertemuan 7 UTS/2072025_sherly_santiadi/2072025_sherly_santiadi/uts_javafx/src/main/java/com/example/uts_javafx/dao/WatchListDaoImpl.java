package com.example.uts_javafx.dao;

import com.example.uts_javafx.entity.Movie;
import com.example.uts_javafx.entity.User;
import com.example.uts_javafx.entity.WatchList;
import com.example.uts_javafx.util.DaoService;
import com.example.uts_javafx.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchListDaoImpl implements DaoService<WatchList> {
    @Override
    public int addData(WatchList object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO WatchList(idWatchList,LastWatch,Favorite,Movie_idMovie,User_idUser) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdWatchList());
        ps.setInt(2,object.getLastWatchList());
        ps.setInt(3,object.getFavoriteWatchList());
        ps.setInt(4,object.getMovieId().getIdMovie()); // FK
        ps.setInt(5,object.getUserId().getIdUser()); // FK
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
    public int deleteData(WatchList object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM WatchList WHERE idWatchList = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdWatchList());
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
    public int updateData(WatchList object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE WatchList SET LastWatch= ? ,Favorite = ? , Movie_idMovie = ?  , User_idUser = ? WHERE idWatchList = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getLastWatchList());
        ps.setInt(2,object.getFavoriteWatchList());
        ps.setInt(3,object.getMovieId().getIdMovie()); //FK
        ps.setInt(4,object.getUserId().getIdUser()); //FK
        ps.setInt(5,object.getIdWatchList());
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
    public List<WatchList> fetchAll() throws SQLException, ClassNotFoundException {
        List<WatchList> watchLists = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT w.idWatchList, w.LastWatch, w.Favorite, w.Movie_idMovie, w.User_idUser, m.Title AS judulMovie , u.UserName AS namaUser\n" +
                "FROM watchlist w\n" +
                "JOIN movie m ON w.Movie_idMovie = m.idMovie\n" +
                "JOIN user u ON w.User_idUser = u.idUser";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            WatchList watchList = new WatchList();
            watchList.setIdWatchList(rs.getInt("idWatchList"));
            watchList.setLastWatchList(rs.getInt("LastWatch"));
            watchList.setFavoriteWatchList(rs.getInt("Favorite"));

            Movie movie = new Movie(); // Bikin objek movie
            movie.setTitleMovie(rs.getString("judulMovie")); // ngambil judul movie
            watchList.setMovieId(movie);

            User user = new User(); // Bikin objek movie
            user.setUserName(rs.getString("namaUser")); // ngambil judul movie
            watchList.setUserId(user);

            watchLists.add(watchList);
        }
        rs.close();
        ps.close();
        connection.close();
        return watchLists;
    }

}
