package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.ProjectManager;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public void saveToDB(LinkedHashMap<Node, DocumentationNode> nodeMap) throws Exception {
        System.out.println(nodeMap.size());
        projectManager.saveDoc(nodeMap);
    }
}
