package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.ProjectManager;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.sql.SQLException;
import java.util.LinkedHashMap;

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

    public void createProject(ProjectDTO projectDTO) throws Exception{
        projectManager.createProject(projectDTO);
    }

    public void saveToDB(LinkedHashMap<Node, DocumentationNode> nodeMap, Documentation doc) throws Exception {
        projectManager.saveDoc(nodeMap, doc);
    }

    public void loadDocumentationData(Documentation documentation) throws SQLException {
        projectManager.loadDocumentationData(documentation);
    }
}
