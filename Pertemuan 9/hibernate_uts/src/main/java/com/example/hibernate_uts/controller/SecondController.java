package com.example.hibernate_uts.controller;

import com.example.hibernate_uts.dao.UserDao;
import com.example.hibernate_uts.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class SecondController {
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    public void submit(ActionEvent actionEvent) {
        UserDao dao = new UserDao();
        if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in the blank!", ButtonType.OK);
            alert.showAndWait();
        } else {
            int result = dao.addData(new User(0, txtUserName.getText(), txtPassword.getText()));
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Added!", ButtonType.OK);
                alert.showAndWait();
                txtUserName.getScene().getWindow().hide();
            }
        }

    }
}
