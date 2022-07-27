package com.example.demo_db_connect;

import com.example.demo_db_connect.dao.ContohDao;
import com.example.demo_db_connect.model.Contoh;
import com.example.demo_db_connect.util.MyConnection;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController {
    public ListView listview1;
    @FXML
    private Label welcomeText;
    private ObservableList<Contoh> clist_tampilan;

    public void initialize() {
        ContohDao dao = new ContohDao();
        clist_tampilan = dao.getData();
        listview1.setItems(clist_tampilan);
    }

    @FXML
    protected void onHelloButtonClick() {
        ContohDao dao = new ContohDao();
        dao.addData(new Contoh(-1, "tes"));
        clist_tampilan = dao.getData();
        listview1.setItems(clist_tampilan);
    }

    public void onActionLihatReport(ActionEvent actionEvent) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                JasperPrint jasperPrint;
                Connection connection = MyConnection.getConnection();
                Map param = new HashMap();
                param.put("idParam",3);
                // jasperPrint = JasperFillManager.fillReport("project/ContohLaporan.jasper",param,connection);
                jasperPrint =JasperFillManager.fillReport("project/ContohLaporan2.jasper",param,connection);
                JasperViewer viewer = new JasperViewer(jasperPrint, false); // Kalau jaspernya diclose apakah aplikasinya diclose? set=false=tidak.
                viewer.setTitle("Contoh laporan");
                viewer.setVisible(true);
                return null;
            }
            };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

    }
}
