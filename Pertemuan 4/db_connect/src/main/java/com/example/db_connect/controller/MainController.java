package com.example.db_connect.controller;

import com.example.db_connect.dao.KategoriTransaksiDaoImpl;
import com.example.db_connect.dao.TransaksiDaoImpl;
import com.example.db_connect.entity.KategoriTransaksi;
import com.example.db_connect.entity.Transaksi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Transaksi> tbTransaksi;
    @FXML
    private TableColumn<Transaksi, Integer> tbColId;
    @FXML
    private TableColumn<Transaksi, String> tbColNama;
    @FXML
    private TableColumn<Transaksi, String> tbColJumlah;
    @FXML
    private TableColumn<Transaksi,KategoriTransaksi> tbColKategori;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtJumlah;
    @FXML
    private ComboBox<KategoriTransaksi> comboKategori;

    // declare var
    private TransaksiDaoImpl transaksiDao;
    private KategoriTransaksiDaoImpl kategoriTransaksiDao;
    private ObservableList<Transaksi> transactions;
    private ObservableList<KategoriTransaksi> kategoriTransactions;
    private Transaksi selectedTransaksi; // untuk dapatkan data dari tabel transaksi

    public void onActionAdd(ActionEvent actionEvent) {
        if(txtNama.getText().trim().isEmpty() || txtJumlah.getText().trim().isEmpty() || comboKategori.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        }
        else{
            Transaksi transaksi = new Transaksi();

            transaksi.setNama(txtNama.getText().trim());
            transaksi.setJumlah(Integer.parseInt(txtJumlah.getText().trim()));
            transaksi.setKategoriTransaksi(comboKategori.getValue());
            // jika return add data satu maka akan kosongin list dan isi dg data baru
            try {
                if(transaksiDao.addData(transaksi)==1){
                    transactions.clear();
                    transactions.addAll(transaksiDao.fetchAll());
                    txtNama.clear();
                    txtJumlah.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transaksiDao = new TransaksiDaoImpl();
        kategoriTransaksiDao = new KategoriTransaksiDaoImpl();
        transactions = FXCollections.observableArrayList();
        kategoriTransactions = FXCollections.observableArrayList();

        try {
            transactions.addAll(transaksiDao.fetchAll());
            kategoriTransactions.addAll(kategoriTransaksiDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Untuk format ke dalam bentuk $
        Locale usa = new Locale("en","US");
        // Untuk format ke dalam bentuk Rp.
//        Locale rupiah = new Locale("ind","ID");

        NumberFormat formatter = NumberFormat.getCurrencyInstance(usa);

        comboKategori.setItems(kategoriTransactions);
        tbTransaksi.setItems(transactions);
        tbColId.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        tbColNama.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getNama()));
//        tbColJumlah.setCellValueFactory(data-> new SimpleIntegerProperty(data.getValue().getJumlah()).asObject());
        tbColJumlah.setCellValueFactory(data-> new SimpleStringProperty(formatter.format(data.getValue().getJumlah())));
        tbColKategori.setCellValueFactory(data-> new SimpleObjectProperty<>(data.getValue().getKategoriTransaksi()));

    }

    public void onActionDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (transaksiDao.deleteData(selectedTransaksi) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    transactions.clear();
                    transactions.addAll(transaksiDao.fetchAll());
                    reset();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void reset() {
        txtNama.clear();
        selectedTransaksi = null;
        btnSave.setDisable(false);
    }

    public void tableTransaksiClicked(MouseEvent mouseEvent) {
        selectedTransaksi = tbTransaksi.getSelectionModel().getSelectedItem();
        if (selectedTransaksi != null) {
            txtNama.setText(selectedTransaksi.getNama());
            txtJumlah.setText(String.valueOf(selectedTransaksi.getJumlah()));
            comboKategori.setValue(selectedTransaksi.getKategoriTransaksi());
            btnSave.setDisable(true);
        }
    }
}
