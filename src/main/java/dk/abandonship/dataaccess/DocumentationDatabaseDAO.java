package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IDocumentationDAO;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            String sql = "SELECT * FROM [DocumentationTextField] WHERE [DocumentationId]=?";

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
}
