package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("kek", "kekich", (byte) 28);
        userDao.saveUser("lol", "lolich", (byte) 25);
        userDao.saveUser("pop", "popich", (byte) 25);
        userDao.saveUser("lop", "lopich", (byte) 25);

        userDao.removeUserById(1);
        userDao.getAllUsers();

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
