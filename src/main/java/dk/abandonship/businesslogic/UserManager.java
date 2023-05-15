package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.dataaccess.proxies.UserDatabaseDAOProxy;
import dk.abandonship.entities.User;

import java.util.List;

public class UserManager {
    private final IUserDAO userDAO;

    public UserManager() {
        userDAO = new UserDatabaseDAOProxy();
    }

    public List<User> getAllUsers() throws Exception{
        return userDAO.getAllUsers();
    }

    public List<User> getAllTechnicians() throws Exception{
        return userDAO.getAllTechnicians();
    }
}
