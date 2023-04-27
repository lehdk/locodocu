package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.entities.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDatabaseDAO implements IRoleDAO {

    @Override
    public List<Role> getAllRoles() throws SQLException {
        var roles = new ArrayList<Role>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Roles];";

            PreparedStatement statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                roles.add(new Role(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name")
                ));
            }
        }

        return roles;
    }
}
