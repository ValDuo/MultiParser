package ru.sfedu.dubina.api;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import java.util.List;
public class HibernateDataProvider {

    private final Session session;

    public HibernateDataProvider(Session session) {
        this.session = session;
    }

    public Long getDatabaseSize() {
        String sql = "SELECT pg_database_size(current_database())";
        NativeQuery<Long> query = session.createNativeQuery(sql);
        return query.uniqueResult();
    }

    public List<String> getTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
        NativeQuery<String> query = session.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<String> getUsers() {
        String sql = "SELECT usename FROM pg_catalog.pg_user";
        NativeQuery<String> query = session.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<String> getDataTypes() {
        String sql = "SELECT typname FROM pg_catalog.pg_type";
        NativeQuery<String> query = session.createNativeQuery(sql);
        return query.getResultList();
    }
}
