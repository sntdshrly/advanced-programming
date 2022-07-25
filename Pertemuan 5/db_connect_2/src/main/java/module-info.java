module com.example.db_connect_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.db_connect_2 to javafx.fxml;
    exports com.example.db_connect_2;
    // exports controller
    exports com.example.db_connect_2.controller;
    opens com.example.db_connect_2.controller to javafx.fxml;

    // exports util
    exports com.example.db_connect_2.util;
    opens com.example.db_connect_2.util to javafx.fxml;

    // exports entity
    exports com.example.db_connect_2.entity;
    opens com.example.db_connect_2.entity to javafx.fxml;

    // exports dao
    exports com.example.db_connect_2.dao;
    opens com.example.db_connect_2.dao to javafx.fxml;

}