package dk.abandonship.dataaccess.interfaces;

import java.sql.SQLException;
import java.util.List;

import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.User;

public interface IProjectDAO {

    /**
     * Gets all the projects from the datasource
     * @return All the projects
     */
    List<Project> getAllProjects() throws SQLException;

    void createProject(ProjectDTO projectDTO) throws SQLException;

    /**
     * deletes technicians from the project and set only the assigned technicians
     * @param selected List of technicians to be assigned to a project
     * @param project Project technicians should be assigned to
     * @throws SQLException
     */
    void setTechnicians(List<User> selected, Project project) throws SQLException;

    void deleteMultipleProjects(List<Project> projects) throws SQLException;
}
