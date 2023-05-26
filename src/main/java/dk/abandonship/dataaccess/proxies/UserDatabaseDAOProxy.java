package dk.abandonship.dataaccess.proxies;

import com.google.gson.Gson;
import dk.abandonship.dataaccess.DatabaseLogDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;

public class UserDatabaseDAOProxy implements IUserDAO {

    private final IDatabaseLogDAO databaseLogDAO;

    private final IUserDAO userDAO;

    public UserDatabaseDAOProxy() {
        databaseLogDAO = new DatabaseLogDAO();

        userDAO = new UserDatabaseDAO(new RoleDatabaseDAOProxy());
    }

    @Override
    public User getUser(String email) throws SQLException {
        var result = userDAO.getUser(email);

        databaseLogDAO.insertToLog("getUser", new Gson().toJson(email), new Gson().toJson(result));

        return result;
    }

    @Override
    public User getUser(int id) throws SQLException {
        var result = userDAO.getUser(id);

        databaseLogDAO.insertToLog("getUser", new Gson().toJson(id), new Gson().toJson(result));

        return result;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        var result = userDAO.getAllUsers();

        databaseLogDAO.insertToLog("getAllUsers", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public List<User> getAllTechnicians() throws Exception {
        var result = userDAO.getAllTechnicians();

        databaseLogDAO.insertToLog("getAllTechnicians", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public User createUser(User user) throws SQLException {
        var result = userDAO.createUser(user);

        databaseLogDAO.insertToLog("createUser",
                new Gson().toJson(user),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        var result = userDAO.deleteUser(user);

        databaseLogDAO.insertToLog("deleteUser", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public boolean editUser(User user, User newData) throws SQLException {
        var result = userDAO.editUser(user, newData);

        databaseLogDAO.insertToLog("editUser", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public void addRole(User user, Role role) throws SQLException {

    }

    @Override
    public void removeRole(User user, Role role) throws SQLException {

    }
}
