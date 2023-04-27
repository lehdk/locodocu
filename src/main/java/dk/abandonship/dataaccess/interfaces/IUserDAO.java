package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.User;

import java.sql.SQLException;

public interface IUserDAO {

    /**
     * Returns a user with all of its roles
     * @param email The email of the user you want
     * @return The user. If no user then return null
     * @throws SQLException
     */
    User getUser(String email) throws SQLException;
}
