package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import com.ia.ia_base.database.dao.FlashcardDAO;
import com.ia.ia_base.models.Flashcard;
import com.ia.ia_base.util.AlertManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditFlashcardController extends BaseController {

    @FXML
    public Button commitChangesBTN;
    @FXML
    public TextField questionField;
    @FXML
    public TextField answerField;
    @FXML
    public Button cancelBTN;
    @FXML
    public ListView tagsListView;
    private Flashcard flashcard;
    private Runnable onSaved;

    public void setFlashcard(Flashcard flashcard) {

        this.flashcard = flashcard;

        questionField.setText(flashcard.getQuestion());
        answerField.setText(flashcard.getAnswer());
    }
    public void setOnSaved(Runnable onSaved) {
        this.onSaved = onSaved;
    }

    @FXML
    private void onCommitBTN() {
        try {
            flashcard.setQuestion(questionField.getText());
            flashcard.setAnswer(answerField.getText());

            FlashcardDAO flashcardDAO;
            flashcardDAO = new FlashcardDAO();
            flashcardDAO.update(flashcard);

            if (onSaved != null) onSaved.run();

            ((Stage) commitChangesBTN.getScene().getWindow()).close();
        } catch (SQLException e) {
            AlertManager.showError("Database Error", e.getMessage());
        }
    }
    @FXML
    private void onCancelBTN() {
        ((Stage) cancelBTN.getScene().getWindow()).close();
    }
}
