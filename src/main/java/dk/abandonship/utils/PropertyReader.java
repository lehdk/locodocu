package dk.abandonship.utils;

import dk.abandonship.Main;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    /**
     * Read a .properties file
     * @param filename The filename to read
     * @return The Properties object read
     */
    public static Properties getConfigFile(String filename) {
        Properties databaseProperties = new Properties();
        var url = Main.class.getResource(filename);

        if(url == null) throw new RuntimeException("Could not read config.properties");

        try(var input = url.openStream()) {
            databaseProperties.load(input);
            return databaseProperties;
        } catch (IOException e) {
            System.out.println("Could not read config.properties");
            throw new RuntimeException(e);
        }
    }
}
