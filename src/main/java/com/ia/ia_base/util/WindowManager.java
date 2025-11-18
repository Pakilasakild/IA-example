package com.ia.ia_base.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasė langų valdymui.
 * Supaprastina naujų langų atidarymą.
 */
public class WindowManager {
    
    /**
     * Atidaro naują langą iš FXML failo
     * @param fxmlPath kelias iki FXML failo (pvz., "views/MainView.fxml")
     * @param title lango pavadinimas
     * @return Stage objektas
     */
    public static Stage openWindow(String fxmlPath, String title) {
        return openWindow(fxmlPath, title, false);
    }
    
    /**
     * Atidaro naują langą iš FXML failo
     * @param fxmlPath kelias iki FXML failo
     * @param title lango pavadinimas
     * @param modal ar langas turi būti modalinis
     * @return Stage objektas
     */
    public static Stage openWindow(String fxmlPath, String title, boolean modal) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource("/com/ia/ia_base/" + fxmlPath));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            
            if (modal) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            
            // Nustatome Stage objektą controlleryje, jei controlleris yra BaseController
            Object controller = loader.getController();
            if (controller instanceof com.ia.ia_base.controllers.BaseController) {
                ((com.ia.ia_base.controllers.BaseController) controller).setStage(stage);
            }
            
            stage.show();
            return stage;
        } catch (IOException e) {
            System.err.println("Klaida atidarant langą: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Atidaro langą su nurodytais matmenimis
     */
    public static Stage openWindow(String fxmlPath, String title, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource("/com/ia/ia_base/" + fxmlPath));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();
            return stage;
        } catch (IOException e) {
            System.err.println("Klaida atidarant langą: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Uždaro langą
     */
    public static void closeWindow(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }
}

