module com.example.uts_old {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.uts_old to javafx.fxml;
    exports com.example.uts_old;

    // exports controller
    exports com.example.uts_old.controller;
    opens com.example.uts_old.controller to javafx.fxml;

    // exports util
    exports com.example.uts_old.util;
    opens com.example.uts_old.util to javafx.fxml;

    // exports entity
    exports com.example.uts_old.entity;
    opens com.example.uts_old.entity to javafx.fxml;

    // exports dao
    exports com.example.uts_old.dao;
    opens com.example.uts_old.dao to javafx.fxml;
}