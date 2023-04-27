package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<Role> getAllRolesForUser(User user) throws SQLException {
        var roles = new HashSet<Role>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql =
                    "SELECT DISTINCT [RoleId], [Name] FROM [UserRoleRelation] " +
                    "JOIN [Roles] ON [RoleId] = [Roles].[Id] " +
                    "WHERE [UserId]=?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                roles.add(new Role(
                        resultSet.getInt("RoleId"),
                        resultSet.getString("Name")
                ));
            }
        }

        return roles;
    }
}
