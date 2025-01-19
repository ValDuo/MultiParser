package ru.sfedu.dubina.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import ru.sfedu.dubina.models.*;
import ru.sfedu.dubina.utils.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DataProviderPostgres  {
    private Logger logger = Logger.getLogger(DataProviderPostgres.class);
    private static Connection connection;

    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null) {
            Properties props = new Properties();
            try(InputStream input = DataProviderPostgres.class.getClassLoader().
                    getResourceAsStream("database.properties")) {
                props.load(input);
            }
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    // CRUD-операции для BigCSVCutter

    public boolean createBigCSVCutter(BigCSVCutter cutter) {
        String sql = "INSERT INTO bigCSVCutter (countLine, countFile, date, folder, created, id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, cutter.getCountLine());
            statement.setInt(2, cutter.getCountFile());
            statement.setString(3, cutter.getDate());
            statement.setString(4, cutter.getFolder());
            statement.setBoolean(5, cutter.getCreated());
            statement.setObject(6, cutter.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи BigCSVCutter.", e);
        }
        return false;
    }

    public BigCSVCutter getBigCSVCutterById(UUID id) {
        String sql = "SELECT * FROM bigCSVCutter WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new BigCSVCutter(
                            resultSet.getInt("countLine"),
                            resultSet.getInt("countFile"),
                            resultSet.getString("date"),
                            resultSet.getString("folder"),
                            resultSet.getBoolean("created")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи BigCSVCutter.", e);
        }
        return null;
    }

    public boolean updateBigCSVCutter(UUID id, BigCSVCutter cutter) {
        String sql = "UPDATE bigCSVCutter SET countLine = ?, countFile = ?, date = ?, folder = ?, created = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, cutter.getCountLine());
            statement.setInt(2, cutter.getCountFile());
            statement.setString(3, cutter.getDate());
            statement.setString(4, cutter.getFolder());
            statement.setBoolean(5, cutter.getCreated());
            statement.setObject(6, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи BigCSVCutter.", e);
        }
        return false;
    }


    public boolean deleteBigCSVCutter(UUID id) {
        String sql = "DELETE FROM bigCSVCutter WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи BigCSVCutter.", e);
        }
        return false;
    }

    //операции с классом CSVReader

    public boolean createCSVReader(CSVReader reader) {
        String sql = "INSERT INTO CSV_Readers (path_name, csv_file, words, id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, reader.getPathName());
            statement.setString(2, reader.getCsvFile());
            statement.setObject(3, reader.getWords());
            statement.setObject(4, reader.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи CSVReader.", e);
        }
        return false;
    }


    public CSVReader getCSVReaderById(UUID id) {
        String sql = "SELECT * FROM CSV_Readers WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new CSVReader(
                            resultSet.getString("path_name"),
                            resultSet.getString("csv_file"),
                            resultSet.getInt("words")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи CSVReader.", e);
        }
        return null;
    }


    public boolean updateCSVReader(UUID id, CSVReader reader) {
        String sql = "UPDATE CSV_Readers SET path_name = ?, csv_file = ?, words = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, reader.getPathName());
            statement.setString(2, String.valueOf(reader.getCsvFile()));
            statement.setInt(3, reader.getWords());
            statement.setObject(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи CSVReader.", e);
        }
        return false;
    }

    public boolean deleteCSVReader(UUID id) {
        String sql = "DELETE FROM CSV_Readers WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи CSVReader.", e);
        }
        return false;
    }

    //crud с классом debtorlist

    public boolean createDebtorList(DebtorList debtor) {
        String sql = "INSERT INTO Debtor_List (owner_name, payer_name, accrued_money, returned_money, create_date_of_payment, upload_date_of_payment, kadastr, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, debtor.getOwnerName());
            statement.setString(2, debtor.getPayerName());
            statement.setLong(3, debtor.getAccruedMoney());
            statement.setLong(4, debtor.getReturnedMoney());
            statement.setTimestamp(5, Timestamp.valueOf(debtor.getCreateDateOfPayment()));
            statement.setTimestamp(6, new Timestamp(debtor.getUploadDateOfPayment().getTime()));
            statement.setBoolean(7, debtor.getKadastr());
            statement.setObject(8, debtor.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи DebtorList.", e);
        }
        return false;
    }

    // Read
    public DebtorList getDebtorListById(UUID id) {
        String sql = "SELECT * FROM Debtor_List WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DebtorList(
                            resultSet.getString("owner_name"),
                            resultSet.getString("payer_name"),
                            resultSet.getLong("accrued_money"),
                            resultSet.getLong("returned_money"),
                            Date.from(resultSet.getTimestamp("create_date_of_payment").toInstant()),
                            new Date(resultSet.getTimestamp("upload_date_of_payment").getTime()),
                            resultSet.getBoolean("kadastr")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи DebtorList.", e);
        }
        return null;
    }

    // Update
    public boolean updateDebtorList(UUID id, DebtorList debtor) {
        String sql = "UPDATE Debtor_List SET owner_name = ?, payer_name = ?, accrued_money = ?, returned_money = ?, create_date_of_payment = ?, upload_date_of_payment = ?, kadastr = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, debtor.getOwnerName());
            statement.setString(2, debtor.getPayerName());
            statement.setLong(3, debtor.getAccruedMoney());
            statement.setLong(4, debtor.getReturnedMoney());
            statement.setTimestamp(5, Timestamp.valueOf(debtor.getCreateDateOfPayment()));
            statement.setTimestamp(6, new Timestamp(debtor.getUploadDateOfPayment().getTime()));
            statement.setBoolean(7, debtor.getKadastr());
            statement.setObject(8, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи DebtorList.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteDebtorList(UUID id) {
        String sql = "DELETE FROM Debtor_List WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи DebtorList.", e);
        }
        return false;
    }

    //crud historycontent

    public boolean createHistoryContent(HistoryContent history) {
        String sql = "INSERT INTO HistoryContent (className, createdDate, actor, methodName, object, status, id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, String.valueOf(history.getId()));
            statement.setString(2, history.getClassName());
            statement.setTimestamp(3, Timestamp.valueOf(history.getCreatedDate()));
            statement.setString(4, history.getActor());
            statement.setString(5, history.getMethodName());
            statement.setString(6, String.valueOf(history.getObject()));
            statement.setString(7, history.getStatus().name());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи HistoryContent.", e);
        }
        return false;
    }

    //метод для работы с объектами типа Map<String, Object>
    private Map<String, Object> parseObjectMap(String objectString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(objectString, new TypeReference<Map<String, Object>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            logger.error("Ошибка при парсинге object.", e);
            return Collections.emptyMap();
        }
    }

    // Read
    public HistoryContent getHistoryContentById(UUID id) {
        String sql = "SELECT * FROM HistoryContent WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, id.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Map<String, Object> objectMap = parseObjectMap(resultSet.getString("object"));
                    return new HistoryContent(
                            resultSet.getString("className"),
                            resultSet.getTimestamp("createdDate").toLocalDateTime(),
                            resultSet.getString("actor"),
                            resultSet.getString("methodName"),
                            objectMap,
                            Status.valueOf(resultSet.getString("status"))
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи HistoryContent.", e);
        }
        return null;
    }

    // Update
    public boolean updateHistoryContent(UUID id, HistoryContent history) {
        String sql = "UPDATE HistoryContent SET className = ?, createdDate = ?, actor = ?, methodName = ?, object = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, history.getClassName());
            statement.setTimestamp(2, Timestamp.valueOf(history.getCreatedDate()));
            statement.setString(3, history.getActor());
            statement.setString(4, history.getMethodName());
            statement.setString(5, history.getObject().toString());
            statement.setString(6, history.getStatus().name());
            statement.setString(7, id.toString());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи HistoryContent.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteHistoryContent(UUID id) {
        String sql = "DELETE FROM HistoryContent WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, String.valueOf(id));
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи HistoryContent.", e);
        }
        return false;
    }

    //crud incoming emails
    public boolean createIncomingEmail(IncomingEmails email) {
        String sql = "INSERT INTO incoming_emails (email_address, email_sender, email_receiver, id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, email.getEmailAddress());
            statement.setString(2, email.getEmailSender());
            statement.setString(3, email.getEmailReceiver());
            statement.setObject(4, email.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи IncomingEmails.", e);
        }
        return false;
    }

    // Read
    public IncomingEmails getIncomingEmailById(UUID id) {
        String sql = "SELECT * FROM incoming_emails WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new IncomingEmails(
                            resultSet.getString("email_address"),
                            resultSet.getString("email_sender"),
                            resultSet.getString("email_receiver")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи incoming_emails.", e);
        }
        return null;
    }

    // Update
    public boolean updateIncomingEmail(UUID id, IncomingEmails email) {
        String sql = "UPDATE incoming_emails SET email_address = ?, email_sender = ?, email_receiver = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, email.getEmailAddress());
            statement.setString(2, email.getEmailSender());
            statement.setString(3, email.getEmailReceiver());
            statement.setObject(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи incoming_emails.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteIncomingEmail(UUID id) {
        String sql = "DELETE FROM incoming_emails WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи incoming_emails.", e);
        }
        return false;
    }


    // CRUD operations for SeleniumParser

    // Create
    public boolean createSeleniumParser(SeleniumParser parser) {
        String sql = "INSERT INTO parcer (id, driver, srcDstFiles) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, String.valueOf(parser.getId()));
            statement.setInt(2, parser.getDriver());
            statement.setString(3, parser.getSrcDstFiles());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи parcer.", e);
        }
        return false;
    }

    // Read
    public SeleniumParser getSeleniumParserById(UUID id) {
        String sql = "SELECT * FROM parcer WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, String.valueOf(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SeleniumParser(
                            resultSet.getInt("driver"),
                            resultSet.getString("srcDstFiles")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи parcer.", e);
        }
        return null;
    }

    // Update
    public boolean updateSeleniumParser(UUID id, SeleniumParser parser) {
        String sql = "UPDATE parcer SET driver = ?, srcDstFiles = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, parser.getDriver());
            statement.setString(2, parser.getSrcDstFiles());
            statement.setString(3, String.valueOf(id));
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи parcer.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteSeleniumParser(UUID id) {
        String sql = "DELETE FROM parcer WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, String.valueOf(id));
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи parcer.", e);
        }
        return false;
    }

    // CRUD operations for WindowApp

    // Create
    public boolean createWindowApp(WindowApp windowApp) {
        String sql = "INSERT INTO window_app (kadastrNumber, personalAddress, personalAccount, personalNumber, square, emailCounter, personalID, createDate, uploadDate, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, windowApp.getKadastrNumber());
            statement.setString(2, windowApp.getPersonalAddress());
            statement.setString(3, windowApp.getPersonalAccount());
            statement.setLong(4, windowApp.getPersonalNumber());
            statement.setInt(5, windowApp.getSquare());
            statement.setInt(6, windowApp.getEmailCounter());
            statement.setInt(7, windowApp.getPersonalID());
            statement.setTimestamp(8, Timestamp.valueOf(windowApp.getCreateDate()));
            statement.setTimestamp(9, new Timestamp(windowApp.getUploadDate().getTime()));
            statement.setObject(10, windowApp.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи WindowApp.", e);
        }
        return false;
    }

    // Read
    public WindowApp getWindowAppById(UUID id) {
        String sql = "SELECT * FROM window_app WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new WindowApp(
                            resultSet.getString("kadastrNumber"),
                            resultSet.getString("personalAddress"),
                            resultSet.getString("personalAccount"),
                            resultSet.getLong("personalNumber"),
                            resultSet.getInt("square"),
                            resultSet.getInt("emailCounter"),
                            resultSet.getInt("personalID"),
                            new Date(resultSet.getTimestamp("uploadDate").getTime())
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи WindowApp.", e);
        }
        return null;
    }

    // Update
    public boolean updateWindowApp(UUID id, WindowApp windowApp) {
        String sql = "UPDATE window_app SET kadastrNumber = ?, personalAddress = ?, personalAccount = ?, personalNumber = ?, square = ?, emailCounter = ?, personalID = ?, createDate = ?, uploadDate = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, windowApp.getKadastrNumber());
            statement.setString(2, windowApp.getPersonalAddress());
            statement.setString(3, windowApp.getPersonalAccount());
            statement.setLong(4, windowApp.getPersonalNumber());
            statement.setInt(5, windowApp.getSquare());
            statement.setInt(6, windowApp.getEmailCounter());
            statement.setInt(7, windowApp.getPersonalID());
            statement.setTimestamp(8, Timestamp.valueOf(windowApp.getCreateDate()));
            statement.setTimestamp(9, new Timestamp(windowApp.getUploadDate().getTime()));
            statement.setObject(10, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи WindowApp.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteWindowApp(UUID id) {
        String sql = "DELETE FROM window_app WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи WindowApp.", e);
        }
        return false;
    }


    // CRUD operations for WithKadastrList

    // Create
    public boolean createWithKadastrList(WithKadastrList withKadastrList) {
        String sql = "INSERT INTO with_kadastr_list (source, destination, created_time, id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, withKadastrList.getSource());
            statement.setString(2, withKadastrList.getDestination());
            statement.setTimestamp(3, new Timestamp(withKadastrList.getCreatedTime().getTime()));
            statement.setObject(4, withKadastrList.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи WithKadastrList.", e);
        }
        return false;
    }

    // Read
    public WithKadastrList getWithKadastrListById(UUID id) {
        String sql = "SELECT * FROM with_kadastr_list WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new WithKadastrList(
                            resultSet.getString("source"),
                            resultSet.getString("destination"),
                            new Date(resultSet.getTimestamp("created_time").getTime())
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи WithKadastrList.", e);
        }
        return null;
    }

    // Update
    public boolean updateWithKadastrList(UUID id, WithKadastrList withKadastrList) {
        String sql = "UPDATE with_kadastr_list SET source = ?, destination = ?, created_time = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, withKadastrList.getSource());
            statement.setString(2, withKadastrList.getDestination());
            statement.setTimestamp(3, new Timestamp(withKadastrList.getCreatedTime().getTime()));
            statement.setObject(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи WithKadastrList.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteWithKadastrList(UUID id) {
        String sql = "DELETE FROM with_kadastr_list WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setObject(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи WithKadastrList.", e);
        }
        return false;
    }


}
