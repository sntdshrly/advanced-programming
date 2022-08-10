module com.example.hibernate_uts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // ini buat sql
    requires jasperreports; // ini buat jasperreports
    requires java.persistence; // persistance di model
    requires org.hibernate.orm.core; // di util/HibernateUtil
    requires java.naming; // konversi name dari class mapping ke tabel di db

    opens com.example.hibernate_uts to javafx.fxml;
    exports com.example.hibernate_uts;
    exports com.example.hibernate_uts.controller;
    opens com.example.hibernate_uts.controller to javafx.fxml;
    exports com.example.hibernate_uts.dao;
    opens com.example.hibernate_uts.dao to javafx.fxml;
    exports com.example.hibernate_uts.entity;
    opens com.example.hibernate_uts.entity;
    exports com.example.hibernate_uts.util;
    opens com.example.hibernate_uts.util to javafx.fxml;
}