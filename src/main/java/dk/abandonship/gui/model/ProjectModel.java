package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.PrintPdfManager;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

public class ProjectModel {

    private final ProjectManager projectManager;

    private final PrintPdfManager pdfManager;

    private final ObservableList<Project> projectObservableList;

    public ProjectModel() throws Exception {
        projectManager = new ProjectManager();
        pdfManager = new PrintPdfManager();

        projectObservableList = FXCollections.observableList(projectManager.getAllProjects());
    }

    public ObservableList<Project> getProjectObservableList() {
        return projectObservableList;
    }

    public void createProject(ProjectDTO projectDTO) throws Exception{
        var result = projectManager.createProject(projectDTO);

        if(result == null) return;

        projectObservableList.add(result);
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

    public void printPdf(LinkedHashMap<Node, DocumentationNode> nodeMap, String path, Project project, Documentation documentation) throws Exception {
        pdfManager.generatePDF(nodeMap.values(), path, project, documentation);
    }

    /**
     * Serches the obersevableList for proumt
     * @param text the thing the list should contain
     * @return a new observableList with data that matches the prompt
     */
    public ObservableList<Project> getSearchResult(String text) {
        Stack<Project> searchList = new Stack<>();

        if (text.isEmpty()) return  projectObservableList;


        for (Project p : projectObservableList) {
            if (p.getName().toLowerCase().contains(text)) {                         //if name contains prompt text
                searchList.add(p);
            } else if (p.getCustomer().getName().toLowerCase().contains(text)) {     // if customer contains prompt text
                searchList.add(p);
            } else if (p.getCustomer().getPostalCode().contains(text)) {             // if customer postal code contains prompt text
                searchList.add(p);
            } else if (p.getCustomer().getAddress().toLowerCase().contains(text)) {  // if customers address contains prompt text
                searchList.add(p);
            } else if (docContains(p, text.toLowerCase())){                          //if a document name on a project contains prompt text
                searchList.add(p);
            }
        }

        return FXCollections.observableList(searchList);
    }

    /**
     * if projects documentations list contains a doc named after promt text
     * @param project that should be checked for a specific doc
     * @param text the name of document
     * @return If the documentation contains the specified items
     */
    private boolean docContains(Project project, String text){
        for (var d : project.getDocumentations()) {
            if (d.getName().toLowerCase().contains(text)){
                return true;
            }
        }

        return false;
    }

    public void deleteProjects(List<Project> Projects) throws Exception{
        projectManager.deleteProjects(Projects);
    }
}
