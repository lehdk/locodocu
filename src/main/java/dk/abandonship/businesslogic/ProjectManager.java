package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class ProjectManager {

    private final IProjectDAO projectDAO;
    private final IDocumentationDAO documentationDAO;

    public ProjectManager() {
        this.documentationDAO =  new DocumentationDatabaseDAO();
        this.projectDAO = new ProjectDatabaseDAO(documentationDAO, new UserDatabaseDAO(new RoleDatabaseDAO()));
    }

    /**
     * Gets all the projects in the datasource
     * @return A list of the projects
     * @throws SQLException If an error occurred when accessing the database
     */
    public List<Project> getAllProjects() throws SQLException {
        var projects = projectDAO.getAllProjects();

        projects.parallelStream().forEach(p -> {
            try {
                var documentation = documentationDAO.getDocumentationForProject(p);
                p.setDocumentations(documentation);
            } catch (SQLException e) {
                System.err.println("Could not get documentation for project: " + p.getId());
            }
        });

        return projects;
    }

    public void createProject(ProjectDTO projectDTO) throws Exception{
        projectDAO.createProject(projectDTO);
    }

    public void saveDoc(LinkedHashMap<Node, DocumentationNode> nodeMap, Documentation doc) throws Exception{
        for (var set : nodeMap.entrySet()) {
            if(set.getKey() instanceof TextArea){

                if (set.getValue() == null){
                    var result = documentationDAO.createTextNode(((TextArea)set.getKey()).getText(), doc);
                    set.setValue(result);
                } else {
                    documentationDAO.updateTextNode(set);
                }
            } else if (set.getKey() instanceof VBox) {

                int textField = 0;
                int hBox = 0;

                for (Node v : ((VBox) set.getKey()).getChildren()) {
                    if (v instanceof HBox) {
                        hBox++;

                    } else if (v instanceof TextField) {
                        textField++;
                    }
                }

                if (hBox >= 1) {
                    for (Node v : ((VBox) set.getKey()).getChildren()) {
                        if (v instanceof TextField) {
                            ((TextField) v).getText();
                        }
                        if (v instanceof HBox) {
                            for (Node b : ((HBox) v).getChildren()) {
                                ImageView img = (ImageView) b;
                                //TODO Save IMAGE
                            }
                        }
                    }
                }

                else if (textField >= 2){
                    for (Node v : ((VBox) set.getKey()).getChildren()) {
                        if (v instanceof TextField){
                            System.out.println(((TextField) v).getText());
                            //TODO SAVE log-in
                        }
                    }
                }

            }
        }
    }

    /**
     * Sets data on the given object
     * @param documentation The documentation you want the data from.
     */
    public void loadDocumentationData(Documentation documentation) throws SQLException{
        List<DocumentationTextFieldNode> docTextFields = documentationDAO.getDocumentationTextField(documentation);
        List<DocumentationLogInNode> docLog = documentationDAO.getDocumentationLogIn(documentation);
        List<DocumentationPictureNode> picNode = documentationDAO.getPictureNode(documentation);

        for (DocumentationNode dn: docTextFields) {
            documentation.addDocumentationNode(dn);
        }

        for (var dl : docLog) {
            documentation.addDocumentationNode(dl);
        }

        for (var pn : picNode) {
            documentation.addDocumentationNode(pn);
        }
    }
}
