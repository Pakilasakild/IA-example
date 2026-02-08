package com.ia.ia_base.controllers;

import com.ia.ia_base.config.AppConfig;
import com.ia.ia_base.database.DatabaseConnection;
import com.ia.ia_base.util.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeController extends BaseController {

    private static final String FLASHCARD_TABLE = "flashcards";
    private static final String QUIZ_TABLE = "quizzes";

    @FXML public Text greetingText;
    @FXML public Text flashcardAmountText;
    @FXML public Text quizAmountText;

    private final Runnable refreshHandler = this::refreshCounts;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setupGreeting();
        refreshCounts();

        QuizReloadBus.register(refreshHandler);
        FlashcardReloadBus.register(refreshHandler);
        InformationReloadBus.register(refreshHandler);

        greetingText.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getWindow().setOnHidden(e -> {
                    QuizReloadBus.unregister(refreshHandler);
                    FlashcardReloadBus.unregister(refreshHandler);
                    InformationReloadBus.unregister(refreshHandler);
                });
            }
        });
    }

    private void setupGreeting() {
        String greet = "Welcome, " + SessionManager.getInstance().getCurrentUser().getEmail();
        greetingText.setText(greet);
    }

    private void refreshCounts() {
        int flashCount = fetchCount(FLASHCARD_TABLE);
        int quizCount = fetchCount(QUIZ_TABLE);

        flashcardAmountText.setText("There are currently " + flashCount + " flashcards");
        quizAmountText.setText("There are currently " + quizCount + " quizzes");
    }

    private int fetchCount(String tableName) {
        if (!AppConfig.isUseDatabase()) {
            return 0;
        }

        String sql = "SELECT COUNT(*) AS total FROM " + tableName;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getInt("total") : 0;

        } catch (SQLException e) {
            AlertManager.showError("Database Error", "Failed to load " + tableName + " count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
