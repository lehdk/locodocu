package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IDocumentationDAO {

    /**
     * Gets all the documentation for a project.
     * @param project The project you want the documentation from.
     * @return The documentation as a set
     * @throws SQLException If an error occurred while getting from data source.
     */
    Set<Documentation> getDocumentationForProject(Project project) throws SQLException;

    /**
     * returns textfriel½ds  for given document
     * @param documentation the document you want the text fields from
     * @return textfields from the database
     * @throws SQLException If an error occurred while getting from data source.
     */
    List<DocumentationTextFieldNode> getDocumentationTextField(Documentation documentation) throws SQLException;


    /**
     * Returns login data from the database in a DocumentationNode to the specific document
     * @param documentation the document you want the text fields from
     * @return a node with log-In information with information sorted in username and password
     * @throws SQLException If an error occurred while getting from data source.
     */
    List<DocumentationLogInNode> getDocumentationLogIn(Documentation documentation) throws SQLException;

    /**
     * Saves a picture to the data source.
     * @param pictureNode The picture node to save
     * @param title The title of the image.
     * @param doc The Documentation to save the picture to.
     * @return The picture node with the new Id.
     * @throws SQLException If an error occurred while saving the image.
     */
    DocumentationPictureNode createPictureNode(DocumentationPictureNode pictureNode, String title, Documentation doc) throws SQLException;

        /**
         * returns picture and tittle, from the database where the data is saved to the specific document
         * @param documentation the document you want the text fields from
         * @return a node with a document tittle and a picture
         * @throws SQLException If an error occurred while getting from data source.
         */
    List<DocumentationPictureNode> getPictureNode(Documentation documentation) throws SQLException;

    /**
     * Updates a picture node.
     * @param node The node to update.
     * @param newTitle The new title of the image
     * @return True if success. False otherwise.
     * @throws SQLException If an error occurred while updating the picture.
     */
    boolean updatePictureNode(DocumentationPictureNode node, String newTitle) throws SQLException;

    /**
     * creates a text-field in the DB under the document
     * @param text The string that should be saved to DB
     * @param doc The document the text should be connected to
     * @return returns A node with an ID value it gets from the DB
     * @throws SQLException If an error occurred while getting from data source.
     */
    DocumentationTextFieldNode createTextNode(String text, Documentation doc) throws SQLException;

    /**
     * Updates the value in the dB with the string value where the maps Id is true
     * @param text contain new string value and id that exist in DB
     * @throws SQLException If an error occurred while updating to data source.
     */
    void updateTextNode(String text, int id) throws  SQLException;

    /**
     * Inserts a login node to the datasource.
     * @param doc The documentation the node should be added to.
     * @param device The name of device the login info is for.
     * @param username The username data.
     * @param password The password data.
     */
    DocumentationLogInNode createLoginNode(Documentation doc, String device, String username, String password) throws SQLException;

    /**
     * Updates the login node in the datasource.
     * @param nodeId The id of the node to update.
     * @param device The device the login info is about.
     * @param username The new username.
     * @param password The new password.
     * @return True if the change was successful, False otherwise.
     * @throws SQLException If an error happened while updating.
     */
    boolean updateLoginNode(int nodeId, String device, String username, String password) throws SQLException;

    /**
     * Creates new Document to add nodes to
     * @param docName the nam,e of the doc
     * @param project the project the doc should be connected to
     * @throws SQLException If an error occurred while saving to data source.
     */
    Documentation createNewDoc(String docName, Project project) throws SQLException;
}
