package com.example.uts_old.entity;

public class Player {
    private int idPlayer;
    private String nama;
    private int umur;
    private String keahlian;

    public Player() {
    }

    public Player(int id, String nama, int umur, String keahlian) {
        this.idPlayer = id;
        this.nama = nama;
        this.umur = umur;
        this.keahlian = keahlian;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int id) {
        this.idPlayer = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    // di combobox comboPemain bentuknya masih "lokasi penyimpanan" -> Pake toString
    @Override
    public String toString() {
        return nama;
    }
}
