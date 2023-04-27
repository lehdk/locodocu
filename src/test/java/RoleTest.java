import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.entities.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private static IRoleDAO roleDAO;

    private static List<Role> roles;

    @BeforeAll
    static void beforeAll() throws SQLException {
        roleDAO = new RoleDatabaseDAO();

        roles = roleDAO.getAllRoles();
    }

    @Test
    void rolesAreEqualOnSameId() {
        Role role1 = new Role(0, "fake");
        Role role2 = new Role(0, "role");

        assertEquals(role1, role2);
    }

    @Test
    void rolesAreNotTheSameWithDifferentIds() {
        Role role1 = new Role(0, "role");
        Role role2 = new Role(1, "role");

        assertNotEquals(role1, role2);
    }

    @Test
    void adminRoleHasId1() {
        Role role = roles.stream().filter(r -> r.getId() == 1).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(role.getId(), 1);
        assertEquals(role.getName(), "admin");
    }

    @Test
    void projectManagerHasRoleHasId2() {
        Role role = roles.stream().filter(r -> r.getId() == 2).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(role.getId(), 2);
        assertEquals(role.getName(), "project-manager");
    }

    @Test
    void technicianHasRoleHasId3() {
        Role role = roles.stream().filter(r -> r.getId() == 3).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(role.getId(), 3);
        assertEquals(role.getName(), "technician");
    }

    @Test
    void salespersonHasRoleHasId4() {
        Role role = roles.stream().filter(r -> r.getId() == 4).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(role.getId(), 4);
        assertEquals(role.getName(), "salesperson");
    }
}
