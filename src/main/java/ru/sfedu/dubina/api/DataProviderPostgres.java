package ru.sfedu.dubina.api;

import org.apache.log4j.Logger;

import ru.sfedu.dubina.models.*;


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
        String sql = "INSERT INTO bigCSVCutter (id, countLine, countFile, date, folder, created) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, cutter.getId());
            statement.setInt(2, cutter.getCountLine());
            statement.setInt(3, cutter.getCountFile());
            statement.setString(4, cutter.getDate());
            statement.setString(5, cutter.getFolder());
            statement.setBoolean(6, cutter.getCreated());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи BigCSVCutter.", e);
        }
        return false;
    }

    public BigCSVCutter getBigCSVCutterById(String id) {
        String sql = "SELECT * FROM bigCSVCutter";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, id);
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

    public boolean updateBigCSVCutter(BigCSVCutter cutter) {
        String sql = "UPDATE bigCSVCutter SET countLine = ?, countFile = ?, date = ?, folder = ?, created = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, cutter.getCountLine());
            statement.setInt(2, cutter.getCountFile());
            statement.setString(3, cutter.getDate());
            statement.setString(4, cutter.getFolder());
            statement.setBoolean(5, cutter.getCreated());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи BigCSVCutter.", e);
        }
        return false;
    }


    public boolean deleteBigCSVCutter(String id) {
        String sql = "DELETE FROM bigCSVCutter WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи BigCSVCutter.", e);
        }
        return false;
    }

    //операции с классом CSVReader

    public boolean createCSVReader(CSVReader reader) {
        String sql = "INSERT INTO CSVReader (pathName, csvFile, words) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, reader.getPathName());
            statement.setString(2, reader.getCsvFile().getAbsolutePath());
            statement.setObject(3, reader.getWords());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи CSVReader.", e);
        }
        return false;
    }


    public CSVReader getCSVReaderById(int id) {
        String sql = "SELECT * FROM CSVReader WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new CSVReader(
                            resultSet.getString("pathName"),
                            new File(resultSet.getString("csvFile")),
                            resultSet.getInt("words")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи CSVReader.", e);
        }
        return null;
    }


    public boolean updateCSVReader(int id, CSVReader reader) {
        String sql = "UPDATE CSVReader SET pathName = ?, csvFile = ?, words = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, reader.getPathName());
            statement.setString(2, reader.getCsvFile().getAbsolutePath());
            statement.setObject(3, reader.getWords());
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи CSVReader.", e);
        }
        return false;
    }

    public boolean deleteCSVReader(int id) {
        String sql = "DELETE FROM CSVReader WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи CSVReader.", e);
        }
        return false;
    }


    //crud с классом debtorlist

    public boolean createDebtorList(DebtorList debtor) {
        String sql = "INSERT INTO DebtorList (ownerName, payerName, accruedMoney, returnedMoney, createDateOfPayment, uploadDateOfPayment, kadastr) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, debtor.getOwnerName());
            statement.setString(2, debtor.getPayerName());
            statement.setLong(3, debtor.getAccruedMoney());
            statement.setLong(4, debtor.getReturnedMoney());
            statement.setTimestamp(5, Timestamp.valueOf(debtor.getCreateDateOfPayment()));
            statement.setTimestamp(6, new Timestamp(debtor.getUploadDateOfPayment().getTime()));
            statement.setBoolean(7, debtor.getKadastr());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи DebtorList.", e);
        }
        return false;
    }

    // Read
    public DebtorList getDebtorListById(int id) {
        String sql = "SELECT * FROM DebtorList WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DebtorList(
                            resultSet.getString("ownerName"),
                            resultSet.getString("payerName"),
                            resultSet.getLong("accruedMoney"),
                            resultSet.getLong("returnedMoney"),
                            Date.from(resultSet.getTimestamp("createDateOfPayment").toInstant()),
                            new Date(resultSet.getTimestamp("uploadDateOfPayment").getTime()),
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
    public boolean updateDebtorList(int id, DebtorList debtor) {
        String sql = "UPDATE DebtorList SET ownerName = ?, payerName = ?, accruedMoney = ?, returnedMoney = ?, createDateOfPayment = ?, uploadDateOfPayment = ?, kadastr = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, debtor.getOwnerName());
            statement.setString(2, debtor.getPayerName());
            statement.setLong(3, debtor.getAccruedMoney());
            statement.setLong(4, debtor.getReturnedMoney());
            statement.setTimestamp(5, Timestamp.valueOf(debtor.getCreateDateOfPayment()));
            statement.setTimestamp(6, new Timestamp(debtor.getUploadDateOfPayment().getTime()));
            statement.setBoolean(7, debtor.getKadastr());
            statement.setInt(8, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи DebtorList.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteDebtorList(int id) {
        String sql = "DELETE FROM DebtorList WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи DebtorList.", e);
        }
        return false;
    }

    //crud historycontent

    public boolean createHistoryContent(HistoryContent history) {
        String sql = "INSERT INTO HistoryContent (id, className, createdDate, actor, methodName, object, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, history.getId());
            statement.setString(2, history.getClassName());
            statement.setTimestamp(3, Timestamp.valueOf(history.getCreatedDate()));
            statement.setString(4, history.getActor());
            statement.setString(5, history.getMethodName());
            statement.setString(6, history.getObject());
            statement.setString(7, history.getStatus().name());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи HistoryContent.", e);
        }
        return false;
    }

    // Read
    public HistoryContent getHistoryContentById(String id) {
        String sql = "SELECT * FROM HistoryContent WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    HistoryContent history = new HistoryContent();
                    history.setId(resultSet.getString("id"));
                    history.setClassName(resultSet.getString("className"));
                    history.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    history.setActor(resultSet.getString("actor"));
                    history.setMethodName(resultSet.getString("methodName"));

                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("data", resultSet.getString("object"));
                    history.setObject(objectMap);

                    history.setStatus(HistoryContent.Status.valueOf(resultSet.getString("status")));
                    return history;
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи HistoryContent.", e);
        }
        return null;
    }

    // Update
    public boolean updateHistoryContent(String id, HistoryContent history) {
        String sql = "UPDATE HistoryContent SET className = ?, createdDate = ?, actor = ?, methodName = ?, object = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, history.getClassName());
            statement.setTimestamp(2, Timestamp.valueOf(history.getCreatedDate()));
            statement.setString(3, history.getActor());
            statement.setString(4, history.getMethodName());
            statement.setString(5, history.getObject());
            statement.setString(6, history.getStatus().name());
            statement.setString(7, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи HistoryContent.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteHistoryContent(String id) {
        String sql = "DELETE FROM HistoryContent WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, id);
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
            statement.setInt(4, email.getId());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи IncomingEmails.", e);
        }
        return false;
    }

    // Read
    public IncomingEmails getIncomingEmailById(int id) {
        String sql = "SELECT * FROM incoming_emails WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
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
    public boolean updateIncomingEmail(int id, IncomingEmails email) {
        String sql = "UPDATE incoming_emails SET email_address = ?, email_sender = ?, email_receiver = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, email.getEmailAddress());
            statement.setString(2, email.getEmailSender());
            statement.setString(3, email.getEmailReceiver());
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи incoming_emails.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteIncomingEmail(int id) {
        String sql = "DELETE FROM incoming_emails WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
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
            statement.setString(1, parser.getId());
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
    public SeleniumParser getSeleniumParserById(int id) {
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
    public boolean updateSeleniumParser(int id, SeleniumParser parser) {
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
    public boolean deleteSeleniumParser(int id) {
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
        String sql = "INSERT INTO Window_App (kadastrNumber, personalAddress, personalAccount, personalNumber, square, emailCounter, personalID, createDate, uploadDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи WindowApp.", e);
        }
        return false;
    }

    // Read
    public WindowApp getWindowAppById(int id) {
        String sql = "SELECT * FROM Window_App WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
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
    public boolean updateWindowApp(int id, WindowApp windowApp) {
        String sql = "UPDATE Window_App SET kadastrNumber = ?, personalAddress = ?, personalAccount = ?, personalNumber = ?, square = ?, emailCounter = ?, personalID = ?, createDate = ?, uploadDate = ? WHERE id = ?";
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
            statement.setInt(10, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи WindowApp.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteWindowApp(int id) {
        String sql = "DELETE FROM Window_App WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
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
        String sql = "INSERT INTO WithKadastrList (source, destination, createdTime) VALUES (?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, withKadastrList.getSource());
            statement.setString(2, withKadastrList.getDestination());
            statement.setTimestamp(3, new Timestamp(withKadastrList.getCreatedTime().getTime()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при добавлении записи WithKadastrList.", e);
        }
        return false;
    }

    // Read
    public WithKadastrList getWithKadastrListById(int id) {
        String sql = "SELECT * FROM WithKadastrList WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new WithKadastrList(
                            resultSet.getString("source"),
                            resultSet.getString("destination"),
                            new Date(resultSet.getTimestamp("createdTime").getTime())
                    );
                }
            }
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при получении записи WithKadastrList.", e);
        }
        return null;
    }

    // Update
    public boolean updateWithKadastrList(int id, WithKadastrList withKadastrList) {
        String sql = "UPDATE WithKadastrList SET source = ?, destination = ?, createdTime = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, withKadastrList.getSource());
            statement.setString(2, withKadastrList.getDestination());
            statement.setTimestamp(3, new Timestamp(withKadastrList.getCreatedTime().getTime()));
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при обновлении записи WithKadastrList.", e);
        }
        return false;
    }

    // Delete
    public boolean deleteWithKadastrList(int id) {
        String sql = "DELETE FROM WithKadastrList WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | IOException e) {
            logger.error("Ошибка при удалении записи WithKadastrList.", e);
        }
        return false;
    }


}
