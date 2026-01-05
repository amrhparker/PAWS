package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:derby://localhost:1527/PAWSdb;currentSchema=APP";

    private static final String USER = "app";
    private static final String PASS = "app";

    static {
        try {
            // Load Derby Client Driver (important for some setups)
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("✅ Derby driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Derby driver not found");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        if (conn != null && !conn.isClosed()) {
            System.out.println("✅ Connected to database");
            System.out.println("DB URL  = " + conn.getMetaData().getURL());
            System.out.println("DB User = " + conn.getMetaData().getUserName());
        }

        return conn;
    }
}
