package dk.abandonship.dataaccess.interfaces;

import java.sql.SQLException;

public interface IDatabaseLogDAO {

    void insertToLog(String method, String request, String result) throws SQLException;

}
