package Vodagone.dataaccesslayer.database;

import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class JdbcConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(JdbcConnectionFactory.class.getName());

    public String getResourceItem() {
        return "database.properties";
    }

    public InputStream getResourceItemInputStream() {
        return getClass().getClassLoader().getResourceAsStream(getResourceItem());
    }

    public Connection getDBConnection() {
        Connection connection = null;
        try {
            Properties properties = new Properties();
            InputStream resourceItem = getResourceItemInputStream();
            if (resourceItem == null) {
                throw new FileNotFoundException();
            }
            properties.load(resourceItem);

            if (properties.getProperty("driver") == null) {
                throw new ClassNotFoundException();
            }

            Class.forName(properties.getProperty("driver"));

            connection = DriverManager.getConnection(properties.getProperty("databaseurl"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to a database:  " + e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not find Class:  " + e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "There was an IO issue:  " + e);
        }
        return connection;
    }
}

/*public class JdbcConnectionFactory {
    private static Logger logger = Logger.getLogger(JdbcConnectionFactory.class.getName());
    private Properties properties;

    public JdbcConnectionFactory(){
        try{
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
    }

    public Connection create(){
        try{
            return DriverManager.getConnection(properties.getProperty("databaseurl"));
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return null;
    }
}*/
