package com.example.uts_javafx.entity;

public class WatchList {
    private int idWatchList;
    private int lastWatchList;
    private int favoriteWatchList;
    private Movie movieId;
    private User userId;

    public WatchList() {

    }

    public WatchList(int idWatchList, int lastWatchList, int favoriteWatchList, Movie movieId, User userId) {
        this.idWatchList = idWatchList;
        this.lastWatchList = lastWatchList;
        this.favoriteWatchList = favoriteWatchList;
        this.movieId = movieId;
        this.userId = userId;
    }

    public int getIdWatchList() {
        return idWatchList;
    }

    public void setIdWatchList(int idWatchList) {
        this.idWatchList = idWatchList;
    }

    public int getLastWatchList() {
        return lastWatchList;
    }

    public void setLastWatchList(int lastWatchList) {
        this.lastWatchList = lastWatchList;
    }

    public int getFavoriteWatchList() {
        return favoriteWatchList;
    }

    public void setFavoriteWatchList(int favoriteWatchList) {
        this.favoriteWatchList = favoriteWatchList;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public void setMovieId(Movie movieId) {
        this.movieId = movieId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}
