package ru.sfedu.dubina.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(sessionFactory);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
