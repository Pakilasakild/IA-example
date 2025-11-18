package com.ia.ia_base.controllers;

import com.ia.ia_base.util.AlertManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pavyzdinio lango controlleris.
 * Parodo kaip lengvai galima sukurti naują langą su status bar.
 */
public class ExampleController extends BaseController {
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Label dateTimeLabel;
    
    @FXML
    private TableView<Map<String, String>> tableView;
    
    private Timeline dateTimeTimeline;
    private ObservableList<Map<String, String>> tableData;
    
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        messageLabel.setText("Sveiki atvykę į pavyzdinį langą!");
        
        // Nustatome status bar
        statusLabel.setText("Parengta");
        
        // Inicializuojame TableView
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
        
        // Nustatome datą ir laiką
        updateDateTime();
        
        // Sukuriame Timeline, kuris atnaujina datą ir laiką kas sekundę
        dateTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateDateTime())
        );
        dateTimeTimeline.setCycleCount(Animation.INDEFINITE);
        dateTimeTimeline.play();
    }
    
    /**
     * Atnaujina datą ir laiką status bar
     */
    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
    }
    
    /**
     * Užkrauna CSV failą į TableView
     */
    @FXML
    private void loadCSVFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pasirinkite CSV failą");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV failai", "*.csv", "*.txt")
        );
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                loadCSV(file);
                statusLabel.setText("Užkrautas: " + file.getName());
            } catch (IOException e) {
                statusLabel.setText("Klaida: " + e.getMessage());
                AlertManager.showError("Klaida", "Nepavyko užkrauti failo: " + e.getMessage());
            }
        }
    }
    
    /**
     * Skaito CSV failą ir užkrauna į TableView
     */
    private void loadCSV(File file) throws IOException {
        List<String[]> rows = new ArrayList<>();
        List<String> headers = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (firstLine) {
                    // Pirmoji eilutė - stulpelių pavadinimai
                    headers = new ArrayList<>();
                    for (String value : values) {
                        headers.add(value.trim());
                    }
                    firstLine = false;
                } else {
                    // Kitos eilutės - duomenys
                    rows.add(values);
                }
            }
        }
        
        // Išvalome senus duomenis
        tableData.clear();
        tableView.getColumns().clear();
        
        if (headers == null || headers.isEmpty()) {
            throw new IOException("CSV failas neturi stulpelių pavadinimų");
        }
        
        // Sukuriame stulpelius
        for (int i = 0; i < headers.size(); i++) {
            String headerName = headers.get(i);
            TableColumn<Map<String, String>, String> column = new TableColumn<>(headerName);
            column.setCellValueFactory(data -> {
                Map<String, String> row = data.getValue();
                String value = row.get(headerName);
                return new javafx.beans.property.SimpleStringProperty(value != null ? value : "");
            });
            tableView.getColumns().add(column);
        }
        
        // Užkrauname duomenis
        for (String[] row : rows) {
            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                String value = (i < row.length) ? row[i].trim() : "";
                rowData.put(headers.get(i), value);
            }
            tableData.add(rowData);
        }
        
        messageLabel.setText("Užkrauta eilučių: " + rows.size());
    }
    
    /**
     * Grįžti į pagrindinį langą
     */
    @FXML
    private void goBack() {
        changeScene("views/MainView.fxml");
        if (stage != null) {
            stage.setTitle("IA Base Application");
        }
    }
    
    /**
     * Uždaryti aplikaciją
     */
    @FXML
    private void closeApplication() {
        if (stage != null) {
            stage.close();
        }
    }
    
    @Override
    public void setStage(javafx.stage.Stage stage) {
        super.setStage(stage);
        // Kai langas uždaromas, sustabdykite Timeline
        if (stage != null) {
            stage.setOnCloseRequest(e -> {
                if (dateTimeTimeline != null) {
                    dateTimeTimeline.stop();
                }
            });
        }
    }
}
