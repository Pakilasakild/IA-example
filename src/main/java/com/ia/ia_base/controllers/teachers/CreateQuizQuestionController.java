package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateQuizQuestionController extends BaseController {

    @FXML
    public TextField enterQuestionTextField;
    @FXML
    public Button createQuestionBTN;
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
