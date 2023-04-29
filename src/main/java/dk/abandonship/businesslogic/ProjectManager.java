package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Project;

import java.sql.SQLException;
import java.util.List;

public class ProjectManager {

    private final IProjectDAO projectDAO;
    private final IDocumentationDAO documentationDAO;

    public ProjectManager() {
        this.documentationDAO =  new DocumentationDatabaseDAO();
        this.projectDAO = new ProjectDatabaseDAO(documentationDAO);
    }

    /**
     * Gets all the projects in the datasource
     * @return A list of the projects
     * @throws SQLException
     */
    public List<Project> getAllProjects() throws SQLException {
        var projects = projectDAO.getAllProjects();

        projects.parallelStream().forEach(p -> {
            try {
                var documentation = documentationDAO.getDocumentationForProject(p);
                p.setDocumentations(documentation);
            } catch (SQLException e) {
                System.err.println("Could not get documentation for project: " + p.getId());
            }
        });

        return projects;
    }

}
