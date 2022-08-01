package com.example.uts_javafx.entity;

public class Movie {
    private int idMovie;
    private String titleMovie;
    private String genreMovie;
    private int durasiMovie;

    public Movie() {

    }

    public Movie(int idMovie, String titleMovie, String genreMovie, int durasiMovie) {
        this.idMovie = idMovie;
        this.titleMovie = titleMovie;
        this.genreMovie = genreMovie;
        this.durasiMovie = durasiMovie;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getGenreMovie() {
        return genreMovie;
    }

    public void setGenreMovie(String genreMovie) {
        this.genreMovie = genreMovie;
    }

    public int getDurasiMovie() {
        return durasiMovie;
    }

    public void setDurasiMovie(int durasiMovie) {
        this.durasiMovie = durasiMovie;
    }

    @Override
    public String toString() {
        return genreMovie;
    }
}
