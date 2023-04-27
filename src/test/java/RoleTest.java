import dk.abandonship.entities.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

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
}
