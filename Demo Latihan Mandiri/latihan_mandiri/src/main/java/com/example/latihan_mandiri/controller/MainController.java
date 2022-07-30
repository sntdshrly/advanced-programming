package com.example.latihan_mandiri.controller;

import com.example.latihan_mandiri.Main;
import com.example.latihan_mandiri.dao.DosenDaoImpl;
import com.example.latihan_mandiri.dao.MahasiswaDaoImpl;
import com.example.latihan_mandiri.entity.Dosen;
import com.example.latihan_mandiri.entity.Mahasiswa;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Dosen> tbViewDosen;
    @FXML
    private TableColumn<Dosen, Integer> tbColIDDosen;
    @FXML
    private TableColumn<Dosen, String> tbColNamaDosen;
    @FXML
    private TableView<Mahasiswa> tbViewMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, Integer> tbColIDMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, String> tbColNamaMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, String> tbColAlamatMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, Dosen> tbColNamaDosenMahasiswa;
    @FXML
    private Button btnAddDosen;
    @FXML
    private Button btnUpdateDosen;
    @FXML
    private Button btnDeleteDosen;
    @FXML
    private TextField txtIDDosen;
    @FXML
    private TextField txtNamaDosen;
    @FXML
    private Button btnAddMahasiswa;
    @FXML
    private Button btnUpdateMahasiswa;
    @FXML
    private Button btnDeleteMahasiswa;

    private ObservableList<Mahasiswa> mahasiswas;
    private ObservableList<Dosen> dosens;
    private Dosen selectedDosen; // utk mendapatkan data dari tb dosen
    private Mahasiswa selectedMahasiswa; // utk mendapatkan data dari tb mhsw

    private String updateOrDelete;

    public String getAddOrUpdateOrDelete() {
        return updateOrDelete;
    }
    public void setAddOrUpdateOrDelete(String updateOrDelete) {
        this.updateOrDelete = updateOrDelete;
    }
    public ObservableList<Dosen> getDosens() {return dosens;
    }
    public ObservableList<Mahasiswa> getMahasiswas() {return mahasiswas;
    }
    public TableView<Mahasiswa> getTableMahasiswa() {
        return tbViewMahasiswa;
    }

    public void onActionAddMahasiswa(ActionEvent actionEvent) throws IOException {
        setAddOrUpdateOrDelete("add");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SecondView.fxml"));
        Parent root = loader.load();
        Stage tambahStage = new Stage();

        // ** NOTE ** di sini pake controller baru supaya ga bikin list baru ketika pindah page
        SecondController secondController = loader.getController();
        secondController.setMainController(this);

        tambahStage.setTitle("");
        tambahStage.setScene(new Scene(root));
        tambahStage.show();
    }

    public void onActionUpdateMahasiswa(ActionEvent actionEvent) throws IOException {
        setAddOrUpdateOrDelete("edit");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SecondView.fxml"));
        Parent root = loader.load();
        Stage tambahStage = new Stage();

        // ** NOTE ** di sini pake controller baru supaya ga bikin list baru ketika pindah page
        SecondController secondController = loader.getController();
        secondController.setMainController(this);

        tambahStage.setTitle("");
        tambahStage.setScene(new Scene(root));
        tambahStage.show();
    }

    public void onActionDeleteMahasiswa(ActionEvent actionEvent) {
        MahasiswaDaoImpl mahasiswaDao = new MahasiswaDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (mahasiswaDao.deleteData(selectedMahasiswa) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    mahasiswas.clear();
                    mahasiswas.addAll(mahasiswaDao.fetchAll());
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionAddDosen(ActionEvent actionEvent) {
        //            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
        //            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
        Dosen dosen = new Dosen();
        DosenDaoImpl dosenDao = new DosenDaoImpl();

        dosen.setIdDosen(Integer.parseInt(txtIDDosen.getText().trim()));
        dosen.setNamaDosen(txtNamaDosen.getText().trim());

        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (dosenDao.addData(dosen) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                dosens.clear();
                dosens.addAll(dosenDao.fetchAll());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionUpdateDosen(ActionEvent actionEvent) {
        DosenDaoImpl dosenDao = new DosenDaoImpl();
        if (txtIDDosen.getText().trim().isEmpty() || txtNamaDosen.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        } else {
            selectedDosen.setIdDosen(Integer.parseInt(txtIDDosen.getText().trim()));
            selectedDosen.setNamaDosen(txtNamaDosen.getText().trim());
            try {
                if (dosenDao.updateData(selectedDosen) == 1) {
                    dosens.clear();
                    dosens.addAll(dosenDao.fetchAll());
                    reset();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionDeleteDosen(ActionEvent actionEvent) {
        DosenDaoImpl dosenDao = new DosenDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (dosenDao.deleteData(selectedDosen) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    dosens.clear();
                    dosens.addAll(dosenDao.fetchAll());
                    reset();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MahasiswaDaoImpl mahasiswaDao = new MahasiswaDaoImpl();
        mahasiswas = FXCollections.observableArrayList();
        DosenDaoImpl dosenDao = new DosenDaoImpl();
        dosens = FXCollections.observableArrayList();

        try {
            mahasiswas.addAll(mahasiswaDao.fetchAll());
            dosens.addAll(dosenDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        tbViewDosen.setItems(dosens);
        tbColIDDosen.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdDosen()).asObject());
        tbColNamaDosen.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaDosen()));

        tbViewMahasiswa.setItems(mahasiswas);
        tbColIDMahasiswa.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdMahasiswa()).asObject());
        tbColNamaMahasiswa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaMahasiswa()));
        tbColAlamatMahasiswa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlamatMahasiswa()));
        tbColNamaDosenMahasiswa.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getDosen().getNamaDosen())); // dari FK
    }


    private void reset() {
        txtIDDosen.clear();
        txtNamaDosen.clear();
    }

    public void tbViewDosenClicked(MouseEvent mouseEvent) {
        selectedDosen = tbViewDosen.getSelectionModel().getSelectedItem();
        if (selectedDosen != null) {
            txtIDDosen.setText(String.valueOf(selectedDosen.getIdDosen()));
            txtNamaDosen.setText(selectedDosen.getNamaDosen());
        }
    }

    public void tbViewMahasiswaClicked(MouseEvent mouseEvent) {
        selectedMahasiswa = tbViewMahasiswa.getSelectionModel().getSelectedItem();
    }
}