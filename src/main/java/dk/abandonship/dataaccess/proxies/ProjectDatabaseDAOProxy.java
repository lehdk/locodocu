package dk.abandonship.dataaccess.proxies;

import com.google.gson.Gson;
import dk.abandonship.dataaccess.DatabaseLogDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.User;

import java.sql.SQLException;
import java.util.List;

public class ProjectDatabaseDAOProxy implements IProjectDAO {

    private final IDatabaseLogDAO databaseLogDAO;

    private final IProjectDAO projectDAO;

    public ProjectDatabaseDAOProxy() {
        databaseLogDAO = new DatabaseLogDAO();

        projectDAO = new ProjectDatabaseDAO(new UserDatabaseDAOProxy());
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {
        var result = projectDAO.getAllProjects();

        databaseLogDAO.insertToLog("getAllProjects", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public void createProject(ProjectDTO projectDTO) throws SQLException {
        projectDAO.createProject(projectDTO);

        databaseLogDAO.insertToLog("createProject", null, null);
    }

    @Override
    public void setTechnicians(List<User> selected, Project project) throws SQLException {
        projectDAO.setTechnicians(selected, project);

        databaseLogDAO.insertToLog("setTechnicians", new Gson().toJson(selected) + new Gson().toJson(project), null);
    }

    @Override
    public void deleteMultipleProjects(List<Project> projects) throws SQLException {
        projectDAO.deleteMultipleProjects(projects);

        databaseLogDAO.insertToLog("deleteMultipleProjects", new Gson().toJson(projects), null);
    }
}
