package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static final String SHOW_SQL = "true";
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "thread";
    private static final String HBM2DDL_AUTO = "";
    private static Connection connection;
    private static SessionFactory factory;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Class.forName(DRIVER);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static SessionFactory getSession() {
        if (factory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties prop = new Properties();

                prop.put(Environment.DRIVER, DRIVER);
                prop.put(Environment.URL, URL);
                prop.put(Environment.USER, USERNAME);
                prop.put(Environment.PASS, PASSWORD);
                prop.put(Environment.DIALECT, DIALECT);
                prop.put(Environment.SHOW_SQL, SHOW_SQL);
                prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, CURRENT_SESSION_CONTEXT_CLASS);
                prop.put(Environment.HBM2DDL_AUTO, HBM2DDL_AUTO);

                configuration.setProperties(prop);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                factory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;
    }
}
