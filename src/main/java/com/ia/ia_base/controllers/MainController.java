package com.ia.ia_base.controllers;

import com.ia.ia_base.util.AlertManager;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * Pagrindinio lango controlleris su meniu sistema.
 */
public class MainController extends BaseController {
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private Menu fileMenu;
    
    @FXML
    private MenuItem exitMenuItem;
    
    @FXML
    private Menu viewMenu;
    
    @FXML
    private MenuItem exampleViewMenuItem;
    
    @FXML
    private Menu helpMenu;
    
    @FXML
    private MenuItem aboutMenuItem;
    
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupMenuActions();
    }
    
    /**
     * Nustato meniu veiksmus
     */
    private void setupMenuActions() {
        // Uždaryti aplikaciją su patvirtinimu
        exitMenuItem.setOnAction(e -> {
            confirmExit();
        });
        
        // Atidaryti pavyzdinį langą (pakeičia esamą langą)
        exampleViewMenuItem.setOnAction(e -> {
            changeScene("views/ExampleView.fxml");
            if (stage != null) {
                stage.setTitle("Pavyzdinis langas");
            }
        });
        
        // Apie meniu
        aboutMenuItem.setOnAction(e -> {
            openModalWindow("views/AboutView.fxml", "Apie");
        });
    }
    
    /**
     * Patvirtina ar tikrai norite išeiti iš programos
     */
    private void confirmExit() {
        if (AlertManager.confirmExit()) {
            if (stage != null) {
                stage.close();
            }
        }
    }
    
    /**
     * Atidaro pavyzdinį langą (naudojama iš mygtuko)
     * Pakeičia esamą langą vietoj atidarymo naujo
     */
    @FXML
    private void openExampleView() {
        changeScene("views/ExampleView.fxml");
        if (stage != null) {
            stage.setTitle("Pavyzdinis langas");
        }
    }
    
    /**
     * Prideda naują meniu punktą programiškai
     */
    public void addMenuItem(String menuName, String itemName, Runnable action) {
        Menu menu = menuBar.getMenus().stream()
                .filter(m -> m.getText().equals(menuName))
                .findFirst()
                .orElse(null);
        
        if (menu != null) {
            MenuItem menuItem = new MenuItem(itemName);
            menuItem.setOnAction(e -> action.run());
            menu.getItems().add(menuItem);
        }
    }
}

