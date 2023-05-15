package dk.abandonship.dataaccess;

import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.state.LoggedInUserState;

import java.sql.SQLException;
import java.sql.Types;

public class DatabaseLogDAO implements IDatabaseLogDAO {
    @Override
    public void insertToLog(String method, String request, String response) throws SQLException {
        try(var connection = DBConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO [DatabaseLog] ([UserId], [Method], [Request], [Response]) VALUES (?, ?, ?, ?);";

            var statement = connection.prepareStatement(sql);

            var loggedInUser = LoggedInUserState.getInstance().getLoggedInUser();

            statement.setObject(1, (loggedInUser == null) ? null : loggedInUser.getId(), Types.INTEGER);
            statement.setString(2, method);
            statement.setString(3, request);
            statement.setString(4, response);

            statement.executeUpdate();
        }
    }
}
