package com.example.thread_teori;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer angka, angka2;

        // Masukan angka
        System.out.print("Input first number: ");
        angka = Integer.valueOf(scanner.nextLine());
        System.out.print("Input second number: ");
        angka2 = Integer.valueOf(scanner.nextLine());

        // Runnable
        Thread runThread1 = new Thread(new PrintTaskRunnable("Runnable task",10,angka,angka2));
        // Executor
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(runThread1);
        service.shutdown();

        // Thread
        new PrintTaskThread("Thread task",10,angka,angka2).start();
    }

}