package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class CreateQuizController extends BaseController {
    @FXML
    public Button createQuizBTN;
    @FXML
    public Button cancelBTN;
    @FXML
    public ListView quizQuestionsListView;
    @FXML
    public ComboBox tagSelect;


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
