package com.ia.ia_base.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * About window controller.
 */
public class AboutController extends BaseController {
    
    @FXML
    private Label aboutLabel;
    
    @FXML
    private Button closeButton;
    
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        aboutLabel.setText("IA Base Application\n\n" +
                          "Base application for IB HL Computer Science IA projects.\n" +
                          "Created with JavaFX and MySQL.\n\n" +
                          "Version: 1.0");
        
        closeButton.setOnAction(e -> closeWindow());
    }
}

