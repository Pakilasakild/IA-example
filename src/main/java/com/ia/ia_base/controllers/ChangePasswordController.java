package com.ia.ia_base.controllers;

import com.ia.ia_base.util.AlertManager;
import com.ia.ia_base.util.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController extends BaseController{

    @FXML
    public Button changePasswordBTN;
    @FXML
    public PasswordField oldPasswordField;
    @FXML
    public PasswordField newPasswordField;
    @FXML
    public PasswordField repeatNewPasswordField;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupButtonAction();
    }

    public void setupButtonAction(){

        //TODO variables for fields, add all logic
        changePasswordBTN.setOnAction(e -> {
            if (oldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() || repeatNewPasswordField.getText().isEmpty()){
                AlertManager.showError("Error", "Fill all the fields with the correct information");
                return;
            }
            if (PasswordHasher.verifyPassword(p))

        });
    }
}
