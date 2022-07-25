package com.example.db_connect_2.controller;

import com.example.db_connect_2.Main;
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
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PenerbanganController implements Initializable {
    @FXML
    private ComboBox<Negara> comboFilter;
    @FXML
    private TableView<Penerbangan> tbViewPenerbangan;
    @FXML
    private TableColumn<Penerbangan, Integer> tbColID;
    @FXML
    private TableColumn<Penerbangan, Negara> tbColAsal;
    @FXML
    private TableColumn<Penerbangan, Negara> tbColTujuan;
    @FXML
    private TableColumn<Penerbangan, String> tbColHarga;
    @FXML
    private TableColumn<Penerbangan, String> tbColMaskapai;
    @FXML
    private Button btnTambahPenerbangan;
    @FXML
    private Button btnHapusPenerbangan;
    //declare var
    private PenerbanganDaoImpl penerbanganDao;
    private ObservableList<Penerbangan> penerbangans;
    private Penerbangan selectedItem; // utk mendapatkan data dari tb category
    private NegaraDaoImpl negaraDao;
    private ObservableList negaras; // di sini ga di hard type karena pas mau add "tanpa filter" dia tipenya String

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        penerbanganDao = new PenerbanganDaoImpl();
        penerbangans = FXCollections.observableArrayList();

        negaraDao = new NegaraDaoImpl();
        negaras = FXCollections.observableArrayList();

        try {
            penerbangans.addAll(penerbanganDao.fetchAll());
            negaras.addAll(negaraDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Untuk format ke dalam bentuk $
//        Locale usa = new Locale("en","US");
        // Untuk format ke dalam bentuk IDR.
        Locale rupiah = new Locale("ind", "ID");

        NumberFormat formatter = NumberFormat.getCurrencyInstance(rupiah);

        tbViewPenerbangan.setItems(penerbangans);
        tbColID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPenerbanganId()).asObject());
        tbColAsal.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getPenerbanganAsal()));
        tbColTujuan.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getPenerbanganTujuan()));
        tbColHarga.setCellValueFactory(data -> new SimpleStringProperty(formatter.format(data.getValue().getPenerbanganHarga())));
        tbColMaskapai.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPenerbanganMaskapai()));

        negaras.add(0, "Tanpa filter");
        comboFilter.setItems(negaras);
        comboFilter.getSelectionModel().select(0);

    }

    public void onActionShowAdd(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TambahPenerbanganView.fxml"));
        Parent root = loader.load();
        Stage tambahStage = new Stage();

        // ** NOTE ** di sini pake controller baru supaya ga bikin list baru ketika pindah page
        TambahPenerbanganController tambahPenerbanganController = loader.getController();
        tambahPenerbanganController.setPenerbanganController(this);

        tambahStage.setTitle("Soal Kelas");
        tambahStage.setScene(new Scene(root));
        tambahStage.show();
    }

    public void onActionDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (penerbanganDao.deleteData(selectedItem) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    penerbangans.clear();
                    penerbangans.addAll(penerbanganDao.fetchAll());
//                    reset(); // dijadikan komen karena tidak ada textfield yang perlu dihapus
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void tableClicked(MouseEvent mouseEvent) {
        selectedItem = tbViewPenerbangan.getSelectionModel().getSelectedItem();
    }

    // ** NOTE ** di sini bikin getter supaya ga bikin list baru ketika pindah page || setternya ada di page baru (TambahPenerbanganController)
    public ObservableList<Penerbangan> getPenerbangans() {
        return penerbangans;
    }

    public void onActionCombo(ActionEvent actionEvent) {
        Negara negara = new Negara();
        negara.setNegara(String.valueOf(comboFilter.getValue()));
        try {
//                negaras.clear();
//                negaras.addAll(negaraDao.fetchFilter(negara));
            penerbangans.clear();
            penerbangans.addAll(negaraDao.fetchFilters(negara));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

