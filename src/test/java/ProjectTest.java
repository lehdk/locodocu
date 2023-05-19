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
        String address = "Gadenavn 1";
        String postalCode = "1242";
        Timestamp createdAt = Timestamp.from(Instant.now());
        Customer customer = new Customer(1, null, null, null, null, null);

        Project p = new Project(id, name, address, postalCode, createdAt, customer);

        assertEquals(id, p.getId());
        assertEquals(name, p.getName());
        assertEquals(address, p.getAddress());
        assertEquals(postalCode, p.getPostalCode());
        assertEquals(createdAt, p.getCreatedAt());
        assertEquals(customer, p.getCustomer());
    }

    @Test
    void projectsAreNotNull() throws SQLException {
        var projectDAO = new ProjectDatabaseDAO(new UserDatabaseDAO(new RoleDatabaseDAO()));

        var projects = projectDAO.getAllProjects();

        assertNotNull(projects);
    }
}
