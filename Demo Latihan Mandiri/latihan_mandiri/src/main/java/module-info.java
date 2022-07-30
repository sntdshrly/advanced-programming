module com.example.latihan_mandiri {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // ini buat sql
    requires jasperreports; // ini buat jasperreports


    opens com.example.latihan_mandiri to javafx.fxml;
    exports com.example.latihan_mandiri;
    exports com.example.latihan_mandiri.controller;
    opens com.example.latihan_mandiri.controller to javafx.fxml;
}