import oracle.jdbc.OracleConnection;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    final static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        for (int i = 0; i < args.length; i++) {
            LOG.log(Level.INFO, "arg + " + i  +" = " + args[i]);
        }

        Class.forName("oracle.jdbc.driver.OracleDriver");

        if (args.length != 3) {
            LOG.log(Level.SEVERE, "Invalid number of arguments: Must provide 3 arguments in the format: <schema_name> <schema_password> jdbc:oracle:thin:@//<host>:<port>/<SID>");
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("user", args[0]);
        properties.setProperty("password", args[1]);
        properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "8000");
        Connection conn = null;
        Statement statement = null;

        try {
            LOG.info("****** Starting JDBC Connection test *******");
            String sqlQuery = "select sysdate from dual";

            conn = DriverManager.getConnection(args[2], properties);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            LOG.log(Level.INFO, "Running SQL query: " + sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                LOG.log(Level.INFO,"Result of SQL query: " + resultSet.getString(1));
            }
            statement.close();
            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE,"Exception occurred connecting to database: " + ex.getMessage());
        } finally {
            if(statement != null) {
                statement.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
    }
}