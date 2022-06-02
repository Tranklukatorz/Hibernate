package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Util {
    // Hibernate
    private static  Util singletonUtil;
    private static SessionFactory sessionFactory;

    private Util(){
    }

    public static Util getSingletonUtil(){
        if (singletonUtil != null){
            singletonUtil = new Util();
        }
        return singletonUtil;
    }

    public SessionFactory getSessionFactory(){

        if (sessionFactory == null) {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
            prop.setProperty("hibernate.connection.username", "adm");
            prop.setProperty("hibernate.connection.password", "1234");
            prop.setProperty("show_sql", "true");

            Configuration configuration = new Configuration().addProperties(prop);
            configuration.addAnnotatedClass(User.class);

            try {
                sessionFactory = configuration.buildSessionFactory();
            } catch (HibernateException e) {

                throw new RuntimeException("Ошибка при создании фабрики", e);
            }
        }
        return sessionFactory;
    }
}
