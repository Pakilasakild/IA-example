package com.ia.ia_base.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class for managing alerts and dialog windows.
 * All alerts should be created through this class.
 */
public class AlertManager {
    
    /**
     * Shows information alert
     */
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows error alert
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows warning alert
     */
    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Shows confirmation alert
     * @return true if OK selected, false if Cancel
     */
    public static boolean showConfirmation(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    /**
     * Shows confirmation alert with simpler interface
     * @return true if OK selected, false if Cancel
     */
    public static boolean confirm(String message) {
        return showConfirmation("Confirmation", null, message);
    }
    
    /**
     * Shows confirmation alert to exit application
     * @return true if OK selected, false if Cancel
     */
    public static boolean confirmExit() {
        return showConfirmation("Confirmation", "Exit Application", 
                              "Are you sure you want to exit the application?");
    }
    
    /**
     * Shows confirmation alert to delete record
     * @return true if OK selected, false if Cancel
     */
    public static boolean confirmDelete(String itemName) {
        return showConfirmation("Confirmation", "Delete Record", 
                              "Are you sure you want to delete: " + itemName + "?");
    }
}

