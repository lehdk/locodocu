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

    List<User> getAllUsers() throws Exception;
    List<User> getAllTechnicians() throws Exception;
}
