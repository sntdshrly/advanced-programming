package com.example.uts_old.controller;

import com.example.uts_old.Main;
import com.example.uts_old.dao.HutangDaoImpl;
import com.example.uts_old.dao.PlayerDaoImpl;
import com.example.uts_old.entity.Hutang;
import com.example.uts_old.entity.Player;
import javafx.beans.property.SimpleDoubleProperty;
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
    private TableView<Player> tbPlayer;
    @FXML
    private TableColumn<Player, Integer> tbColIDPlayer;
    @FXML
    private TableColumn<Player, String> tbColNamaPlayer;
    @FXML
    private TableColumn<Player, String> tbColKemampuanPlayer;
    @FXML
    private TableColumn<Player, Integer> tbColUmurPlayer;
    @FXML
    private Button btnAddPemain;
    @FXML
    private Button btnEditPemain;
    @FXML
    private Button btnDeletePemain;
    @FXML
    private ComboBox comboPemain;
    @FXML
    private TextField txtHutangTerhadap;
    @FXML
    private TextField txtSejumlahHutang;
    @FXML
    private Button btnAddHutang;
    @FXML
    private TableView tbHutang;
    @FXML
    private TableColumn<Hutang, Player> tbColIDPemainHutang; //diambil dari tabel player makanya tipenya Player
    @FXML
    private TableColumn<Hutang, String> tbColHutangTerhadap;
    @FXML
    private TableColumn<Hutang, Double> tbColHutangSejumlah;
    @FXML
    private Button btnDeleteHutang;
    private ObservableList<Player> players;
    private ObservableList<Hutang> hutangs;
    private Player selectedItemPlayer;
    private Hutang selectedItemHutang;


    public TableView<Player> getTablePemain() {
        return tbPlayer;
    }

    // @@ NOTE @@ di sini bikin statusnya apakah mau di add || update-delete ?
    // Kalau add maka diset = FALSE karna kalo add perlu nambahin ID
    // Kalau update-delete diset = TRUE karna kalo up-delete ga perlu utak-atik

    private String updateOrDelete;
    public String getAddOrUpdateOrDelete() {
        return updateOrDelete;
    }
    public void setAddOrUpdateOrDelete(String updateOrDelete) {
        this.updateOrDelete = updateOrDelete;
    }



    // ** NOTE ** di sini bikin getter supaya ga bikin list baru ketika pindah page || setternya ada di page baru (TambahController)
    public ObservableList<Player> getPlayers() {
        return players;
    }
    public void onActionAddPemain(ActionEvent actionEvent) throws IOException {
        setAddOrUpdateOrDelete("add");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TambahPemainView.fxml"));
        Parent root = loader.load();
        Stage tambahStage = new Stage();

        // ** NOTE ** di sini pake controller baru supaya ga bikin list baru ketika pindah page
        TambahController tambahController = loader.getController();
        tambahController.setPlayerController(this);

        tambahStage.setTitle("");
        tambahStage.setScene(new Scene(root));
        tambahStage.show();
    }

    public void onActionEditPemain(ActionEvent actionEvent) throws IOException {
        setAddOrUpdateOrDelete("edit");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TambahPemainView.fxml"));
        Parent root = loader.load();
        Stage tambahStage = new Stage();

        // ** NOTE ** di sini pake controller baru supaya ga bikin list baru ketika pindah page
        TambahController tambahController = loader.getController();
        tambahController.setPlayerController(this);

        tambahStage.setTitle("");
        tambahStage.setScene(new Scene(root));
        tambahStage.show();
    }

    public void onActionDeletePemain(ActionEvent actionEvent) {
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (playerDao.deleteData(selectedItemPlayer) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    players.clear();
                    players.addAll(playerDao.fetchAll());
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onActionAddHutang(ActionEvent actionEvent) {
        //            Kode di bawah namanya casting, dipakai kalau @FXML ComboBox tdk dideklarasi terlebih dahulu
//            penerbangan.setPenerbanganMaskapai((String) comboTambahMaskapai.getValue());
        Hutang hutang = new Hutang();
        HutangDaoImpl hutangDao = new HutangDaoImpl();

        hutang.setPlayer((Player) comboPemain.getValue());
        hutang.setPemberiUtang(txtHutangTerhadap.getText().trim());
        hutang.setJumlah(Integer.parseInt(txtSejumlahHutang.getText().trim()));
        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (hutangDao.addData(hutang) == 1) {
                // ** NOTE ** yang ini tdk pakai list baru, tapi pakai dari controllernya aja
//                    penerbangans.clear();
//                    penerbangans.addAll(penerbanganDao.fetchAll());
                hutangs.clear();
                hutangs.addAll(hutangDao.fetchAll());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionDeleteHutang(ActionEvent actionEvent) {
        HutangDaoImpl hutangDao = new HutangDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (hutangDao.deleteData(selectedItemHutang) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    hutangs.clear();
                    hutangs.addAll(hutangDao.fetchAll());
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize buat tabel player
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        players = FXCollections.observableArrayList();
        try {
            players.addAll(playerDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tbPlayer.setItems(players);
        tbColIDPlayer.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdPlayer()).asObject());
        tbColNamaPlayer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));
        tbColUmurPlayer.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getUmur()).asObject());
        tbColKemampuanPlayer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKeahlian()));

        // Initialize buat tabel hutang
        HutangDaoImpl hutangDao = new HutangDaoImpl();
        hutangs = FXCollections.observableArrayList();
        try {
            hutangs.addAll(hutangDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tbHutang.setItems(hutangs);
        tbColIDPemainHutang.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId())); // dari FK
        tbColHutangTerhadap.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPemberiUtang()));
        tbColHutangSejumlah.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getJumlah()).asObject());

        // Initialize buat combo box
        // di sini bentuknya masih "lokasi penyimpanan" -> Pake toString
        comboPemain.setItems(players);
        comboPemain.getSelectionModel().select(0);
    }

    public void tablePlayerClicked(MouseEvent mouseEvent) {
        selectedItemPlayer = (Player) tbPlayer.getSelectionModel().getSelectedItem();
    }


    public void tableHutangClicked(MouseEvent mouseEvent) {
        selectedItemHutang = (Hutang) tbHutang.getSelectionModel().getSelectedItem();
    }


}
