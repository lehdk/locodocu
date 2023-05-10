package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.ProjectManager;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.User;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    public void saveTechOnProject(List<User> selected, Project project) throws Exception{
        project.setAssignedTechnicians(selected);
        projectManager.setTechnicians(selected, project);
    }

    public void createNewDoc(String docName, Project project) throws Exception {
        projectManager.createDocument(docName, project);
    }

    public ObservableList getSearchResult(String text) {
        List<Project> searchList = new ArrayList<>();

        if (text.isEmpty()) return  projectObservableList;

        System.out.println(searchList);

        for (Project p : projectObservableList) {
            if (p.getName().toLowerCase().contains(text)) {
                searchList.add(p);
            } else if (p.getCustomer().getName().toLowerCase().contains(text)) {
                searchList.add(p);
            } else if (p.getCustomer().getPostalCode().contains("text")) {
                searchList.add(p);
            }
        }

        System.out.println(searchList);

        ObservableList<Project> searchListObb = FXCollections.observableList(searchList);
        return searchListObb;
    }
}
