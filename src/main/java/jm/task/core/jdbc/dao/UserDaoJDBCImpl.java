package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String GET_ALL_USR = "select * from user";
    private static final String CREATE_TAB = "CREATE TABLE IF NOT EXISTS User (" +
            "id BIGINT unsigned not null auto_increment  PRIMARY KEY," +
            "name varchar(25) not null," +
            "lastName varchar(25) not null," +
            "age TINYINT not null )";

    private static final String DEL_TAB = "drop table if exists User";
    private static final String INSERT_USR = "INSERT INTO user (name, lastName, age) VALUES (?,?,?)";
    private static final String REMOVE_USR = "delete from user where id = ?";
    private static final String CLEAR_TAB = "TRUNCATE TABLE user";
    private Connection conn;
    private Statement statement;
    private PreparedStatement pstatment;

    public void createUsersTable() {

        conn = Util.letsConnect();
        statement = Util.letsStatm(conn);
        try {
            statement.execute(CREATE_TAB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.clouse(statement, conn);
        }

    }

    public void dropUsersTable() {
        conn = Util.letsConnect();
        statement = Util.letsStatm(conn);

        try {
            statement.execute(DEL_TAB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.clouse(statement, conn);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            conn = Util.letsConnect();
            pstatment = conn.prepareStatement(INSERT_USR);
            pstatment.setString(1, name);
            pstatment.setString(2, lastName);
            pstatment.setByte(3, age);
            pstatment.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.clouse(pstatment, conn);
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.letsConnect();
            pstatment = conn.prepareStatement(REMOVE_USR);
            pstatment.setLong(1, id);
            pstatment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.clouse(pstatment, conn);
        }
    }

    public List<User> getAllUsers() {

        List<User> myUsers = new ArrayList<>();
        User tmpUsr;
        conn = Util.letsConnect();
        statement = Util.letsStatm(conn);

        ResultSet rs= null;
        try {
            rs = statement.executeQuery(GET_ALL_USR);

            while (rs.next()){
                tmpUsr =new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                tmpUsr.setId(rs.getLong("id"));
                myUsers.add(tmpUsr);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                Util.clouse(statement, conn);
            }
        return myUsers;
    }

    public void cleanUsersTable() {
        conn = Util.letsConnect();
        statement = Util.letsStatm(conn);

        try {
            statement.execute(CLEAR_TAB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.clouse(statement, conn);
        }
    }

}
