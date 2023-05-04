package dk.abandonship.dataaccess.interfaces;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IDocumentationDAO {

    /**
     * Gets all the documentation for a project.
     * @param project The project you want the documentation from.
     * @return The documentation as a set
     * @throws SQLException
     */
    Set<Documentation> getDocumentationForProject(Project project) throws SQLException;

    List<DocumentationTextFieldNode> getDocumentationTextField(Documentation documentation) throws SQLException;
}
