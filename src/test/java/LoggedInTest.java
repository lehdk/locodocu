import dk.abandonship.entities.User;
import dk.abandonship.state.LoggedInUserState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoggedInTest {

    @Test
    void canLogInUser() {
        var state = LoggedInUserState.getInstance();

        User loggedInUser = state.getLoggedInUser();
        assertNull(loggedInUser);

        User user = new User(0, "name", null, null, null, null);

        state.setLoggedInUser(user);

        assertEquals(user, state.getLoggedInUser());
    }
}
