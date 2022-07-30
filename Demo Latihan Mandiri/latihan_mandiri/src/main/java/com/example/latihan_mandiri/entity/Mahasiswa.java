package com.example.latihan_mandiri.entity;

public class Mahasiswa {
    private int idMahasiswa;
    private String namaMahasiswa;
    private String alamatMahasiswa;
    private Dosen dosen;

    public Mahasiswa(int idMahasiswa, String namaMahasiswa, String alamatMahasiswa, Dosen dosen) {
        this.idMahasiswa = idMahasiswa;
        this.namaMahasiswa = namaMahasiswa;
        this.alamatMahasiswa = alamatMahasiswa;
        this.dosen = dosen;
    }
    public Mahasiswa(){

    }


    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public String getAlamatMahasiswa() {
        return alamatMahasiswa;
    }

    public void setAlamatMahasiswa(String alamatMahasiswa) {
        this.alamatMahasiswa = alamatMahasiswa;
    }

    public Dosen getDosen() {
        return dosen;
    }

    public void setDosen(Dosen dosen) {
        this.dosen = dosen;
    }
}
