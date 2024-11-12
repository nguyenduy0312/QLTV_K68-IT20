package DataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides utility methods for establishing and managing JDBC connections.
 */
public class JDBCConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/qltv_k68_it20"; // Connection URL for MySQL database.
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";


    /**
     * Establishes and returns a connection to the database.
     *
     * @return a {@link Connection} object if the connection is successful, or {@code null} if the connection fails.
     */
    public static Connection getJDBCConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return null;
    }

    /**
     * Closes the given database connection.
     *
     * @param connection the {@link Connection} to be closed.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Prints the metadata of the database connected via the given connection.
     *
     * @param connection the {@link Connection} to retrieve metadata from.
     */
    public static void printDatabase(Connection connection) {
        if (connection != null) {
            try {
                DatabaseMetaData metadata = connection.getMetaData();
                System.out.println("Database Product Name: " + metadata.getDatabaseProductName());
                System.out.println("Database Product Version: " + metadata.getDatabaseProductVersion());
            } catch (SQLException e) {
                System.err.println("Error retrieving database metadata: " + e.getMessage());
            }
        } else {
            System.err.println("Cannot print database metadata: connection is null.");
        }
    }
}
