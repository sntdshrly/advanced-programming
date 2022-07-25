package com.example.db_connect.entity;

public class KategoriTransaksi {
    private int id;
    private String nama;
    private int tipeKategori;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTipeKategori() {
        return tipeKategori;
    }

    public void setTipeKategori(int tipeKategori) {
        this.tipeKategori = tipeKategori;
    }

    @Override
    public String toString() {
        return nama;
    }
}
