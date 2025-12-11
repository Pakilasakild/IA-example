package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateFlashcardController extends BaseController {

    @FXML
    public AnchorPane createFlashcard;
    @FXML
    public TextField questionTextFieldFlash;
    @FXML
    public TextField answerTextFieldFlash;
    @FXML
    public Button createFlashcardBTN;
    @FXML
    public Button cancelBTN;


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupMenuActions();
    }

    public void setupMenuActions(){
        cancelBTN.setOnAction(e -> {
            closeWindow();
        });
    }
}
