package com.example.demo_db_connect_2.controller;

import com.example.demo_db_connect_2.dao.DepartmentDaoImpl;
import com.example.demo_db_connect_2.dao.FacultyDaoImpl;
import com.example.demo_db_connect_2.entity.Department;
import com.example.demo_db_connect_2.entity.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnSaveFaculty;
    @FXML
    private Button btnUpdateFaculty;
    @FXML
    private Button btnDeleteFaculty;
    @FXML
    private Button btnSaveDepartment;
    @FXML
    private Button btnUpdateDepartment;
    @FXML
    private Button btnDeleteDepartment;
    @FXML
    private TextField txtFacultyName;
    @FXML
    private TableView<Faculty> tableFaculty;
    @FXML
    private TableColumn<Faculty, Integer> facultyCol01;
    @FXML
    private TableColumn<Faculty, String> facultyCol02;
    @FXML
    private TextField txtDepartmentName;
    @FXML
    private ComboBox<Faculty> comboFaculty;
    @FXML
    private TableView<Department> tableDepartment;
    @FXML
    private TableColumn<Department, Integer> departmentCol01;
    @FXML
    private TableColumn<Department, String> departmentCol02;
    @FXML
    private TableColumn<Department, Faculty> departmentCol03;

    // declare var
    private ObservableList<Department> departments;
    private ObservableList<Faculty> faculties;
    private DepartmentDaoImpl departmentDao;
    private FacultyDaoImpl facultyDao;

    private Faculty selectedFaculty; // untuk dapatkan data dari tabel faculty
    private Department selectedDepartment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facultyDao = new FacultyDaoImpl();
        departmentDao = new DepartmentDaoImpl();
        faculties = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();

        try {
            faculties.addAll(facultyDao.fetchAll());
            departments.addAll(departmentDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        comboFaculty.setItems(faculties);
        tableFaculty.setItems(faculties);
        facultyCol01.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        facultyCol02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        tableDepartment.setItems(departments);
        departmentCol01.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        departmentCol02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        departmentCol03.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFaculty()));

    }


    @FXML
    private void saveFacultyAction(ActionEvent actionEvent) {
        if (txtFacultyName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill faculty name!");
            alert.showAndWait();
        } else {
            Faculty faculty = new Faculty();
            faculty.setName(txtFacultyName.getText().trim());
            // jika return add data satu maka akan kosongin list dan isi dg data baru
            try {
                if (facultyDao.addData(faculty) == 1) {
                    faculties.clear();
                    faculties.addAll(facultyDao.fetchAll());
                    resetFaculty();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void saveDepartmentAction(ActionEvent actionEvent) {
        if (txtDepartmentName.getText().trim().isEmpty() || comboFaculty.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill department name and select faculty!");
            alert.showAndWait();
        } else {
            Department department = new Department();
            department.setName(txtDepartmentName.getText().trim());
            department.setFaculty(comboFaculty.getValue());
            // jika return add data satu maka akan kosongin list dan isi dg data baru
            try {
                if (departmentDao.addData(department) == 1) {
                    departments.clear();
                    departments.addAll(departmentDao.fetchAll());
                    resetDepartment();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Perbedaan dari update dan save data adalah pada objek yg dikirimkan ke dao
     * Proses add data membuat objek baru dari class XXX
     * Proses update menggunakan instance variabel yg diisi pada saat tabel diklik
     **/

    @FXML
    private void updateFacultyAction(ActionEvent actionEvent) {
        if (txtFacultyName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        } else {
            selectedFaculty.setName(txtFacultyName.getText().trim());
            try {
                if (facultyDao.updateData(selectedFaculty) == 1) {
                    faculties.clear();
                    faculties.addAll(facultyDao.fetchAll());
                    resetFaculty();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void resetFaculty() {
        txtFacultyName.clear();
        selectedFaculty = null;
        btnSaveFaculty.setDisable(false);
        btnUpdateFaculty.setDisable(true);
        btnDeleteFaculty.setDisable(true);

    }

    @FXML
    private void deleteFacultyAction(ActionEvent actionEvent) {
        deleteObject(selectedFaculty);
    }

    @FXML
    private void tableFacultyClicked(MouseEvent mouseEvent) {
        selectedFaculty = tableFaculty.getSelectionModel().getSelectedItem();
        if (selectedFaculty != null) {
            txtFacultyName.setText(selectedFaculty.getName());
            btnSaveFaculty.setDisable(true);
            btnUpdateFaculty.setDisable(false);
            btnDeleteFaculty.setDisable(false);
        }
    }

    @FXML
    private void updateDepartmentAction(ActionEvent actionEvent) {
        if (txtDepartmentName.getText().trim().isEmpty() || comboFaculty == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        } else {
            selectedDepartment.setName(txtDepartmentName.getText().trim());
            selectedDepartment.setFaculty(comboFaculty.getValue());
            try {
                if (departmentDao.updateData(selectedDepartment) == 1) {
                    departments.clear();
                    departments.addAll(departmentDao.fetchAll());
                    resetDepartment();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }}

        @FXML
        private void deleteDepartmentAction (ActionEvent actionEvent){
        deleteObject(selectedDepartment);
        }

        @FXML
        private void tableDepartmentClicked (MouseEvent mouseEvent){
            selectedDepartment = tableDepartment.getSelectionModel().getSelectedItem();
            if (selectedDepartment != null) {
                txtDepartmentName.setText(selectedDepartment.getName());
                comboFaculty.setValue(selectedDepartment.getFaculty());
                btnSaveDepartment.setDisable(true);
                btnUpdateDepartment.setDisable(false);
                btnDeleteDepartment.setDisable(false);
            }
        }


    private void resetDepartment() {
        txtDepartmentName.clear();
        selectedDepartment = null;
        btnSaveDepartment.setDisable(false);
        btnUpdateDepartment.setDisable(true);
        btnDeleteDepartment.setDisable(true);
    }

    // Membuat private method karena kode program akan punya struktur yg mirip
    // Supaya efisien

    private void deleteObject(Object object) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            if(object instanceof Faculty){
                try {
                    if(facultyDao.deleteData(selectedFaculty)==1){
                        // jika result = 1 maka kita akan mengosongkan observable list
                        faculties.clear();
                        faculties.addAll(facultyDao.fetchAll());
                        resetFaculty();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (object instanceof Department) {
                try {
                    if(departmentDao.deleteData(selectedDepartment)==1){
                        // jika result = 1 maka kita akan mengosongkan observable list
                        departments.clear();
                        departments.addAll(departmentDao.fetchAll());
                        resetDepartment();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
}