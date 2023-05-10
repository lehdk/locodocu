package dk.abandonship.businesslogic;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;

public class UserManager {
    private IUserDAO userDAO;
    private IRoleDAO roleDAO;
    private UserDatabaseDAO userDatabaseDAO;
    public UserManager() {
        roleDAO = new RoleDatabaseDAO();
        userDAO = new UserDatabaseDAO(roleDAO);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<User> getAllTechnicians() throws Exception{
        return userDAO.getAllTechnicians();
    }

    public void createUser(User user) throws SQLException {
        userDAO.createUser(user);
    }

    public boolean deleteUser(User user) throws SQLException {
        return userDAO.deleteUser(user);
    }

}
