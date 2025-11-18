package com.ia.ia_base.controllers;

import com.ia.ia_base.util.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Bazinė Controller klasė.
 * Visi kiti controlleriai gali paveldėti šią klasę.
 */
public abstract class BaseController implements Initializable {
    protected Stage stage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bazinė inicializacija - gali būti perrašyta vaikinėse klasėse
    }
    
    /**
     * Nustato Stage objektą (naudojama iš Application klasės)
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Atidaro naują langą
     */
    protected void openNewWindow(String fxmlPath, String title) {
        WindowManager.openWindow(fxmlPath, title);
    }
    
    /**
     * Atidaro modalinį langą
     */
    protected void openModalWindow(String fxmlPath, String title) {
        WindowManager.openWindow(fxmlPath, title, true);
    }
    
    /**
     * Pakeičia esamo lango turinį (sceną)
     * Naudinga kai norite pakeisti langą vietoj atidarymo naujo
     */
    protected void changeScene(String fxmlPath) {
        if (stage == null) {
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/ia/ia_base/" + fxmlPath)
            );
            Parent root = loader.load();
            
            // Nustatome Stage objektą naujame controlleryje
            BaseController controller = loader.getController();
            if (controller != null) {
                controller.setStage(stage);
            }
            
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            System.err.println("Klaida keičiant langą: " + fxmlPath);
            e.printStackTrace();
        }
    }
    
    /**
     * Uždaro esamą langą
     */
    protected void closeWindow() {
        if (stage != null) {
            WindowManager.closeWindow(stage);
        }
    }
}

