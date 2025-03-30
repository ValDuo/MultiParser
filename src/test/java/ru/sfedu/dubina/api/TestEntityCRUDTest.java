package ru.sfedu.dubina.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.sfedu.dubina.lab2.*;

import java.util.Date;

public class TestEntityCRUDTest {
    private static SessionFactory sessionFactory;
    private Session session;


    @BeforeAll
    public static void setup() {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(TestEntity.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
    }
    @AfterEach
    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testCreate() {
        session.beginTransaction();

        AuditInfo audit = new AuditInfo();
        audit.setCreatedBy("admin");
        audit.setCreatedDate(new Date());

        TestEntity entity = new TestEntity();
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        entity.setDateCreated(new Date());
        entity.setCheck(true);
        entity.setAuditInfo(audit);

        Long id = (Long) session.save(entity);
        session.getTransaction().commit();

        Assertions.assertNotNull(id);
    }

    @Test
    public void testRead() {
        testCreate();
        session.beginTransaction();
        TestEntity entity = session.get(TestEntity.class, 1L);
        session.getTransaction().commit();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("Test Entity", entity.getName());
    }

    @Test
    public void testUpdate() {
        testCreate();

        session.beginTransaction();
        TestEntity entity = session.get(TestEntity.class, 1L);
        entity.setName("Updated Name");

        AuditInfo audit = entity.getAuditInfo();
        audit.setModifiedBy("user");
        audit.setModifiedDate(new Date());

        session.update(entity);
        session.getTransaction().commit();

        session.beginTransaction();
        TestEntity updated = session.get(TestEntity.class, 1L);
        session.getTransaction().commit();

        Assertions.assertEquals("Updated Name", updated.getName());
        Assertions.assertEquals("user", updated.getAuditInfo().getModifiedBy());
    }

    @Test
    public void testDelete() {
        testCreate();

        session.beginTransaction();
        TestEntity entity = session.get(TestEntity.class, 1L);
        session.delete(entity);
        session.getTransaction().commit();

        session.beginTransaction();
        TestEntity deleted = session.get(TestEntity.class, 1L);
        session.getTransaction().commit();

        Assertions.assertNull(deleted);
    }
}