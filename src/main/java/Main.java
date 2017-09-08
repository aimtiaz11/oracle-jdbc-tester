import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class Main {

    final static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg " + i + " = " + args[i]);
        }

        Class.forName("oracle.jdbc.driver.OracleDriver");

        String username = args[0];
        String password = args[1];
        String url = args[2];

        try {

            LOG.info("****** Starting JDBC Connection test *******");
            String sqlStatement = "select sysdate from DUAL";
            Connection conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            LOG.info("Running SQL query: {}", sqlStatement);
            ResultSet resultSet = stmt.executeQuery(sqlStatement);

            while (resultSet.next()) {
                LOG.info("Result of sql Query: {}", resultSet.getString(1));
            }

            stmt.close();

            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.error("Exception occurred connecting to database: {}", ex.getMessage());
        }
    }
}