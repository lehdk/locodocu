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

}
