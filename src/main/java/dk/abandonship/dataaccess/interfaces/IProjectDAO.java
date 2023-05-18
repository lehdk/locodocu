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

    Project getProjectById(int id) throws SQLException;

    Project createProject(ProjectDTO projectDTO) throws SQLException;

    /**
     * deletes technicians from the project and set only the assigned technicians
     * @param selected List of technicians to be assigned to a project
     * @param project Project technicians should be assigned to
     * @throws SQLException If error occurred while setting technicians
     */
    void setTechnicians(List<User> selected, Project project) throws SQLException;

    /**
     * Uses Batch to delete multiple projects
     * @param projects List of projects that should be deleted
     * @throws SQLException If error occurred while deleting projects
     */
    void deleteMultipleProjects(List<Project> projects) throws SQLException;
}
