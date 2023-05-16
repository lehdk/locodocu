package dk.abandonship.dataaccess.proxies;

import com.google.gson.Gson;
import dk.abandonship.dataaccess.DatabaseLogDAO;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RoleDatabaseDAOProxy implements IRoleDAO {

    private final IDatabaseLogDAO databaseLogDAO;

    private final RoleDatabaseDAO roleDatabaseDAO;

    public RoleDatabaseDAOProxy() {
        databaseLogDAO = new DatabaseLogDAO();

        roleDatabaseDAO = new RoleDatabaseDAO();
    }

    @Override
    public List<Role> getAllRoles() throws SQLException {
        var result = roleDatabaseDAO.getAllRoles();

        databaseLogDAO.insertToLog("getAllRoles", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public Set<Role> getAllRolesForUser(User user) throws SQLException {
        var result = roleDatabaseDAO.getAllRolesForUser(user);

        databaseLogDAO.insertToLog("getAllRolesForUser", new Gson().toJson(user), new Gson().toJson(result));

        return result;
    }
}
