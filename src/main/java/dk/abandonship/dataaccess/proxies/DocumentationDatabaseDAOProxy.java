package dk.abandonship.dataaccess.proxies;

import com.google.gson.Gson;
import dk.abandonship.dataaccess.DatabaseLogDAO;
import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DocumentationDatabaseDAOProxy implements IDocumentationDAO {

    private final IDatabaseLogDAO databaseLogDAO;

    private final IDocumentationDAO documentationDAO;

    public DocumentationDatabaseDAOProxy() {
        databaseLogDAO = new DatabaseLogDAO();

        documentationDAO = new DocumentationDatabaseDAO();
    }

    @Override
    public Set<Documentation> getDocumentationForProject(Project project) throws SQLException {
        var result = documentationDAO.getDocumentationForProject(project);

        databaseLogDAO.insertToLog(
                "getDocumentationForProject",
                new Gson().toJson(project),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public List<DocumentationTextFieldNode> getDocumentationTextField(Documentation documentation) throws SQLException {
        var result = documentationDAO.getDocumentationTextField(documentation);

        databaseLogDAO.insertToLog(
                "getDocumentationTextField",
                new Gson().toJson(documentation),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public List<DocumentationLogInNode> getDocumentationLogIn(Documentation documentation) throws SQLException {
        var result = documentationDAO.getDocumentationLogIn(documentation);

        databaseLogDAO.insertToLog(
                "getDocumentationLogIn",
                new Gson().toJson(documentation),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public DocumentationPictureNode createPictureNode(DocumentationPictureNode pictureNode, String title, Documentation doc) throws SQLException {
        var result = documentationDAO.createPictureNode(pictureNode, title, doc);

        databaseLogDAO.insertToLog(
                "createPictureNode",
                new Gson().toJson(pictureNode) + new Gson().toJson(title) + new Gson().toJson(doc),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public List<DocumentationPictureNode> getPictureNode(Documentation documentation) throws SQLException {
        var result = documentationDAO.getPictureNode(documentation);

        databaseLogDAO.insertToLog(
                "getPictureNode",
                new Gson().toJson(documentation),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public boolean updatePictureNode(DocumentationPictureNode node, String newTitle) throws SQLException {
        var result = documentationDAO.updatePictureNode(node, newTitle);

        databaseLogDAO.insertToLog(
                "updatePictureNode",
                new Gson().toJson(node) + new Gson().toJson(newTitle),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public DocumentationTextFieldNode createTextNode(String text, Documentation doc) throws SQLException {
        var result = documentationDAO.createTextNode(text, doc);

        databaseLogDAO.insertToLog(
                "createTextNode",
                new Gson().toJson(text) + new Gson().toJson(doc),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public void updateTextNode(String text, int id) throws SQLException {
        documentationDAO.updateTextNode(text, id);

        databaseLogDAO.insertToLog(
                "updateTextNode",
                new Gson().toJson(text) + new Gson().toJson(id),
                null
        );
    }

    @Override
    public DocumentationLogInNode createLoginNode(Documentation doc, String device, String username, String password) throws SQLException {
        var result = documentationDAO.createLoginNode(doc, device, username, password);

        databaseLogDAO.insertToLog(
                "createLoginNode",
                new Gson().toJson(doc) + new Gson().toJson(device) + new Gson().toJson(username) + new Gson().toJson(password),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public boolean updateLoginNode(int nodeId, String device, String username, String password) throws SQLException {
        var result = documentationDAO.updateLoginNode(nodeId, device, username, password);

        databaseLogDAO.insertToLog(
                "updateLoginNode",
                new Gson().toJson(nodeId) + new Gson().toJson(device) + new Gson().toJson(username) + new Gson().toJson(password),
                new Gson().toJson(result)
        );

        return result;
    }

    @Override
    public Documentation createNewDoc(String docName, Project project) throws SQLException {
        var result = documentationDAO.createNewDoc(docName, project);

        databaseLogDAO.insertToLog(
                "createNewDoc",
                new Gson().toJson(docName) + new Gson().toJson(project),
                new Gson().toJson(result)
        );

        return result;
    }
}
