package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IRoleDAO {

    /**
     * Gets all roles from the chosen data source.
     * @return All the roles. If no roles, then return empty array.
     */
    List<Role> getAllRoles() throws SQLException;

    Set<Role> getAllRolesForUser(User user) throws SQLException;
}
