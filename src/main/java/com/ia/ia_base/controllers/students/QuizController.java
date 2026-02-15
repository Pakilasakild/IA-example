package com.ia.ia_base.controllers.students;

import com.ia.ia_base.controllers.BaseController;
import com.ia.ia_base.database.dao.QuizDAO;
import com.ia.ia_base.database.dao.QuizTagDAO;
import com.ia.ia_base.database.dao.TagDAO;
import com.ia.ia_base.models.Quiz;
import com.ia.ia_base.models.Tag;
import com.ia.ia_base.util.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController extends BaseController {

    private final QuizDAO quizDAO = new QuizDAO();
    private final QuizTagDAO quizTagDAO = new QuizTagDAO();
    private final TagDAO tagDAO = new TagDAO();
    private final ObservableList<Quiz> allQuizzes = FXCollections.observableArrayList();
    private final ObservableList<Quiz> filteredQuizzes = FXCollections.observableArrayList();
    private final ObservableList<Tag> allTags = FXCollections.observableArrayList();
    private final Tag ALL_TAG = new Tag("All");
    @FXML
    private Button startQuizBTN;
    @FXML
    private ListView<Quiz> quizListView;
    @FXML
    private ComboBox<Tag> tagSelect;

    {
        ALL_TAG.setId(-1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupQuizListView();

        quizListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setupTagComboBox();
        loadInitialData();

        startQuizBTN.setOnAction(e -> startSession());
    }

    private void setupQuizListView() {
        quizListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Quiz item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    return;
                }

                String name = item.getName() == null ? "" : item.getName();
                List<String> tags = item.getTags();

                String tagText = (tags == null || tags.isEmpty())
                        ? ""
                        : " [" + String.join(", ", tags) + "]";

                setText(name + tagText);
            }
        });
    }

    private void setupTagComboBox() {
        tagSelect.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Tag item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTagName());
            }
        });
        tagSelect.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tag item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTagName());
            }
        });

        tagSelect.valueProperty().addListener((obs, oldVal, newVal) -> applyTagFilter(newVal));
    }

    private void loadInitialData() {
        try {
            allTags.clear();
            allTags.add(ALL_TAG);
            allTags.addAll(tagDAO.findAll());
            tagSelect.setItems(allTags);
            tagSelect.getSelectionModel().select(ALL_TAG);

            allQuizzes.clear();
            List<Quiz> quizzes = quizDAO.findAll();

            for (Quiz q : quizzes) {
                List<Tag> quizTags = quizTagDAO.findTagsForQuiz(q.getId());
                ArrayList<String> tagNames = new ArrayList<>();
                for (Tag t : quizTags) {
                    tagNames.add(t.getTagName());
                }
                q.setTags(tagNames);
            }

            allQuizzes.addAll(quizzes);

            filteredQuizzes.setAll(allQuizzes);
            quizListView.setItems(filteredQuizzes);

            quizListView.getSelectionModel().clearSelection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void applyTagFilter(Tag selectedTag) {
        if (selectedTag == null || selectedTag.getId() == -1) {
            filteredQuizzes.setAll(allQuizzes);
            quizListView.getSelectionModel().clearSelection();
            return;
        }

        String tagName = selectedTag.getTagName();
        filteredQuizzes.setAll(
                allQuizzes.filtered(q ->
                        q.getTags() != null && q.getTags().contains(tagName)
                )
        );

        Quiz selected = quizListView.getSelectionModel().getSelectedItem();
        if (selected != null && !filteredQuizzes.contains(selected)) {
            quizListView.getSelectionModel().clearSelection();
        }
    }

    private void startSession() {
        Quiz quiz = quizListView.getSelectionModel().getSelectedItem();

        if (quiz == null){
            AlertManager.showError("No quiz selected", "Please select a quiz.");
        }
        else {
            //TODO Add functionality
        }
    }
}
