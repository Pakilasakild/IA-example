package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import com.ia.ia_base.models.Flashcard;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.ArrayList;

public class FlashcardTableController extends BaseController {

    @FXML
    public TableView<Flashcard> flashTableTeach;
    @FXML
    public TableColumn<Flashcard, String> questionsFlashColumn;
    @FXML
    public TableColumn<Flashcard, ArrayList<String>> tagsFlashColumn;
    @FXML
    public TableColumn<Flashcard, Boolean> activeFlashColumn;
    @FXML
    public TableColumn<Flashcard, Void> editFlashColumn;
    @FXML
    public TableColumn<Flashcard, Void> delFlashColumn;

    private void initialize() {
        setupColumns();
    }

    private void setupColumns() {
        // Active column (checkbox)
        activeFlashColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        activeFlashColumn.setCellFactory(CheckBoxTableCell.forTableColumn(activeFlashColumn));
        activeFlashColumn.setEditable(true);

        editFlashColumn.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink editLink = new Hyperlink("Edit");
            // TODO: fix column having checkbox hyperlink: https://pastebin.com/U8Y5K8es
            {
                editLink.setOnAction(e -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    onEdit(flashcard);
                });
            }
        });
}

    private void onEdit(Flashcard flashcard) {
        openNewWindow("com/ia/ia_base/IA/Teachers/editFlashcards.fxml", "Edit flashcard");
    }
}