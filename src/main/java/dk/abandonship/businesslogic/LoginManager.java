package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.PasswordHasher;

import java.sql.SQLException;

public class LoginManager {

    private IRoleDAO roleDAO;
    private IUserDAO userDAO;
    public LoginManager() {

        roleDAO = new RoleDatabaseDAO();
        userDAO = new UserDatabaseDAO(roleDAO);
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
