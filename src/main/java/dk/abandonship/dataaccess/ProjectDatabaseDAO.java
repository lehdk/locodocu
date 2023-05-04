package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDatabaseDAO implements IProjectDAO {

    private final IDocumentationDAO documentationDAO;

    public ProjectDatabaseDAO(IDocumentationDAO documentationDAO) {
        this.documentationDAO = documentationDAO;
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {

        var projects = new ArrayList<Project>();

        try(var connection = DBConnector.getInstance().getConnection()) {

            String sql = "SELECT \n" +
                    "[Project].[Id] AS [ProjectId]," +
                    "[Project].[Name] AS [ProjectName]," +
                    "[CreatedAt] AS [ProjectCreatedAt]," +
                    "[CustomerId]," +
                    "[Customer].[Name] AS [CustomerName]," +
                    "[Customer].[Email] AS [CustomerEmail]," +
                    "[Customer].[Phone] AS [CustomerPhone]," +
                    "[Customer].[Address] AS [CustomerAddress]" +
                    "FROM [Project]" +
                    "JOIN [Customer] ON [CustomerId] = [Customer].[Id]";

            PreparedStatement statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("CustomerName"),
                        resultSet.getString("CustomerEmail"),
                        resultSet.getString("CustomerPhone"),
                        resultSet.getString("CustomerAddress")
                );

                Project project = new Project(
                        resultSet.getInt("ProjectId"),
                        resultSet.getString("ProjectName"),
                        resultSet.getTimestamp("ProjectCreatedAt"),
                        customer
                );

                projects.add(project);
            }

        }

        return projects;
    }

    public void createProject(ProjectDTO projectDTO) throws Exception {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [Project] ([Name], [CustomerId]) VALUES (?, ?);";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, projectDTO.getAddress());
            stmt.setInt(2, projectDTO.getCustomer().getId());

            stmt.execute();
        }
    }
}
