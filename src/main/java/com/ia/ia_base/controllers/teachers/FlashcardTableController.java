package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import com.ia.ia_base.database.dao.FlashcardDAO;
import com.ia.ia_base.models.Flashcard;
import com.ia.ia_base.util.AlertManager;
import com.ia.ia_base.util.FlashcardReloadBus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlashcardTableController extends BaseController {

    @FXML public TableView<Flashcard> flashTableTeach;
    @FXML public TableColumn<Flashcard, String> questionsFlashColumn;
    @FXML public TableColumn<Flashcard, ArrayList<String>> tagsFlashColumn;
    @FXML public TableColumn<Flashcard, Boolean> activeFlashColumn;
    @FXML public TableColumn<Flashcard, Void> editFlashColumn;
    @FXML public TableColumn<Flashcard, Void> delFlashColumn;

    private FlashcardDAO flashcardDAO;

    // One shared list for the table
    private final ObservableList<Flashcard> flashcardsObs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flashcardDAO = new FlashcardDAO();

        setupColumns();

        flashTableTeach.setEditable(true); // REQUIRED for checkbox clicking
        flashTableTeach.setItems(flashcardsObs);

        reloadTable();
        FlashcardReloadBus.register(this::reloadTable);
    }

    public void reloadTable() {
        try {
            List<Flashcard> flashcards = flashcardDAO.findAll();

            for (Flashcard fc : flashcards) {
                attachActiveListener(fc);
            }

            flashcardsObs.setAll(flashcards);
        } catch (SQLException e) {
            AlertManager.showError("Database Error", "Failed to reload flashcards: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        // Must match getQuestion()
        questionsFlashColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        // Tags (optional)
        tagsFlashColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));
        tagsFlashColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(ArrayList<String> item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.join(", ", item));
            }
        });

        // Active checkbox
        activeFlashColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        activeFlashColumn.setCellFactory(CheckBoxTableCell.forTableColumn(activeFlashColumn));
        activeFlashColumn.setEditable(true);

        // Edit link (PASS FLASHCARD)
        editFlashColumn.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink editLink = new Hyperlink("Edit");
            {
                editLink.setOnAction(e -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    openEditWindow(flashcard);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editLink);
            }
        });

        // Delete link (delete + refresh immediately)
        delFlashColumn.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink deleteLink = new Hyperlink("Delete");
            {
                deleteLink.setOnAction(e -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    onDelete(flashcard);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteLink);
            }
        });
    }

    private void attachActiveListener(Flashcard fc) {
        final boolean[] guard = {false};
        fc.activeProperty().addListener((obs, oldVal, newVal) -> {
            if (guard[0]) return;

            try {
                flashcardDAO.update(fc);
            } catch (Exception ex) {
                guard[0] = true;
                fc.setActive(oldVal);
                guard[0] = false;

                AlertManager.showError("Database Error",
                        "Failed to update active status for id=" + fc.getId() + ": " + ex.getMessage());
            }
        });
    }

    private void openEditWindow(Flashcard flashcard) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/ia/ia_base/IA/Teachers/editFlashcards.fxml"
            ));
            Parent root = loader.load();

            // IMPORTANT: replace EditFlashcardsController with YOUR edit controller class name
            EditFlashcardController editCtrl = loader.getController();

            // Pass the flashcard to edit window
            editCtrl.setFlashcard(flashcard);

            // After saving/closing edit, refresh table (because question/answer are plain Strings)
            editCtrl.setOnSaved(() -> {
                flashTableTeach.refresh();
                reloadTable();
            });

            Stage stage = new Stage();
            stage.setTitle("Edit flashcard");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(flashTableTeach.getScene().getWindow());
            stage.show();

        } catch (Exception e) {
            AlertManager.showError("UI Error", "Failed to open Edit window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onDelete(Flashcard flashcard) {
        try {
            if(AlertManager.showConfirmation("Delete flashcard", "Are you sure you want to delete this flashcard?", "")){
                flashcardDAO.delete(flashcard.getId());
                flashcardsObs.remove(flashcard);  // refresh instantly
                flashTableTeach.refresh();
            }
        } catch (SQLException e) {
            AlertManager.showError("Database Error",
                    "Failed to delete flashcard id=" + flashcard.getId() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
