package dk.abandonship.dataaccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.utils.PropertyReader;

import java.sql.Connection;

/**
 * This class handles the connection to the database.
 */
public class DBConnector {
    private static DBConnector dbConnector;
    private final SQLServerDataSource dataSource;

    private DBConnector() {

        var databaseProperties = PropertyReader.getConfigFile("config.properties");

        String server = databaseProperties.getProperty("DB_IP");
        String database = databaseProperties.getProperty("DB_DATABASE");
        int port = Integer.parseInt(databaseProperties.getProperty("DB_PORT"));
        String user = databaseProperties.getProperty("DB_USER");
        String password = databaseProperties.getProperty("DB_PASSWORD");

        dataSource = new SQLServerDataSource();
        dataSource.setServerName(server);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setTrustServerCertificate(true);
    }

    /**
     * Gets the instance of the singleton.
     * Will instantiate new object first time it is run.
     * @return The DBConnector instance
     */
    public static DBConnector getInstance() {
        if (dbConnector == null) dbConnector = new DBConnector();

        return dbConnector;
    }

    /**
     * Gets a connection to the database.
     * @return The connection made
     * @throws SQLServerException If it was not able to create a connection
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
