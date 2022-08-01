module com.example.uts_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // ini buat sql
    requires jasperreports; // ini buat jasperreports

    opens com.example.uts_javafx to javafx.fxml;
    exports com.example.uts_javafx;
    exports com.example.uts_javafx.controller;
    opens com.example.uts_javafx.controller to javafx.fxml;
    exports com.example.uts_javafx.dao;
    opens com.example.uts_javafx.dao to javafx.fxml;
    exports com.example.uts_javafx.entity;
    opens com.example.uts_javafx.entity to javafx.fxml;
    exports com.example.uts_javafx.util;
    opens com.example.uts_javafx.util to javafx.fxml;
}