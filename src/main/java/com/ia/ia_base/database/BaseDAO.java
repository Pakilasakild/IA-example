package com.ia.ia_base.database;

import com.ia.ia_base.config.AppConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bazinė DAO (Data Access Object) klasė.
 * Teikia pagrindinius metodus darbui su duomenų baze.
 */
public abstract class BaseDAO<T> {
    protected DatabaseConnection dbConnection;
    
    public BaseDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Patikrina ar duomenų bazė naudojama
     */
    protected void checkDatabaseEnabled() throws SQLException {
        if (!AppConfig.isUseDatabase()) {
            throw new SQLException("Duomenų bazė nenaudojama. Įjunkite ją AppConfig.setUseDatabase(true)");
        }
    }
    
    /**
     * Vykdo SELECT užklausą ir grąžina rezultatus
     */
    protected List<T> executeQuery(String sql, Object... params) throws SQLException {
        checkDatabaseEnabled();
        
        List<T> results = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setParameters(stmt, params);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToEntity(rs));
                }
            }
        }
        return results;
    }
    
    /**
     * Vykdo INSERT, UPDATE, DELETE užklausas
     */
    protected int executeUpdate(String sql, Object... params) throws SQLException {
        checkDatabaseEnabled();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setParameters(stmt, params);
            return stmt.executeUpdate();
        }
    }
    
    /**
     * Nustato parametrus PreparedStatement objektui
     */
    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
    
    /**
     * Konvertuoja ResultSet į Entity objektą
     * Turi būti implementuotas kiekvienoje vaikinėje klasėje
     */
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
}

