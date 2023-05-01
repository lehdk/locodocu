package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.ProjectManager;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ProjectModel {

    private final ProjectManager projectManager;

    private final ObservableList<Project> projectObservableList;

    public ProjectModel() throws Exception {
        projectManager = new ProjectManager();

        projectObservableList = FXCollections.observableList(projectManager.getAllProjects());
    }

    public ObservableList<Project> getProjectObservableList() {
        return projectObservableList;
    }

    public void createProject(Project project) throws Exception{

    }
}
