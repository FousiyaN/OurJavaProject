package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ====== CHANGE THESE TO MATCH YOUR DATABASE ======
	static final String URL = "jdbc:mysql://localhost:3306/bankingdb?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "fousiya@123";

    /**
     * Get a connection to the MySQL database.
     * Works locally without SSL for development.
     */
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Return connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database. Check URL, username, and password.", e);
        }
    }
}
   