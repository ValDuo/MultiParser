package org.example.api;

import org.example.models.PersForApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DataProviderPostgresTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private DataProviderPostgres dataProvider;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        dataProvider = new DataProviderPostgres();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        dataProvider.initDataSource();
    }

    @Test
    void initDataSource() {
        assertNotNull(dataProvider);
    }

    @Test
    void saveRecord() throws SQLException {
        PersForApi person = new PersForApi(1L, "John Doe", "john.doe@example.com");
        doNothing().when(preparedStatement).executeUpdate();
        dataProvider.saveRecord(person);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void deleteRecord() throws SQLException {
        Long id = 1L;
        when(preparedStatement.executeUpdate()).thenReturn(1);
        dataProvider.deleteRecord(id);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getRecordById() throws SQLException {
        Long id = 1L;
        PersForApi expectedPerson = new PersForApi(id, "John Doe", "john.doe@example.com");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        PersForApi result = dataProvider.getRecordById(id);
        assertEquals(expectedPerson.getId(), result.getId());
        assertEquals(expectedPerson.getName(), result.getName());
        assertEquals(expectedPerson.getEmail(), result.getEmail());
    }

    @Test
    void closeConnection() throws SQLException {
        dataProvider.closeConnection();
        verify(connection, times(1)).close();
    }
}
