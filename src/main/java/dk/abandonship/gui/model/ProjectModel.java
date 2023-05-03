package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.ProjectManager;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
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

    public void saveToDB(ArrayList<Node> nodeArray) {
        System.out.println(nodeArray.size());

        for (Node n : nodeArray) {
            if(n instanceof TextArea){
                System.out.println("area");
            } else if (n instanceof VBox) {

                int textField = 0;

                for (Node v : ((VBox) n).getChildren()) {
                    if (v instanceof HBox) {
                        System.out.println("images");
                    } else if (v instanceof TextField) {
                        textField++;
                    }

                    if (textField > 1) {
                        System.out.println("LogIn");
                    }
                }

            }
        }
    }
}
