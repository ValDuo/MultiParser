package ru.sfedu.dubina.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataProviderHibernateTest {


    private HibernateDataProvider hibernateDataProvider;
    private Session session;

    @BeforeEach
    public void setUp() {
        session = mock(Session.class);
        hibernateDataProvider = new HibernateDataProvider(session);
    }

    @Test
    public void testGetDatabaseSize() {
        NativeQuery<Long> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(1024L);

        Long result = hibernateDataProvider.getDatabaseSize();
        assertEquals(1024L, result);
    }

    @Test
    public void testGetTables() {
        NativeQuery<String> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList("users", "orders", "products"));

        List<String> result = hibernateDataProvider.getTables();
        assertEquals(3, result.size());
        assertEquals("users", result.get(0));
        assertEquals("orders", result.get(1));
        assertEquals("products", result.get(2));
    }

    @Test
    public void testGetUsers() {
        NativeQuery<String> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList("admin", "user1", "user2"));

        List<String> result = hibernateDataProvider.getUsers();
        assertEquals(3, result.size());
        assertEquals("admin", result.get(0));
        assertEquals("user1", result.get(1));
        assertEquals("user2", result.get(2));
    }

    @Test
    public void testGetDataTypes() {
        NativeQuery<String> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList("integer", "text", "boolean"));

        List<String> result = hibernateDataProvider.getDataTypes();
        assertEquals(3, result.size());
        assertEquals("integer", result.get(0));
        assertEquals("text", result.get(1));
        assertEquals("boolean", result.get(2));
    }







}
