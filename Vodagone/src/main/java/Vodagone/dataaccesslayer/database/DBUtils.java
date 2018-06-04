package Vodagone.dataaccesslayer.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private DBUtils() {
    }

    public static void checkConnection(Connection connection) throws SQLException {
        if (connection == null) {
            throw new SQLException("The connection is null");
        }
    }
}
