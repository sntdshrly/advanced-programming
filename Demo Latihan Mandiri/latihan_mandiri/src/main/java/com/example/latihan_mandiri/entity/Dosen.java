package com.example.latihan_mandiri.entity;

public class Dosen {
    private int idDosen;
    private String namaDosen;

    @Override
    public String toString() {
        return namaDosen;
    }

    public Dosen(int idDosen, String namaDosen) {
        this.idDosen = idDosen;
        this.namaDosen = namaDosen;
    }

    public Dosen() {

    }

    public int getIdDosen() {
        return idDosen;
    }

    public void setIdDosen(int idDosen) {
        this.idDosen = idDosen;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }
}
