import oracle.jdbc.OracleConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.Properties;

@SpringBootApplication
public class Main {

    final static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {

        for (int i = 0; i < args.length; i++) {
            LOG.info("arg {} = {}", i, args[i]);
        }

        Class.forName("oracle.jdbc.driver.OracleDriver");

        if (args.length != 3) {
            LOG.error("Invalid number of arguments: Must provide 3 arguments in the format: <schema_name> <schema_password> jdbc:oracle:thin:@//<host>:<port>/<SID>");
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("user", args[0]);
        properties.setProperty("password", args[1]);
        properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "10000");

        try {
            LOG.info("****** Starting JDBC Connection test *******");
            String sqlQuery = "select sysdate from dual";

            Connection conn = DriverManager.getConnection(args[2], properties);
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            LOG.info("Running SQL query: [{}]", sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                LOG.info("Result of SQL query: [{}]", resultSet.getString(1));
            }

            statement.close();
            conn.close();

            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.error("Exception occurred connecting to database: {}", ex.getMessage());
        }
    }
}