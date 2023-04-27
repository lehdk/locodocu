package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.Role;

import java.sql.SQLException;
import java.util.List;

public interface IRoleDAO {

    /**
     * Gets all roles from the chosen data source.
     * @return All the roles. If no roles, then return empty array.
     */
    List<Role> getAllRoles() throws SQLException;
}
