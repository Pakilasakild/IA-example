package com.ia.ia_base.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Apie lango controlleris.
 */
public class AboutController extends BaseController {
    
    @FXML
    private Label aboutLabel;
    
    @FXML
    private Button closeButton;
    
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        aboutLabel.setText("IA Base Application\n\n" +
                          "Bazinė aplikacija IB HL Computer Science IA projektams.\n" +
                          "Sukurta su JavaFX ir MySQL.\n\n" +
                          "Versija: 1.0");
        
        closeButton.setOnAction(e -> closeWindow());
    }
}

