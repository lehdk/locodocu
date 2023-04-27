package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabaseDAO implements IUserDAO {

    private IRoleDAO roleDAO;

    public UserDatabaseDAO(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public User getUser(String email) throws SQLException {
        User resultUser = null;

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Email]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            var resultSet = statement.executeQuery();

            if(resultSet.next()) {
                resultUser = new User(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Password"),
                        resultSet.getTimestamp("DisabledAt")
                );
            }
        }

        if(resultUser == null) return null;

        var roles = roleDAO.getAllRolesForUser(resultUser);

        resultUser.setAllRoles(roles);

        return resultUser;
    }
}
