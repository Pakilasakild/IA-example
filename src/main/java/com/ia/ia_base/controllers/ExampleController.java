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
 * Example window controller.
 * Shows how easy it is to create a new window with status bar.
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
        messageLabel.setText("Welcome to the example window!");
        
        // Set status bar
        statusLabel.setText("Ready");
        
        // Initialize TableView
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
        
        // Set date and time
        updateDateTime();
        
        // Create Timeline that updates date and time every second
        dateTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateDateTime())
        );
        dateTimeTimeline.setCycleCount(Animation.INDEFINITE);
        dateTimeTimeline.play();
    }
    
    /**
     * Updates date and time in status bar
     */
    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
    }
    
    /**
     * Loads CSV file into TableView
     */
    @FXML
    private void loadCSVFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv", "*.txt")
        );
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                loadCSV(file);
                statusLabel.setText("Loaded: " + file.getName());
            } catch (IOException e) {
                statusLabel.setText("Error: " + e.getMessage());
                AlertManager.showError("Error", "Failed to load file: " + e.getMessage());
            }
        }
    }
    
    /**
     * Reads CSV file and loads into TableView
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
                    // First line - column names
                    headers = new ArrayList<>();
                    for (String value : values) {
                        headers.add(value.trim());
                    }
                    firstLine = false;
                } else {
                    // Other lines - data
                    rows.add(values);
                }
            }
        }
        
        // Clear old data
        tableData.clear();
        tableView.getColumns().clear();
        
        if (headers == null || headers.isEmpty()) {
            throw new IOException("CSV file does not have column headers");
        }
        
        // Create columns
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
        
        // Load data
        for (String[] row : rows) {
            Map<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headers.size(); i++) {
                String value = (i < row.length) ? row[i].trim() : "";
                rowData.put(headers.get(i), value);
            }
            tableData.add(rowData);
        }
        
        messageLabel.setText("Loaded rows: " + rows.size());
    }
    
    /**
     * Go back to main window
     */
    @FXML
    private void goBack() {
        changeScene("views/MainView.fxml");
        if (stage != null) {
            stage.setTitle("IA Base Application");
        }
    }
    
    /**
     * Close application
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
        // When window closes, stop Timeline
        if (stage != null) {
            stage.setOnCloseRequest(e -> {
                if (dateTimeTimeline != null) {
                    dateTimeTimeline.stop();
                }
            });
        }
    }
}
