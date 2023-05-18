package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectDatabaseDAO implements IProjectDAO {
    private final IUserDAO userDAO;

    public ProjectDatabaseDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {

        var projects = new ArrayList<Project>();

        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql = "SELECT " +
                    "[Project].[Id] AS [ProjectId]," +
                    "[Project].[Name] AS [ProjectName]," +
                    "[Project].[Address] AS [ProjectAddress]," +
                    "[Project].[PostalCode] AS [ProjectPostalCode]," +
                    "[CreatedAt] AS [ProjectCreatedAt]," +
                    "[CustomerId]," +
                    "[Customer].[Name] AS [CustomerName]," +
                    "[Customer].[Email] AS [CustomerEmail]," +
                    "[Customer].[Phone] AS [CustomerPhone]," +
                    "[Customer].[Address] AS [CustomerAddress]," +
                    "[Customer].[PostalCode] AS [CustomerPostalCode]" +
                    "FROM [Project]" +
                    "JOIN [Customer] ON [CustomerId] = [Customer].[Id];";

            PreparedStatement statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("CustomerName"),
                        resultSet.getString("CustomerEmail"),
                        resultSet.getString("CustomerPhone"),
                        resultSet.getString("CustomerAddress"),
                        resultSet.getString("CustomerPostalCode")
                );

                Project project = new Project(
                        resultSet.getInt("ProjectId"),
                        resultSet.getString("ProjectName"),
                        resultSet.getString("ProjectAddress"),
                        resultSet.getString("ProjectPostalCode"),
                        resultSet.getTimestamp("ProjectCreatedAt"),
                        customer
                );

                projects.add(project);
            }
        }

        // Load assigned technicians
        for(var p : projects) {
            try(var connection = DBConnector.getInstance().getConnection()) {
                String sql ="SELECT [UserId] FROM [ProjectUserRelation] WHERE [ProjectId]=?;";

                var statement = connection.prepareStatement(sql);
                statement.setInt(1, p.getId());

                statement.executeQuery();

                var resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    p.assignTechnician(userDAO.getUser(resultSet.getInt(1)));
                }
            }
        }

        return projects;
    }

    public Project getProjectById(int id) throws SQLException {

        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql = "SELECT " +
                    "[Project].[Id] AS [ProjectId]," +
                    "[Project].[Name] AS [ProjectName]," +
                    "[Project].[Address] AS [ProjectAddress]," +
                    "[Project].[PostalCode] AS [ProjectPostalCode]," +
                    "[CreatedAt] AS [ProjectCreatedAt]," +
                    "[CustomerId]," +
                    "[Customer].[Name] AS [CustomerName]," +
                    "[Customer].[Email] AS [CustomerEmail]," +
                    "[Customer].[Phone] AS [CustomerPhone]," +
                    "[Customer].[Address] AS [CustomerAddress]," +
                    "[Customer].[PostalCode] AS [CustomerPostalCode]" +
                    "FROM [Project]" +
                    "JOIN [Customer] ON [CustomerId] = [Customer].[Id]" +
                    "WHERE [Project].[Id] = ?";

            var statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("CustomerName"),
                        resultSet.getString("CustomerEmail"),
                        resultSet.getString("CustomerPhone"),
                        resultSet.getString("CustomerAddress"),
                        resultSet.getString("CustomerPostalCode")
                );

                return new Project(
                        resultSet.getInt("ProjectId"),
                        resultSet.getString("ProjectName"),
                        resultSet.getString("ProjectAddress"),
                        resultSet.getString("ProjectPostalCode"),
                        resultSet.getTimestamp("ProjectCreatedAt"),
                        customer
                );
            }
        }

        return null;
    }

    public Project createProject(ProjectDTO projectDTO) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [Project] ([Name], [Address], [PostalCode], [CustomerId]) VALUES (?, ?, ?, ?);";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, projectDTO.getName());
            stmt.setString(2, projectDTO.getAddress());
            stmt.setString(3, projectDTO.getPostalCode());
            stmt.setInt(4, projectDTO.getCustomer().getId());

            var rs = stmt.executeQuery();

            if(rs.next()) {
                int projectId = rs.getInt(1);

                return getProjectById(projectId);
            }
        }

        return null;
    }

    @Override
    public void setTechnicians(List<User> selected, Project project) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql1 = "DELETE FROM [ProjectUserRelation] WHERE [ProjectId] = ?;";
            PreparedStatement ps1 = connection.prepareStatement(sql1);

            ps1.setInt(1, project.getId());


            ps1.execute();

            String sql = "INSERT INTO [ProjectUserRelation] ([ProjectId], [UserId]) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (User u : selected) {
                ps.setInt(1, project.getId());
                ps.setInt(2, u.getId());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    @Override
    public void deleteMultipleProjects(List<Project> projects) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "DELETE FROM [Project] WHERE Id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Project p : projects){
                ps.setInt(1, p.getId());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}
