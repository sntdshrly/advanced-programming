package com.example.hibernate_uts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Watchlist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idWatchList")
    private int idWatchList;
    @Basic
    @Column(name = "LastWatch")
    private Integer lastWatch;
    @Basic
    @Column(name = "Favorite")
    private Byte favorite;
    @ManyToOne
    @JoinColumn(name = "Movie_idMovie", referencedColumnName = "idMovie", nullable = false)
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "User_idUser", referencedColumnName = "idUser", nullable = false)
    private User user;

    public int getIdWatchList() {
        return idWatchList;
    }

    public void setIdWatchList(int idWatchList) {
        this.idWatchList = idWatchList;
    }

    public Integer getLastWatch() {
        return lastWatch;
    }

    public void setLastWatch(Integer lastWatch) {
        this.lastWatch = lastWatch;
    }

    public Byte getFavorite() {
        return favorite;
    }

    public void setFavorite(Byte favorite) {
        this.favorite = favorite;
    }

    public String getDurasiWatch() {
        String durasiWatch = lastWatch + "/" + movie.getDurasi();
        return durasiWatch;
    }
    public boolean getBoolFavorite() {
        boolean bool;
        if (favorite == 1) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Watchlist watchlist = (Watchlist) o;
        return idWatchList == watchlist.idWatchList && Objects.equals(lastWatch, watchlist.lastWatch) && Objects.equals(favorite, watchlist.favorite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWatchList, lastWatch, favorite);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Watchlist() {
    }

}
