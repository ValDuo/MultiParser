package ru.sfedu.dubina.api;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                String configFilePath = System.getProperty("hibernate.config.path", "src/main/resources/hibernate.cfg.xml");

                File configFile = new File(configFilePath);
                if (!configFile.exists()) {
                    throw new RuntimeException("Конфигурационный файл не найден: " + configFilePath);
                }
                Configuration configuration = new Configuration();
                configuration.configure(configFile);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                throw new ExceptionInInitializerError("Ошибка инициализации Hibernate: " + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
