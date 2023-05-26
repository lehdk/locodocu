import dk.abandonship.utils.PasswordHasher;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestPasswordHash {

    @Test
    void hashIsDifferentFromRaw() {
        String password = UUID.randomUUID().toString();

        var passwordHash = PasswordHasher.getPasswordHash(password);

        assertNotNull(passwordHash);
        assertNotEquals(password, passwordHash);
    }

    @Test
    void passwordMatchesHash() {
        String password = UUID.randomUUID().toString();

        var passwordHash = PasswordHasher.getPasswordHash(password);

        boolean passwordMatches = PasswordHasher.comparePasswordToHash(password, passwordHash);

        assertTrue(passwordMatches);
    }

    @Test
    void passwordDoesNotMatchHash() {

        String password = "password";
        String wrongPassword = "feriusgjn";

        var passwordHash = PasswordHasher.getPasswordHash(password);

        assertNotEquals(password, passwordHash);

        boolean passwordMatches = PasswordHasher.comparePasswordToHash(wrongPassword, passwordHash);

        assertFalse(passwordMatches);
    }
}
