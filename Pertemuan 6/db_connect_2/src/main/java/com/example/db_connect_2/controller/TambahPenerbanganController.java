package com.example.db_connect_2.controller;

import com.example.db_connect_2.dao.NegaraDaoImpl;
import com.example.db_connect_2.dao.PenerbanganDaoImpl;
import com.example.db_connect_2.entity.Negara;
import com.example.db_connect_2.entity.Penerbangan;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TambahPenerbanganController implements Initializable {
    @FXML
    private ComboBox<Penerbangan> comboTambahMaskapai;
    @FXML
    private ComboBox<Negara> comboTambahTujuan;
    @FXML
    private ComboBox<Negara> comboTambahAsal;
    @FXML
    private TextField txtTambahHarga;
    @FXML
    private Button btnTambahOK;

    //declare var
    private PenerbanganDaoImpl penerbanganDao;
    private NegaraDaoImpl negaraDao;
    private ObservableList<Penerbangan> penerbangans;
    private ObservableList<Negara> negaras;
    // ** NOTE ** di sini variabel buat controller
    private PenerbanganController penerbanganController;

    // ** NOTE ** di sini bikin setter supaya ga bikin list baru ketika pindah page || getternya ada di page lama (PenerbanganController)
    public void setPenerbanganController(PenerbanganController penerbanganController) {
        this.penerbanganController = penerbanganController;
    }

    public void onActionTambahOK(ActionEvent actionEvent) {
        if (txtTambahHarga.getText().trim().isEmpty() || comboTambahMaskapai.getValue() == null || comboTambahAsal.getValue() == null || comboTambahTujuan.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        } else {
            Penerbangan penerbangan = new Penerbangan();
//            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
//            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
            penerbangan.setPenerbanganMaskapai(String.valueOf(comboTambahMaskapai.getValue()));
            penerbangan.setPenerbanganHarga(Float.valueOf(txtTambahHarga.getText().trim()));
            penerbangan.setPenerbanganAsal(comboTambahAsal.getValue());
            penerbangan.setPenerbanganTujuan(comboTambahTujuan.getValue());
            // jika return add data satu maka akan kosongin list dan isi dg data baru
            try {
                if (penerbanganDao.addData(penerbangan) == 1) {
                      // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                    penerbanganController.getPenerbangans().clear();
                    penerbanganController.getPenerbangans().addAll(penerbanganDao.fetchAll());
                    txtTambahHarga.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        penerbanganDao = new PenerbanganDaoImpl();
        penerbangans = FXCollections.observableArrayList();
        negaraDao = new NegaraDaoImpl();
        negaras = FXCollections.observableArrayList();

        try {
            penerbangans.addAll(penerbanganDao.fetchAllMaskapai());
            negaras.addAll(negaraDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        comboTambahMaskapai.setItems(penerbangans);
        comboTambahAsal.setItems(negaras);
        comboTambahTujuan.setItems(negaras);

    }
}
