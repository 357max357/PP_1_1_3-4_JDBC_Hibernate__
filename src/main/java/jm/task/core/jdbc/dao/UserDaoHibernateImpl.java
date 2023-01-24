package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private final SessionFactory factory = Util.getSession();
    private static Session session;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlQueryCreateUsersTable = "CREATE TABLE IF NOT EXISTS new_schema.`User` (\n"
                + " `id` INT NOT NULL AUTO_INCREMENT,\n"
                + "`name` VARCHAR(20) NOT NULL,\n"
                + "`lastName` VARCHAR(20) NULL,\n"
                + "`age` INT NOT NULL,\n"
                + "PRIMARY KEY (`id`));";

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query = session.createSQLQuery(sqlQueryCreateUsersTable).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlQueryDropUsersTable = "DROP TABLE IF EXISTS User";

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query = session.createSQLQuery(sqlQueryDropUsersTable).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQueryGetAllUsers = "from User";

        try (Session session = factory.openSession()) {
            for (User user : session.createQuery(sqlQueryGetAllUsers, User.class).list()) {
                System.out.println(user.toString());
            }

            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String sqlQueryCleanUsersTable = "DELETE FROM User";

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query = session.createSQLQuery(sqlQueryCleanUsersTable).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
