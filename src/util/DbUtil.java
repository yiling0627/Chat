package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
    private String dbUrl = "jdbc:mysql://localhost:3306/chat?useSSL=false";
    private String dbUserName = "root";
    private String dbPassword = "lyl09050627m";
    private String jdbcName = "java.sql.Connection";
    /*
     * Database connection
     */
    public Connection getCon() throws Exception {
        Class.forName(jdbcName);
        Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        return con;
    }
    /*
     * Close database connection
     */
    public void closeConnect(java.sql.Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }

}
