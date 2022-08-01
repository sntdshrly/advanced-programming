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

public class MovieDaoImpl implements DaoService<Movie> {
    @Override
    public int addData(Movie object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO Movie(idMovie,Title,Genre,Durasi) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdMovie());
        ps.setString(2,object.getTitleMovie());
        ps.setString(3,object.getGenreMovie());
        ps.setInt(4,object.getDurasiMovie());
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
    public int deleteData(Movie object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM Movie WHERE idMovie = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getIdMovie());
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
    public int updateData(Movie object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE Movie SET Title=? , Genre=? , Durasi=? WHERE idMovie = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getTitleMovie());
        ps.setString(2,object.getGenreMovie());
        ps.setInt(3,object.getDurasiMovie());
        ps.setInt(4,object.getIdMovie());
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
    public List<Movie> fetchAll() throws SQLException, ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT * FROM Movie";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Movie movie = new Movie();
            movie.setIdMovie(rs.getInt("idMovie"));
            movie.setTitleMovie(rs.getString("Title"));
            movie.setGenreMovie(rs.getString("Genre"));
            movie.setDurasiMovie(rs.getInt("Durasi"));
            movies.add(movie);
        }
        rs.close();
        ps.close();
        connection.close();
        return movies;
    }
    // ini sql ketika usernya sudah diklik tapi filter genrenya masih all
    public List<Movie> fetchFilterMovie(User object) throws SQLException, ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT w.idWatchList, w.LastWatch, w.Favorite, w.Movie_idMovie, w.User_idUser, u.UserName AS namaUser, m.Title AS judulMovie , m.Genre AS genreMovie, m.Durasi AS durasiMovie\n" +
                "FROM watchlist w\n" +
                "JOIN movie m ON w.Movie_idMovie = m.idMovie\n" +
                "JOIN user u ON w.User_idUser = u.idUser\n" +
                "WHERE u.UserName =" + '"'+ object.getUserName() + '"';

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Movie movie = new Movie();
            movie.setTitleMovie(rs.getString("judulMovie"));
            movie.setGenreMovie(rs.getString("genreMovie"));
            movie.setDurasiMovie(rs.getInt("durasiMovie"));
            movies.add(movie);
        }

        rs.close();
        ps.close();
        connection.close();
        return movies;
    }

    // ini sql ketika filter genrenya dijalanin tapi usernya belum diklik
    public List<Movie> fetchGenre(Movie object) throws SQLException, ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT *\n" +
                "FROM Movie\n" +
                "WHERE Genre LIKE \"%"+object.getGenreMovie()+"%\"";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        // gunain looping utk masukin data dr result set ke dalam list yg udah dibuat
        while(rs.next()){
            Movie movie = new Movie();
            movie.setTitleMovie(rs.getString("Title"));
            movie.setGenreMovie(rs.getString("Genre"));
            movie.setDurasiMovie(rs.getInt("Durasi"));
            movies.add(movie);
        }

        rs.close();
        ps.close();
        connection.close();
        return movies;
    }
}
