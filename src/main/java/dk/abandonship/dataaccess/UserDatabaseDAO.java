package dk.abandonship.dataaccess;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseDAO implements IUserDAO {

    private IRoleDAO roleDAO;

    public UserDatabaseDAO(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public User getUser(String email) throws SQLException {
        User resultUser = null;

        try (var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Email]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
        if (resultUser == null) return null;

        var roles = roleDAO.getAllRolesForUser(resultUser);

        resultUser.setAllRoles(roles);

        return resultUser;
    }

    @Override
    public User getUser(int id) throws SQLException {
        User resultUser = null;

        try (var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Id]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
        if (resultUser == null) return null;

        var roles = roleDAO.getAllRolesForUser(resultUser);

        resultUser.setAllRoles(roles);

        return resultUser;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Users]";

            PreparedStatement stmt = connection.prepareStatement(sql);

            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                User resultUser = new User(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Password"),
                        resultSet.getTimestamp("DisabledAt")
                );

                if (resultUser != null) {

                    var roles = roleDAO.getAllRolesForUser(resultUser);

                    resultUser.setAllRoles(roles);

                    users.add(resultUser);
                }
            }
        }
        return users;
    }

    @Override
    public List<User> getAllTechnicians() throws Exception {
        List<User> technicians = new ArrayList<>();

        try (var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM Users WHERE Id IN (SELECT UserId FROM UserRoleRelation WHERE RoleId = 3)";

            PreparedStatement statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User resultUser = new User(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Password"),
                        resultSet.getTimestamp("DisabledAt")
                );

                var roles = roleDAO.getAllRolesForUser(resultUser);

                resultUser.setAllRoles(roles);
                technicians.add(resultUser);

            }
        }
        return technicians;
    }

    public User createUser(User user) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [Users] ([Name], [Email], [Phone], [Password], [DisabledAt]) VALUES (?,?,?,?,?)";

            var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getPassword());
            statement.setTimestamp(5, new Timestamp(1));

            var rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt(1);
                user.setId(userId);
                return user;
            }
        }
        return null;
    }

    public boolean deleteUser(User user) throws SQLException {
        try( var connection = DBConnector.getInstance().getConnection()) {
            String sql = "DELETE FROM [Users] WHERE [Id]=?";

            var statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());


            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }

    public boolean editUser(User user, User newData) throws SQLException {

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "UPDATE [Users] SET [Name]=?,[Email]=?,[Phone]=?,[Password]=?,[DisabledAt]=? WHERE [Id]=?;";

            var statement = connection.prepareStatement(sql);
            statement.setString(1, newData.getName());
            statement.setString(2, newData.getEmail());
            statement.setString(3, newData.getPhone());
            statement.setString(4, newData.getPassword());
            statement.setTimestamp(5, null);
            statement.setInt(6, user.getId());

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }

    public void addRole(User user, Role role) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql = "INSERT INTO [UserRoleRelation] ([UserId], [RoleId]) VALUES (?, ?)";

            var statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());

            statement.executeUpdate();
        }
    }

    public void removeRole(User user, Role role) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql = "DELETE FROM [UserRoleRelation] ([UserId], [RoleId]) VALUES (?, ?)";

            var statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());

            statement.executeUpdate();
        }
    }

}