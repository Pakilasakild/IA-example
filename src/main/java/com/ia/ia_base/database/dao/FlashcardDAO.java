package com.ia.ia_base.database.dao;

import com.ia.ia_base.models.Flashcard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FlashcardDAO extends BaseDAO<Flashcard> {

    public List<Flashcard> findAll() throws SQLException {
        String sql = "SELECT * from flashcards";
        return executeQuery(sql);
    }

    public Flashcard findById(int id) throws SQLException {
        String sql = "SELECT * FROM flashcards WHERE id = ?";
        List<Flashcard> results = executeQuery(sql, id);
        return results.isEmpty() ? null : results.getFirst();
    }

    public Flashcard findByQuestion(String question) throws SQLException {
        String sql = "SELECT * FROM flashcards WHERE question = ?";
        List<Flashcard> results = executeQuery(sql, question);
        return results.isEmpty() ? null : results.getFirst();
    }

    public int create(Flashcard entity) throws SQLException {
        String sql = "INSERT INTO flashcards (question, answer, active) VALUES (?, ?, ?)";
        return executeUpdate(sql, entity.getQuestion(), entity.getAnswer(), entity.isActive());
    }

    public int update(Flashcard entity) throws SQLException {
        String sql = "UPDATE flashcards SET question = ?, SET answer = ?, SET active = ? WHERE id = ?";
        return executeUpdate(sql, entity.getQuestion(), entity.getAnswer(), entity.getId());
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM flashcards WHERE id = ?";
        return executeUpdate(sql, id);
    }

    @Override
    protected Flashcard mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Flashcard(rs.getString("question"), rs.getString("answer"));
    }
}
