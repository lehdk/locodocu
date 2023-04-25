import dk.abandonship.dataaccess.DBConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseTest {

    @Test
    void canConnectToDatabase() throws Exception {
        var dbConnector = DBConnector.getInstance();

        try(var connection = dbConnector.getConnection()) {
            assertFalse(connection.isClosed());
        }
    }

}
