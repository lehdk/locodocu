package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.User;

import java.util.List;

public class UserManager {
    private IUserDAO userDAO;
    private IRoleDAO roleDAO;
    public UserManager() {
        roleDAO = new RoleDatabaseDAO();
        userDAO = new UserDatabaseDAO(roleDAO);
    }

    public List<User> getAllUsers() throws Exception{
        return null;
    }

    public List<User> getAllTechnicians() throws Exception{
        return userDAO.getAllTechnicians();
    }

}
