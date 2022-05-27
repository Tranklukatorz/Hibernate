package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User " +
                    "(id BIGINT unsigned not null auto_increment  PRIMARY KEY," +
                    "name varchar(25) not null," +
                    "lastName varchar(25) not null," +
                    "age TINYINT not null )").executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка сесси",e);
        }

    }


    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists test.user").executeUpdate();
            transaction.commit();
            session.close();
            
        } catch (HibernateException er) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка сессии", er);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();

            Transaction transact = session.beginTransaction();
            Query query= session.createSQLQuery("INSERT INTO `test`.`user` (`name`, `lastName`, `age`) VALUES (:myname, :mylastname, :myage)");
            query.setParameter("myname",name);
            query.setParameter("mylastname", lastName);
            query.setParameter("myage", age);
            query.executeUpdate();
            transact.commit();

            session.close();
            } catch (HibernateException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка сессии", e);
            }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM `test`.`user` WHERE (`id` = :myid)").setParameter("myid", id).executeUpdate();
            transaction.commit();

            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка сессии", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usrList;
        String sql = "From " + User.class.getSimpleName();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            usrList = session.createQuery(sql).list();
            transaction.commit();

            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка сессии", e);
        }
        return usrList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE test.user").executeUpdate();
            transaction.commit();

            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка сессии", e);

        }
    }
}
