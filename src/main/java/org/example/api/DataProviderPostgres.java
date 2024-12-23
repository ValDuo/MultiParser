package org.example.api;

import org.example.api.IDataProvider;
import org.example.models.HistoryContent;
import java.sql.*;
import java.util.Optional;

public class DataProviderPostgres implements IDataProvider<HistoryContent> {

    private Connection connection;

    public DataProviderPostgres() {
        initDataSource();  // Инициализация источника данных
    }

    @Override
    public void initDataSource() {
        try {
            String url = "jdbc:postgresql://localhost:5432/yourdatabase"; // Укажите свой URL
            String user = "yourusername";  // Укажите свой пользователь
            String password = "yourpassword";  // Укажите свой пароль
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveRecord(HistoryContent record) {
        String query = "INSERT INTO history (content) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, record.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(Long id) {
        String query = "DELETE FROM history WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HistoryContent getRecordById(Long id) {
        String query = "SELECT * FROM history WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HistoryContent(rs.getLong("id"), rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
