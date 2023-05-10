package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {

    /**
     * Returns a user with all of its roles
     * @param email The email of the user you want
     * @return The user. If no user then return null
     * @throws SQLException
     */
    User getUser(String email) throws SQLException;

    /**
     * Returns a user with all of its roles
     * @param id The id of the user you want
     * @return The user. If no user then return null
     * @throws SQLException
     */
    public User getUser(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;
    List<User> getAllTechnicians() throws Exception;
    public void createUser(User user) throws SQLException;
    public boolean deleteUser(User user) throws SQLException;
}
