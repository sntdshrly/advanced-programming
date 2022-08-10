package com.example.hibernate_uts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idMovie")
    private int idMovie;
    @Basic
    @Column(name = "Title")
    private String title;
    @Basic
    @Column(name = "Genre")
    private String genre;
    @Basic
    @Column(name = "Durasi")
    private Integer durasi;

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDurasi() {
        return durasi;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return idMovie == movie.idMovie && Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(durasi, movie.durasi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMovie, title, genre, durasi);
    }
    public Movie() {
    }

    @Override
    public String toString() {
        return title;
    }
}
