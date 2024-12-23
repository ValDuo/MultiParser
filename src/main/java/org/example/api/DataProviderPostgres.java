package org.example.api;

import org.apache.log4j.Logger;
import org.example.models.PersForApi;
import ru.sfedu.dubina.Constants;

import java.sql.*;

public class DataProviderPostgres implements IDataProvider<PersForApi> {
    private Logger logger = Logger.getLogger(DataProviderPostgres.class);
    private Connection connection;

    @Override
    public void initDataSource() {
        try {
            connection = DriverManager.getConnection(Constants.URL, Constants.USER, Constants.PASSWORD);
            logger.info("Соединение с базой данных установлено.");
        } catch (SQLException e) {
            logger.error("Ошибка соединения с базой данных.", e);
        }
    }

    @Override
    public void saveRecord(PersForApi record) {
        String sql = "INSERT INTO persons (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, record.getName());
            stmt.setString(2, record.getEmail());
            stmt.executeUpdate();
            logger.info("Запись сохранена");
        } catch (SQLException e) {
            logger.error("Ошибка сохранения записи.", e);
        }
    }

    @Override
    public void deleteRecord(Long id) {
        String sql = "DELETE FROM persons WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Запись удалена.");
            } else {
                logger.warn("Запись не найдена.");
            }
        } catch (SQLException e) {
            logger.error("Ошибка удаления записи.", e);
        }
    }

    @Override
    public PersForApi getRecordById(Long id) {
        String sql = "SELECT * FROM persons WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PersForApi record = new PersForApi(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                logger.info("Запись найдена");
                return record;
            }
        } catch (SQLException e) {
            logger.error("ошибка получения записи по айдишнику.", e);
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Соединение с базой данных завершено.");
            }
        } catch (SQLException e) {
            logger.error("Ошибка завершения процесса соединения с базой данных.", e);
        }
    }
}
