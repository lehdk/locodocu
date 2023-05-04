package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DocumentationDatabaseDAO implements IDocumentationDAO {

    @Override
    public Set<Documentation> getDocumentationForProject(Project project) throws SQLException {
        Set<Documentation> documentationSet = new HashSet<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql =
                    "SELECT [Id], [Name] FROM [Documentation] WHERE [Id] IN (" +
                    "SELECT [DocumentationId] FROM [ProjectDocumentationRelation] WHERE [ProjectId]=?" +
                    ");";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, project.getId());

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                documentationSet.add(new Documentation(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name")
                ));
            }
        }

        return documentationSet;
    }

    @Override
    public List<DocumentationTextFieldNode> getDocumentationTextField(Documentation documentation) throws SQLException{
        List<DocumentationTextFieldNode> textFieldNodeList = new ArrayList<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [DocumentationTextNode] WHERE [DocumentationId]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentation.getId());

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                textFieldNodeList.add(new DocumentationTextFieldNode(
                        resultSet.getInt("Id"),
                        resultSet.getString("Text")
                        )
                );
            }
        }

        return textFieldNodeList;
    }

    @Override
    public List<DocumentationLogInNode> getDocumentationLogIn(Documentation documentation) throws SQLException {
        List<DocumentationLogInNode> logNodes = new ArrayList<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [DocumentationLoginNode] WHERE [DocumentationId]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentation.getId());

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                logNodes.add(new DocumentationLogInNode(
                        resultSet.getInt("Id"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password")
                        )
                );
            }
        }

        return logNodes;
    }

    @Override
    public List<DocumentationPictureNode> getPictureNode(Documentation documentation) throws SQLException {
        List<DocumentationPictureNode> picNodes = new ArrayList<>();

        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM [DocumentationPictureNode] WHERE [DocumentationId]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentation.getId());

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                picNodes.add(new DocumentationPictureNode(
                                resultSet.getInt("Id"),
                                resultSet.getString("Title"),
                                convertBteToImg(resultSet.getBytes("Data"))
                        )
                );
            }
        }

        return picNodes;
    }

    @Override
    public DocumentationTextFieldNode createTextNode(String text, Documentation doc) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql ="INSERT INTO [DocumentationTextNode] ([DocumentationId], [Text]) VALUES (?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, doc.getId());
            statement.setString(2, text);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new DocumentationTextFieldNode(
                        rs.getInt(1),
                        text
                );
            }
        }

        return null;
    }

    @Override
    public void updateTextNode( Map.Entry<Node, DocumentationNode> set) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql ="UPDATE [DocumentationTextNode] SET [Text] = ? WHERE [Id] = ?;";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ((TextArea)set.getKey()).getText());
            statement.setInt(2, set.getValue().getId());

            statement.executeQuery();
        }

    }

    private Image convertBteToImg(byte[] arrByte) {
        Image img = new Image(new ByteArrayInputStream(arrByte));
        return img;
    }
}
