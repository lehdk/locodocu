package dk.abandonship.dataaccess.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import javafx.scene.Node;

public interface IProjectDAO {

    /**
     * Gets all the projects from the datasource
     * @return All the projects
     */
    List<Project> getAllProjects() throws SQLException;

    void createProject(ProjectDTO projectDTO) throws SQLException;

}
