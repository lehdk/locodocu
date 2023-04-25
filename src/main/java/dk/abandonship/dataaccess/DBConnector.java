package dk.abandonship.dataaccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.Main;

import java.sql.Connection;
import java.util.Properties;

public class DBConnector {
    private static DBConnector dbConnector;
    private final SQLServerDataSource dataSource;

    private DBConnector() throws Exception {
        Properties databaseProperties = new Properties();
        var url = Main.class.getResource("config.properties");
        if(url == null) throw new RuntimeException("Could not read config.properties");

        try(var input = url.openStream()) {
            databaseProperties.load(input);
        }

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

    // TODO: Make this not throw an exception!
    public static DBConnector getInstance() throws Exception {
        if (dbConnector == null) dbConnector = new DBConnector();

        return dbConnector;
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
