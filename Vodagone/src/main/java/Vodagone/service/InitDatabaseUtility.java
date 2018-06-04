package Vodagone.service;

import Vodagone.dataaccesslayer.database.DBUtilsTest;
import Vodagone.dataaccesslayer.database.JdbcConnectionFactory;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class InitDatabaseUtility {
    public static void initDatabase(Object testClass, JdbcConnectionFactory jdbcConnectionFactory, DBUtilsTest dbUtilsTest) throws SQLException {
        MockitoAnnotations.initMocks(testClass);
        when(jdbcConnectionFactory.getResourceItem()).thenReturn("inmemorydatabase.properties");
        dbUtilsTest.clearDB(jdbcConnectionFactory.getDBConnection());
        dbUtilsTest.initTabellen(jdbcConnectionFactory.getDBConnection());
        dbUtilsTest.insertDummyData(jdbcConnectionFactory.getDBConnection());
    }
}
