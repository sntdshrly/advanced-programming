package com.example.uts_javafx.controller;

import com.example.uts_javafx.Main;
import com.example.uts_javafx.dao.MovieDaoImpl;
import com.example.uts_javafx.dao.UserDaoImpl;
import com.example.uts_javafx.dao.WatchListDaoImpl;
import com.example.uts_javafx.entity.Movie;
import com.example.uts_javafx.entity.User;
import com.example.uts_javafx.entity.WatchList;
import com.example.uts_javafx.util.MySQLConnection;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {
    @FXML
    private TableColumn<Movie, String> tbColTitle1;
    @FXML
    private TableColumn<Movie, String> tbColGenre1;
    @FXML
    private TableColumn<Movie, Integer> tbColDurasi1;
    @FXML
    private TableColumn<WatchList, Movie> tbColTitle2;
    @FXML
    private TableColumn<WatchList, Integer> tbColLastWatch2;
    @FXML
    private TableColumn<WatchList, Integer> tbColFavorite2;
    @FXML
    private ComboBox<String> cmbGenre;
    @FXML
    private ListView lvUser;
    @FXML
    private TableView table1;
    @FXML
    private TableView table2;
    private ObservableList movies; // di sini ga di hard type karena pas mau add "tanpa filter" dia tipenya String
    private ObservableList<User> users;
    private ObservableList<WatchList> watchLists;
    private User selectedUser; // utk mendapatkan data dari tb user
    private ObservableList<String> genre;

    public ObservableList<User> getUsers() {return users;}

    public void changeCombo(ActionEvent actionEvent) {
        MovieDaoImpl movieDao = new MovieDaoImpl();
        Movie movie = new Movie();
        movie.setGenreMovie(String.valueOf(cmbGenre.getValue()));
        System.out.println(cmbGenre.getValue());

        try {
            if (!cmbGenre.getSelectionModel().getSelectedItem().equals("All")) {
            movies.clear();
            movies.addAll(movieDao.fetchGenre(movie));}
            else{
                movies.addAll(movieDao.fetchAll());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddUserAction(ActionEvent actionEvent) throws IOException {
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

    public void DelUserAction(ActionEvent actionEvent) {
        UserDaoImpl userDao = new UserDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (userDao.deleteData(selectedUser) == 1) {
                    // jika result = 1 maka kita akan mengosongkan observable list
                    users.clear();
                    users.addAll(userDao.fetchAll());
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setContentText("User deleted!");
                    alertInfo.showAndWait();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void listViewUserClicked(MouseEvent mouseEvent) {
        selectedUser = (User) lvUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            MovieDaoImpl movieDao = new MovieDaoImpl();
            User user = new User();
            user.setUserName(selectedUser.getUserName());

            Movie movie = new Movie();
            movie.setGenreMovie(String.valueOf(cmbGenre.getValue()));

            try {
                movies.clear();
                movies.addAll(movieDao.fetchFilterMovie(user));
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            table1.setItems(movies);
            tbColTitle1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitleMovie()));
            tbColGenre1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenreMovie()));
            tbColDurasi1.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDurasiMovie()).asObject());
        }
    }

    public void printReport(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                JasperPrint jasperPrint;
                Connection connection = MySQLConnection.createConnection();
                Map param = new HashMap();

                jasperPrint = JasperFillManager.fillReport("project/laporan_uts.jasper",param,connection);
                JasperViewer viewer = new JasperViewer(jasperPrint, false); // Kalau jaspernya diclose apakah aplikasinya diclose? set=false=tidak.
                viewer.setTitle("List Movie");
                viewer.setVisible(true);
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MovieDaoImpl movieDao = new MovieDaoImpl();
        movies = FXCollections.observableArrayList();
        UserDaoImpl userDao = new UserDaoImpl();
        users = FXCollections.observableArrayList();
        WatchListDaoImpl watchListDao = new WatchListDaoImpl();
        watchLists = FXCollections.observableArrayList();

        try {
            movies.addAll(movieDao.fetchAll());
            users.addAll(userDao.fetchAll());
            watchLists.addAll(watchListDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        table1.setItems(movies);
        tbColTitle1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitleMovie()));
        tbColGenre1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGenreMovie()));
        tbColDurasi1.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDurasiMovie()).asObject());

        lvUser.setItems(users);

        table2.setItems(watchLists);
        tbColTitle2.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getMovieId().getTitleMovie()));
        tbColLastWatch2.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getLastWatchList()).asObject());
        tbColFavorite2.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getFavoriteWatchList()).asObject());

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
    }
}