package com.ia.ia_base.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Klasė alertų ir dialogo langų valdymui.
 * Visi alertai turėtų būti sukurti per šią klasę.
 */
public class AlertManager {
    
    /**
     * Rodo informacijos alertą
     */
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Rodo klaidos alertą
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Rodo įspėjimo alertą
     */
    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Rodo patvirtinimo alertą
     * @return true jei pasirinkta OK, false jei Atšaukti
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
     * Rodo patvirtinimo alertą su paprastesniu interfeisu
     * @return true jei pasirinkta OK, false jei Atšaukti
     */
    public static boolean confirm(String message) {
        return showConfirmation("Patvirtinimas", null, message);
    }
    
    /**
     * Rodo patvirtinimo alertą išeiti iš programos
     * @return true jei pasirinkta OK, false jei Atšaukti
     */
    public static boolean confirmExit() {
        return showConfirmation("Patvirtinimas", "Išeiti iš programos", 
                              "Ar tikrai norite išeiti iš programos?");
    }
    
    /**
     * Rodo patvirtinimo alertą ištrinti įrašą
     * @return true jei pasirinkta OK, false jei Atšaukti
     */
    public static boolean confirmDelete(String itemName) {
        return showConfirmation("Patvirtinimas", "Ištrinti įrašą", 
                              "Ar tikrai norite ištrinti: " + itemName + "?");
    }
}

