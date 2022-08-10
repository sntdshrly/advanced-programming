package com.example.hibernate_uts.controller;

import com.example.hibernate_uts.Main;
import com.example.hibernate_uts.dao.MovieDao;
import com.example.hibernate_uts.dao.UserDao;
import com.example.hibernate_uts.dao.WatchlistDao;
import com.example.hibernate_uts.entity.Movie;
import com.example.hibernate_uts.entity.User;
import com.example.hibernate_uts.entity.Watchlist;
import com.example.hibernate_uts.util.JDBCUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    @FXML
    private ComboBox<String> cmbGenre;
    @FXML
    private ListView<User> lvUser;
    @FXML
    private TableView<Movie> table1;
    @FXML
    private TableColumn<String, Movie> titleCol;
    @FXML
    private TableColumn<String, Movie> genreCol;
    @FXML
    private TableColumn<Integer, Movie> durasiCol;
    @FXML
    private TableView<Watchlist> table2;
    @FXML
    private TableColumn<Movie, Watchlist> lastTitleCol;
    @FXML
    private TableColumn<Integer, Watchlist> lastWatchCol;
    @FXML
    private TableColumn<Integer, Watchlist> isFavoriteCol;
    private UserDao uDao;
    private MovieDao mDao;
    private WatchlistDao wDao;
    private ObservableList<String> genre;

    public void initialize() {
        uDao = new UserDao();
        mDao = new MovieDao();
        wDao = new WatchlistDao();
        ObservableList<Watchlist> wList = FXCollections.observableArrayList(wDao.getData());
        genre = FXCollections.observableArrayList(
                "All",
                "Action",
                "Musical",
                "Comedy",
                "Animated",
                "Fantasy",
                "Drama",
                "Mistery",
                "Thriller",
                "Horror"
        );
        cmbGenre.setItems(genre);
        cmbGenre.getSelectionModel().select(0);
        table2.setItems(wList);
        lastTitleCol.setCellValueFactory(new PropertyValueFactory<>("movie"));
        lastWatchCol.setCellValueFactory(new PropertyValueFactory<>("DurasiWatch"));
        isFavoriteCol.setCellValueFactory(new PropertyValueFactory<>("BoolFavorite"));
        showData();
    }

    public void showData() {
        ObservableList<User> uList = FXCollections.observableArrayList(uDao.getData());
        ObservableList<Movie> mList = FXCollections.observableArrayList(mDao.getData());
        lvUser.setItems(uList);
        table1.setItems(mList);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        durasiCol.setCellValueFactory(new PropertyValueFactory<>("Durasi"));
    }

    public void changeCombo(ActionEvent actionEvent) {
        if (cmbGenre.getValue().equals("All")) {
            ObservableList<Movie> mList = FXCollections.observableArrayList(mDao.getData());
            table1.setItems(mList);
        } else {
            ObservableList<Movie> mList = FXCollections.observableArrayList(mDao.filterMovie(cmbGenre.getValue()));
            table1.setItems(mList);
        }
    }

    public void filterWatchList() {
        ObservableList<Watchlist> wList = FXCollections.observableArrayList(wDao.filterData(lvUser.getSelectionModel().getSelectedItem()));
        table2.setItems(wList);
        lastTitleCol.setCellValueFactory(new PropertyValueFactory<>("movie"));
        lastWatchCol.setCellValueFactory(new PropertyValueFactory<>("DurasiWatch"));
        isFavoriteCol.setCellValueFactory(new PropertyValueFactory<>("BoolFavorite"));
    }

    public void AddUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SecondView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("UTS!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        showData();
    }

    public void DelUserAction(ActionEvent actionEvent) {
        if (!lvUser.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete this data?", ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                uDao.delData(lvUser.getSelectionModel().getSelectedItem());
            }
            showData();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select the user first!", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void printReport(ActionEvent actionEvent) {
        JasperPrint jp;
        Connection conn = JDBCUtility.getConnection();
        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("report/fileReport.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Movies Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
