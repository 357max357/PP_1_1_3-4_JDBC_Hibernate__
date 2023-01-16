package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDaoJDBC = new UserDaoJDBCImpl();

        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("kek", "kekich", (byte) 28);
        userDaoJDBC.saveUser("lol", "lolich", (byte) 25);
        userDaoJDBC.saveUser("pop", "popich", (byte) 25);
        userDaoJDBC.saveUser("lop", "lopich", (byte) 25);

        //userDaoJDBC.removeUserById(1);
        userDaoJDBC.getAllUsers();

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
