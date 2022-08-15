module com.example.demo_thread_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.thread_teori to javafx.fxml;
    exports com.example.thread_teori;
}