import dk.abandonship.dataaccess.DocumentationDatabaseDAO;
import dk.abandonship.dataaccess.ProjectDatabaseDAO;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.Project;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    void canCreateProjectWithCorrectFields() {
        int id = 42;
        String name = "Det store blå skib";
        Timestamp createdAt = Timestamp.from(Instant.now());
        Customer customer = new Customer(1, null, null, null, null);

        Project p = new Project(id, name, createdAt, customer);

        assertEquals(p.getId(), id);
        assertEquals(p.getName(), name);
        assertEquals(p.getCreatedAt(), createdAt);
        assertEquals(p.getCustomer(), customer);
    }

    @Test
    void projectsAreNotNull() throws SQLException {
        var projectDAO = new ProjectDatabaseDAO(new DocumentationDatabaseDAO(), new UserDatabaseDAO(new RoleDatabaseDAO()));

        var projects = projectDAO.getAllProjects();

        assertNotNull(projects);
    }
}
