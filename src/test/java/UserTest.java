import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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

        assertEquals(user.getRoles().size(), 0);
        user.addRole(role1);
        assertEquals(user.getRoles().size(), 1);
        user.addRole(role2);
        assertEquals(user.getRoles().size(), 2);
    }

    @Test
    void canNotAddSameRoleTwice() {
        User user1 = new User(0, "name", null, null, null, null);

        Role role = new Role(1, "role1");

        assertEquals(user1.getRoles().size(), 0);
        user1.addRole(role);
        assertEquals(user1.getRoles().size(), 1);
        user1.addRole(role);
        assertEquals(user1.getRoles().size(), 1);
    }
}
