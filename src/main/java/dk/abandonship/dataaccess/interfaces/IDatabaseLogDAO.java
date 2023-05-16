package dk.abandonship.dataaccess.interfaces;

import java.sql.SQLException;

public interface IDatabaseLogDAO {

    /**
     * Inserts a log into the database
     * @param method The method called
     * @param request The request from the database
     * @param result The result from the database
     * @throws SQLException If an error occurred
     */
    void insertToLog(String method, String request, String result) throws SQLException;

}
