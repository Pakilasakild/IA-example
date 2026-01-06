package com.ia.ia_base.controllers.teachers;

import com.ia.ia_base.controllers.BaseController;
import com.ia.ia_base.database.dao.FlashcardDAO;
import com.ia.ia_base.models.Flashcard;
import com.ia.ia_base.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private FlashcardDAO flashcardDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
        setupColumns();
            flashcardDAO = new FlashcardDAO();
            List<Flashcard> flashcards = flashcardDAO.findAll();
            flashTableTeach.setItems(FXCollections.observableArrayList(flashcards));
        } catch (SQLException e) {
            AlertManager.showError("Database Error", "Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupColumns() {

        //Question column
        questionsFlashColumn.setCellValueFactory(new PropertyValueFactory<>("Question"));


        //Active column (checkbox)
        activeFlashColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        activeFlashColumn.setCellFactory(CheckBoxTableCell.forTableColumn(activeFlashColumn));
        activeFlashColumn.setEditable(true);

        editFlashColumn.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink editLink = new Hyperlink("Edit");

            {
                editLink.setOnAction(e -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    onEdit(flashcard);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editLink);
            }
        });

        delFlashColumn.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink deleteLink = new Hyperlink("Delete");

            {
                deleteLink.setOnAction(e -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    onEdit(flashcard);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteLink);
            }
        });
}

    private void onEdit(Flashcard flashcard) {
        openNewWindow("com/ia/ia_base/IA/Teachers/editFlashcards.fxml", "Edit flashcard");
    }
}