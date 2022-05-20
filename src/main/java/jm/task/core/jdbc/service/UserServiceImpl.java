package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDaoJDBCImpl udJDBC = new UserDaoJDBCImpl();


    public void createUsersTable() {
        udJDBC.createUsersTable();
    }

    public void dropUsersTable() {
        udJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        udJDBC.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        udJDBC.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return udJDBC.getAllUsers();
    }

    public void cleanUsersTable() {
        udJDBC.cleanUsersTable();
    }
}
