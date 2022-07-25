module com.example.db_connect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.db_connect to javafx.fxml;
    exports com.example.db_connect;

    // export module controller
    opens com.example.db_connect.controller to javafx.fxml;
    exports com.example.db_connect.controller;

    // export module dao
    opens com.example.db_connect.dao to javafx.fxml;
    exports com.example.db_connect.dao;

    // export module entity
    opens com.example.db_connect.entity to javafx.fxml;
    exports com.example.db_connect.entity;

    // export module util
    opens com.example.db_connect.util to javafx.fxml;
    exports com.example.db_connect.util;
}