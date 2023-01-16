package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlQueryCreateUsersTable = "CREATE TABLE IF NOT EXISTS new_schema.`users_table` (\n"
                + " `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "`name` VARCHAR(20) NOT NULL,\n"
                + "`lastName` VARCHAR(20) NULL,\n"
                + "`age` INT NOT NULL,\n"
                + "PRIMARY KEY (`id`));";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQueryCreateUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sqlQueryDropUsersTable = "DROP TABLE IF EXISTS users_table";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQueryDropUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuerySaveUser = "INSERT INTO users_table (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlQuerySaveUser)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String sqlQueryRemoveUserById = "DELETE FROM users_table WHERE id = " + id;

        try (PreparedStatement statement = connection.prepareStatement(sqlQueryRemoveUserById)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sqlQueryGetAllUsers = "SELECT id, name, lastName, age FROM users_table";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQueryGetAllUsers)) {
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (User user : userList) {
            System.out.println(user.toString());
        }

        return userList;
    }

    public void cleanUsersTable() {
        String sqlQueryCleanUsersTable = "DELETE FROM users_table";

        try (PreparedStatement statement = connection.prepareStatement(sqlQueryCleanUsersTable)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
