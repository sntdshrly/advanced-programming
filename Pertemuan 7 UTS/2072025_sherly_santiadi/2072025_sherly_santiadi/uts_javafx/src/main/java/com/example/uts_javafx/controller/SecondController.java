package com.example.uts_javafx.controller;

import com.example.uts_javafx.dao.UserDaoImpl;
import com.example.uts_javafx.entity.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SecondController {
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        ObservableList<User> users = this.mainController.getUsers();
    }


    public void submit(ActionEvent actionEvent) {
        if (txtUserName.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        }
        // --------- kalau tidak kosong ---------
        else {
            // --------- tambah ---------
            addPlayer();
        }
    }
    private void addPlayer() {
        User user = new User();
        UserDaoImpl userDao = new UserDaoImpl();
        user.setUserName(txtUserName.getText().trim());
        user.setUserPassword(txtPassword.getText().trim());

        // jika return add data satu maka akan kosongin list dan isi dg data baru
        try {
            if (userDao.addData(user) == 1) {
                mainController.getUsers().clear();
                mainController.getUsers().addAll(userDao.fetchAll());
                txtUserName.clear();
                txtPassword.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User Added!");
                alert.showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
