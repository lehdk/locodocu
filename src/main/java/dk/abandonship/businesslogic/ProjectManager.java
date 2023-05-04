package dk.abandonship.businesslogic;

import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.dataaccess.interfaces.IProjectDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class ProjectManager {

    private final IProjectDAO projectDAO;
    private final IDocumentationDAO documentationDAO;

    public ProjectManager() {
        this.documentationDAO =  new DocumentationDatabaseDAO();
        this.projectDAO = new ProjectDatabaseDAO(documentationDAO);
    }

    /**
     * Gets all the projects in the datasource
     * @return A list of the projects
     * @throws SQLException
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
                //TODO save textarea
                System.out.println(((TextArea) set.getKey()).getText());
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
                                System.out.println(convertImgToByte(img.getImage()));
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

    private byte[] convertImgToByte(Image img) throws Exception{
        byte[]  data = img.getUrl().getBytes(StandardCharsets.UTF_8); //TODO NOTE this may only work locally need test
        return data;
    }

    private Image convertBteToImg(byte[] arrByte) {
        Image img = new Image(new ByteArrayInputStream(arrByte));
        return img;
    }

    /**
     * // Sets data on the given object
     * @param documentation
     */
    public void loadDocumentationData(Documentation documentation) throws SQLException{
        List<DocumentationTextFieldNode> docTextFields = documentationDAO.getDocumentationTextField(documentation);

        for (DocumentationNode dn: docTextFields) {
            documentation.addDocumentationNode(dn);
        }
    }
}
