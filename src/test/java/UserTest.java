import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void usersAreEqualOnSameId() {
        User user1 = new User(0, "different", null, null, null, null);
        User user2 = new User(0, "name", null, null, null, null);

        assertEquals(user1, user2);
    }

    @Test
    void usersAreNotTheSameWithDifferentIds() {
        User user1 = new User(0, "name", null, null, null, null);
        User user2 = new User(1, "name", null, null, null, null);

        assertNotEquals(user1, user2);
    }

    @Test
    void canAddRolesToUser() {
        User user = new User(0, "name", null, null, null, null);

        Role role1 = new Role(1, "role1");
        Role role2 = new Role(1, "role2");

        assertEquals(0, user.getRoles().size());
        user.addRole(role1);
        assertEquals(1, user.getRoles().size());
        user.addRole(role2);
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void canNotAddSameRoleTwice() {
        User user1 = new User(0, "name", null, null, null, null);

        Role role = new Role(1, "role1");

        assertEquals(0, user1.getRoles().size());
        user1.addRole(role);
        assertEquals(1, user1.getRoles().size());
        user1.addRole(role);
        assertEquals(1, user1.getRoles().size());
    }
}
