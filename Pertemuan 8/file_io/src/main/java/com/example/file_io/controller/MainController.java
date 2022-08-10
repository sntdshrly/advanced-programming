package com.example.file_io.controller;

import com.example.file_io.entity.User;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MainController {

    @FXML
    private ListView<User> listComment;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtComment;
    @FXML
    protected void onActionAdd1(ActionEvent actionEvent){
        if(txtUsername.getText().trim().isEmpty() || txtUsername.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill in the blank!");
            alert.show();
        }
        else{
            User user = new User(txtUsername.getText(),txtComment.getText());
            listComment.getItems().add(user);
        }
    }

    @FXML
    protected void onActionSave1(ActionEvent actionEvent) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/hasil.txt"));
        Gson gson = new Gson();
        String strToJson = gson.toJson(listComment.getItems());
        writer.write(strToJson);
        writer.close();
    }

    @FXML
    protected void onActionLoad1(ActionEvent actionEvent) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("data/hasil.txt"));
        Gson gson = new Gson();

        try {
            String json = reader.readLine();
            User[] users = gson.fromJson(json,User[].class);
            listComment.getItems().addAll(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onActionSave2(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(txtUsername.getScene().getWindow());
        Path path = Paths.get(file.toURI());
        try{
            Gson gson = new Gson();
            String strToJson = gson.toJson(listComment.getItems());
            Files.write(path,strToJson.getBytes());
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onActionLoad2(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        Path path = Paths.get(file.toURI());
        try{
            String strCombine = "";
            List<String> lines = Files.readAllLines(path);
            for(String l: lines){
                strCombine += l;
            }
            Gson gson = new Gson();
            User[] users = gson.fromJson(strCombine,User[].class);
            listComment.getItems().addAll(users);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}