package com.example.uts_old.controller;

import com.example.uts_old.dao.PlayerDaoImpl;
import com.example.uts_old.entity.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TambahController {
    public TextField txtTambahIDPeserta;
    public TextField txtTambahNamaPeserta;
    public TextField txtTambahUmurPeserta;
    public TextField txtTambahKeahlianPeserta;
    public Button btnTambahOKPeserta;
    public Button btnTambahCancelPeserta;
    private Player selectedItem;

    // ** NOTE ** di sini variabel buat controller
    private MainController mainController;

    // ** NOTE ** di sini bikin setter supaya ga bikin list baru ketika pindah page || getternya ada di page lama (MainController)
    public void setPlayerController(MainController mainController) {
        if (mainController.getAddOrUpdateOrDelete().equals("add")) {
            txtTambahIDPeserta.setDisable(false);
        } else {
            txtTambahIDPeserta.setDisable(true);
        }
        this.mainController = mainController;
    }

    public void onActionTambahOKPeserta(ActionEvent actionEvent) {
//         --------- cek kosong atau tidak ---------
        if (txtTambahNamaPeserta.getText().trim().isEmpty() || txtTambahUmurPeserta.getText().trim().isEmpty() ||
                txtTambahKeahlianPeserta.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        }
        // --------- kalau tidak kosong ---------
        else {
            // --------- tambah ---------
            if (mainController.getAddOrUpdateOrDelete().equals("add")) {
                if (txtTambahIDPeserta.getText().trim().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in the blank!");
                    alert.showAndWait();
                }
                else{
                    addPlayer();
                }
            }
            // --------- edit ---------
            else if (mainController.getAddOrUpdateOrDelete().equals("edit")) {
                editPlayer();
            }

        }
    }

    public void addPlayer() {
        //            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
//            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
        Player player = new Player();
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        player.setIdPlayer(Integer.parseInt(txtTambahIDPeserta.getText().trim()));
        player.setNama(txtTambahNamaPeserta.getText().trim());
        player.setUmur(Integer.parseInt(txtTambahUmurPeserta.getText().trim()));
        player.setKeahlian(txtTambahKeahlianPeserta.getText().trim());
        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (playerDao.addData(player) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                mainController.getPlayers().clear();
                mainController.getPlayers().addAll(playerDao.fetchAll());
                txtTambahIDPeserta.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void editPlayer() {
        //            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
//            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        Player player = mainController.getTablePemain().getSelectionModel().getSelectedItem();


        player.setNama(txtTambahNamaPeserta.getText());
        player.setUmur(Integer.parseInt(txtTambahUmurPeserta.getText()));
        player.setKeahlian(txtTambahKeahlianPeserta.getText());


        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (playerDao.updateData(player) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                mainController.getPlayers().clear();
                mainController.getPlayers().addAll(playerDao.fetchAll());
                txtTambahIDPeserta.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionTambahCancelPeserta(ActionEvent actionEvent) {
        Stage stage = (Stage) txtTambahIDPeserta.getScene().getWindow();
        stage.close();
    }
}
