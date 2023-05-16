package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.dataaccess.proxies.UserDatabaseDAOProxy;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.PasswordHasher;

import java.sql.SQLException;

public class LoginManager {

    private final IUserDAO userDAO;
    public LoginManager() {
        userDAO = new UserDatabaseDAOProxy();
    }

    /**
     * Logs in a user if username and password matches.
     * @param email Email of the user
     * @param rawPassword Raw password of the user
     * @return True if success. False otherwise
     */
    public boolean login(String email, String rawPassword) throws SQLException {

        var userToLogin = userDAO.getUser(email);

        if(userToLogin == null) return false;

        LoggedInUserState.getInstance().setLoggedInUser(userToLogin);

        return PasswordHasher.comparePasswordToHash(rawPassword, userToLogin.getPassword());
    }
}
