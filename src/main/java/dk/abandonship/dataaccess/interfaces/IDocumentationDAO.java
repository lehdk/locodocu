package dk.abandonship.dataaccess.interfaces;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import javafx.scene.Node;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDocumentationDAO {

    /**
     * Gets all the documentation for a project.
     * @param project The project you want the documentation from.
     * @return The documentation as a set
     * @throws SQLException
     */
    Set<Documentation> getDocumentationForProject(Project project) throws SQLException;

    /**
     * returns textfriel½ds  for given document
     * @param documentation the document you want the text fields from
     * @return textfields from the database
     * @throws SQLException
     */
    List<DocumentationTextFieldNode> getDocumentationTextField(Documentation documentation) throws SQLException;


    /**
     * Returns login data from the database in a DocumentationNode to the specific document
     * @param documentation the document you want the text fields from
     * @return a node with log-In information with information sorted in username and password
     * @throws SQLException
     */
    List<DocumentationLogInNode> getDocumentationLogIn(Documentation documentation) throws SQLException;

    /**
     * returns picture and tittle, from the database where the data is saved to the specific document
     * @param documentation the document you want the text fields from
     * @return a node with a document tittle and a picture
     * @throws SQLException
     */
    List<DocumentationPictureNode> getPictureNode(Documentation documentation) throws SQLException;

    /**
     * creates a text-field in the DB under the document
     * @param text the string that should be saved to DB
     * @param doc the document the text should be connected to
     * @return returns a node with a ID value it gets from the DB
     * @throws SQLException
     */
    DocumentationTextFieldNode createTextNode(String text, Documentation doc) throws SQLException;

    /**
     * Updates the value in the dB with the string value where the maps Id is true
     * @param set contain new string value and id that exist in DB
     * @throws SQLException
     */
    void updateTextNode( Map.Entry<Node, DocumentationNode> set) throws  SQLException;

    /**
     * Inserts a login node to the datasource
     * @param doc The documentation the node should be added to
     * @param username The username data
     * @param password The password data
     */
    DocumentationLogInNode createLoginNode(Documentation doc, String username, String password) throws SQLException;
}
