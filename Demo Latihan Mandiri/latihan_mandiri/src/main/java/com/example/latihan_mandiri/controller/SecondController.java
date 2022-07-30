package com.example.latihan_mandiri.controller;

import com.example.latihan_mandiri.dao.DosenDaoImpl;
import com.example.latihan_mandiri.dao.MahasiswaDaoImpl;
import com.example.latihan_mandiri.entity.Dosen;
import com.example.latihan_mandiri.entity.Mahasiswa;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SecondController implements Initializable{
    @FXML
    private TextField txtAddUpIDMahasiswa;
    @FXML
    private TextField txtAddUpNamaMahasiswa;
    @FXML
    private TextField txtAddUpAlamatMahasiswa;
    @FXML
    private ComboBox comboAddUpDosenWali;
    @FXML
    private Button btnProsesAddUp;
    @FXML
    private Button btnCancelAddUp;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        if (mainController.getAddOrUpdateOrDelete().equals("add")) {
            txtAddUpIDMahasiswa.setDisable(false);
        } else {
            txtAddUpIDMahasiswa.setDisable(true);
            Mahasiswa mahasiswa = mainController.getTableMahasiswa().getSelectionModel().getSelectedItem();
            Integer idMahasiswa = mahasiswa.getIdMahasiswa();
            txtAddUpIDMahasiswa.setText(String.valueOf(idMahasiswa));
            String namaMahasiswa = mahasiswa.getNamaMahasiswa();
            txtAddUpNamaMahasiswa.setText(namaMahasiswa);
            String alamatMahasiswa = mahasiswa.getAlamatMahasiswa();
            txtAddUpAlamatMahasiswa.setText(alamatMahasiswa);
        }
        this.mainController = mainController;
        ObservableList<Dosen> dosens = this.mainController.getDosens();
        comboAddUpDosenWali.setItems(dosens);
        comboAddUpDosenWali.getSelectionModel().select(0);

    }


    public void onActionProses(ActionEvent actionEvent) {
        //         --------- cek kosong atau tidak ---------
        if (txtAddUpIDMahasiswa.getText().trim().isEmpty() || txtAddUpNamaMahasiswa.getText().trim().isEmpty() ||
                txtAddUpAlamatMahasiswa.getText().trim().isEmpty() || comboAddUpDosenWali.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        }
        // --------- kalau tidak kosong ---------
        else {
            // --------- tambah ---------
            if (mainController.getAddOrUpdateOrDelete().equals("add")) {
                addPlayer();
                }
            // --------- edit ---------
            else if (mainController.getAddOrUpdateOrDelete().equals("edit")) {
                editPlayer();
            }
            }

        }

    private void addPlayer() {
        //            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
//            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
        Mahasiswa mahasiswa = new Mahasiswa();
        MahasiswaDaoImpl mahasiswaDao = new MahasiswaDaoImpl();
        mahasiswa.setIdMahasiswa(Integer.parseInt(txtAddUpIDMahasiswa.getText().trim()));
        mahasiswa.setNamaMahasiswa(txtAddUpNamaMahasiswa.getText().trim());
        mahasiswa.setAlamatMahasiswa(txtAddUpAlamatMahasiswa.getText().trim());
        mahasiswa.setDosen((Dosen) comboAddUpDosenWali.getValue());

        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (mahasiswaDao.addData(mahasiswa) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                mainController.getDosens().clear();
                mainController.getMahasiswas().addAll(mahasiswaDao.fetchAll());
                txtAddUpIDMahasiswa.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private void editPlayer() {
        MahasiswaDaoImpl mahasiswaDao = new MahasiswaDaoImpl();
        Mahasiswa mahasiswa = mainController.getTableMahasiswa().getSelectionModel().getSelectedItem();

        mahasiswa.setNamaMahasiswa(txtAddUpNamaMahasiswa.getText());
        mahasiswa.setAlamatMahasiswa(txtAddUpAlamatMahasiswa.getText());
        mahasiswa.setDosen((Dosen) comboAddUpDosenWali.getValue());

        ObservableList<Mahasiswa> mahasiswas = this.mainController.getMahasiswas();
        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (mahasiswaDao.updateData(mahasiswa) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                mainController.getMahasiswas().clear();
                mainController.getMahasiswas().addAll(mahasiswaDao.fetchAll());
                txtAddUpIDMahasiswa.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void onActionCancel(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        comboAddUpDosenWali.setItems(mainController.getDosens());
//        System.out.println(mainController.getDosens());
    }
}
