import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.DefaultRoles;
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

        assertEquals(1, role.getId());
        assertEquals("admin", role.getName());
    }

    @Test
    void projectManagerHasRoleHasId2() {
        Role role = roles.stream().filter(r -> r.getId() == 2).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(2, role.getId());
        assertEquals("project-manager", role.getName());
    }

    @Test
    void technicianHasRoleHasId3() {
        Role role = roles.stream().filter(r -> r.getId() == 3).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(3, role.getId());
        assertEquals("technician", role.getName());
    }

    @Test
    void salespersonHasRoleHasId4() {
        Role role = roles.stream().filter(r -> r.getId() == 4).findFirst().orElse(null);

        assertNotEquals(role, null);
        assert role != null;

        assertEquals(4, role.getId());
        assertEquals("salesperson", role.getName());
    }

    @Test
    void userHasRoleAdmin() {
        User user = new User(0, "name", null, null, null, null);

        Role admin = new Role(1, "admin");
        user.addRole(admin);

        LoggedInUserState.getInstance().setLoggedInUser(user);

        assertTrue(LoggedInUserState.getInstance().getLoggedInUser().hasRole(DefaultRoles.ADMIN));
    }

    @Test
    void userDoesNotHaveRoleAdmin() {
        User user = new User(0, "name", null, null, null, null);

        Role pm = new Role(2, "project-manager");
        user.addRole(pm);

        LoggedInUserState.getInstance().setLoggedInUser(user);

        assertFalse(LoggedInUserState.getInstance().getLoggedInUser().hasRole(DefaultRoles.ADMIN));
    }
}
