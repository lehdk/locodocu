package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.dataaccess.proxies.RoleDatabaseDAOProxy;
import dk.abandonship.entities.Role;
import dk.abandonship.dataaccess.proxies.UserDatabaseDAOProxy;
import dk.abandonship.entities.User;
import dk.abandonship.utils.PasswordHasher;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UserManager {
    private final IUserDAO userDAO;
    private final IRoleDAO roleDAO;

    public UserManager() {
        roleDAO = new RoleDatabaseDAOProxy();
        userDAO = new UserDatabaseDAOProxy();
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<User> getAllTechnicians() throws Exception{
        return userDAO.getAllTechnicians();
    }

    public void createUser(User user) throws SQLException {
        user.setPassword(PasswordHasher.getPasswordHash(user.getPassword()));
        userDAO.createUser(user);
    }

    public boolean deleteUser(User user) throws SQLException {
        return userDAO.deleteUser(user);
    }

    public boolean editUser(User user, User newData) throws SQLException {
        newData.setPassword(PasswordHasher.getPasswordHash(newData.getPassword()));
        return userDAO.editUser(user, newData);
    }

    public void addRole(User user, Role role) throws SQLException {
        userDAO.addRole(user, role);
    }

    public void removeRole(User user, Role role) throws SQLException {
        userDAO.removeRole(user, role);
    }

    public List<Role> getAllRoles() throws SQLException {
        return roleDAO.getAllRoles();
    }

    public Set<Role> getAllRolesForUser(User user) throws SQLException {
        return roleDAO.getAllRolesForUser(user);
    }
}
