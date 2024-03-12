/* package electricity.billing.system;

import java.sql.*;

public class Conn {

    Connection c;
    Statement s;
    Conn() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs/?user=root/");
            s = c.createStatement();
        } catch (SQLException e) {
        }
    }
}
*/

package electricity.billing.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {

    Connection c;
    Statement s;

    public Conn() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connection URL with timezone and other properties as needed
            // Replace `yourPassword` with the actual password, if there's any.
            // If no password is set, use "" (an empty string) instead of `yourPassword`.
            String url = "jdbc:mysql://localhost:3306/ebs?user=root&serverTimezone=UTC";

            
            // Establish the connection
            c = DriverManager.getConnection(url);
            
            // Create a statement
            s = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider more sophisticated error handling in production
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handle missing JDBC driver
        }
    }
}
