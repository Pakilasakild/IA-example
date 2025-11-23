package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ImportFlashcardController extends BaseController{
    @FXML
    public Button selectFileBTN;
    @FXML
    public TableView fileContentsTableView;
    @FXML
    public TableColumn column1;
    @FXML
    public TableColumn column2;
    @FXML
    public Button uploadBTN;
    @FXML
    public Button cancelImportBTN;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupMenuActions();
    }
    public void setupMenuActions(){
        cancelImportBTN.setOnAction(e -> {
            closeWindow();
        });
    }
}
