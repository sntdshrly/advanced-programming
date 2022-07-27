package com.example.db_connect_2.entity;

import java.util.ArrayList;
import java.util.List;

public class Negara {
    private Integer negaraId;
    private String negaraAsal;
    private String negaraTujuan;
    private String negara;
    private boolean negaraVisa;

    // getter and setter

    public int getNegaraId() {
        return negaraId;
    }

    public void setNegaraId(int negaraId) {
        this.negaraId = negaraId;
    }


    public boolean isNegaraVisa() {
        return negaraVisa;
    }

    public void setNegaraVisa(boolean negaraVisa) {
        this.negaraVisa = negaraVisa;
    }

    public String getNegaraAsal() {
        return negaraAsal;
    }

    public void setNegaraAsal(String negaraAsal) {
        this.negaraAsal = negaraAsal;
    }

    public String getNegaraTujuan() {
        return negaraTujuan;
    }

    public void setNegaraTujuan(String negaraTujuan) {
        this.negaraTujuan = negaraTujuan;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    // toString supaya nampilin nama bukan alamat penyimpanan
    // if else negaraAsal & negaraTujuan untuk tampilan di tablePenerbangan
    // sedangkan return negara untuk combo box di add data

    @Override
    public String toString() {
        if (negaraAsal != null) {
            return negaraAsal;
        }
        else if (negaraTujuan != null) { return negaraTujuan;}
        return negara;
    }
}
