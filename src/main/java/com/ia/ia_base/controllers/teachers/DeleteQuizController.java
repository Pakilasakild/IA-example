package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DeleteQuizController extends BaseController {

    @FXML
    public Button deleteQuizBTN;
    @FXML
    public Button cancelBTN;
    @FXML
    public ComboBox chooseQuizCombo;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupMenuActions();
    }

    public void setupMenuActions() {
        cancelBTN.setOnAction(e -> {
            closeWindow();
        });
    }
}
