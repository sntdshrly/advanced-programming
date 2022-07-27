package com.example.db_connect_2.controller;

import com.example.db_connect_2.Main;
import com.example.db_connect_2.dao.NegaraDaoImpl;
import com.example.db_connect_2.dao.PenerbanganDaoImpl;
import com.example.db_connect_2.entity.Negara;
import com.example.db_connect_2.entity.Penerbangan;
import com.example.db_connect_2.util.MySQLConnection;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PenerbanganController implements Initializable {
    @FXML
    private Button btnReportPenerbangan;
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

    public void onActionBtnReport(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                JasperPrint jasperPrint;
                Connection connection = MySQLConnection.createConnection();
                Map param = new HashMap();

                Negara negara = new Negara();
                negara.setNegara(String.valueOf(comboFilter.getValue()));
                String negaraStringParam = String.valueOf(negara);

                //buat report yang tanpa filter
                if(negaraStringParam.equalsIgnoreCase("Tanpa filter")){
                    jasperPrint = JasperFillManager.fillReport("project/Laporan.jasper",param,connection);
                }
                // buat report yang di filter
                else{
                    param.put("namaAsalParam",negaraStringParam);
                    param.put("namaTujuanParam",negaraStringParam);
                    jasperPrint = JasperFillManager.fillReport("project/LaporanFilterCurrency.jasper",param,connection);
                }
                JasperViewer viewer = new JasperViewer(jasperPrint, false); // Kalau jaspernya diclose apakah aplikasinya diclose? set=false=tidak.
                viewer.setTitle("Laporan Penerbangan");
                viewer.setVisible(true);
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();
    }
}

