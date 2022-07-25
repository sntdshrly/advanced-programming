package com.example.db_connect.entity;

public class Transaksi {
    private int id;
    private String nama;
    private int jumlah;
    private KategoriTransaksi kategoriTransaksi;

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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public KategoriTransaksi getKategoriTransaksi() {
        return kategoriTransaksi;
    }

    public void setKategoriTransaksi(KategoriTransaksi kategoriTransaksi) {
        this.kategoriTransaksi = kategoriTransaksi;
    }
}
