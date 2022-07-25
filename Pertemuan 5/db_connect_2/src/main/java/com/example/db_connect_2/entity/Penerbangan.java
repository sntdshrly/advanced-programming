package com.example.db_connect_2.entity;

public class Penerbangan {
    private int penerbanganId;
    private Float penerbanganHarga;
    private Negara penerbanganAsal;
    private Negara penerbanganTujuan;
    private String penerbanganMaskapai;

    // getter and setter
    public int getPenerbanganId() {
        return penerbanganId;
    }

    public void setPenerbanganId(int penerbanganId) {
        this.penerbanganId = penerbanganId;
    }

    public Float getPenerbanganHarga() {
        return penerbanganHarga;
    }

    public void setPenerbanganHarga(Float penerbanganHarga) {
        this.penerbanganHarga = penerbanganHarga;
    }

    public Negara getPenerbanganAsal() {
        return penerbanganAsal;
    }

    public void setPenerbanganAsal(Negara penerbanganAsal) {
        this.penerbanganAsal = penerbanganAsal;
    }

    public Negara getPenerbanganTujuan() {
        return penerbanganTujuan;
    }

    public void setPenerbanganTujuan(Negara penerbanganTujuan) {
        this.penerbanganTujuan = penerbanganTujuan;
    }

    public String getPenerbanganMaskapai() {
        return penerbanganMaskapai;
    }

    public void setPenerbanganMaskapai(String penerbanganMaskapai) {
        this.penerbanganMaskapai = penerbanganMaskapai;
    }

    @Override
    public String toString() {
        return penerbanganMaskapai;
    }
}
