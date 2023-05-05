package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.User;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Saves the DocumentationTextNode in the data source
     * @param set The Map Entry containing the fx node and DocumentationNode
     * @param doc The documentation you want to save the node to
     * @throws SQLException If an error occurred in the saving process.
     */
    private void saveDocumentationTextNode(Map.Entry<Node, DocumentationNode> set, Documentation doc) throws SQLException {
        if (set.getValue().getId() == DocumentationNode.UNUSED_NODE_ID){
            var result = documentationDAO.createTextNode(((TextArea)set.getKey()).getText(), doc);
            set.setValue(result);
        } else {
            documentationDAO.updateTextNode(set);
        }
    }

    private void saveDocumentationLoginNode(Map.Entry<Node, DocumentationNode> set, Documentation doc) {
        System.out.println("Sovs og kartofler");
    }

    private void saveDocumentationPictureNode(Map.Entry<Node, DocumentationNode> set, Documentation doc) {

    }

    public void saveDoc(LinkedHashMap<Node, DocumentationNode> nodeMap, Documentation doc) throws Exception{
        for (var set : nodeMap.entrySet()) {
            if(set.getValue() instanceof DocumentationTextFieldNode){
                saveDocumentationTextNode(set, doc);
            } else if (set.getValue() instanceof DocumentationLogInNode) {
                saveDocumentationLoginNode(set, doc);
            } else if (set.getValue() instanceof  DocumentationPictureNode) {
                saveDocumentationPictureNode(set, doc);
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

    public void setTechnicians(List<User> selected, Project project) throws Exception{
        projectDAO.setTechnicians(selected, project);
    }
}
