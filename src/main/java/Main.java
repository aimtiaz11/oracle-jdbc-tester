import oracle.jdbc.OracleConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.Properties;

@SpringBootApplication
public class Main {

    private static final String DEFAULT_QUERY = "select sysdate from dual";
    private static final String DEFAULT_TIMEOUT = "8000";
    private static final Integer DEFAULT_MAX_ROWS = 10;

    final static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        if (args.length < 1) {
            LOG.error("Invalid number of arguments: Must provide at least 1 argument in the format: jdbc:oracle:thin:@//<host>:<port>/<SID> [optional query]");
            return;
        }
        String jdbcUrl = args[0];
        String sqlQuery = args.length >= 2 ? args[1] : DEFAULT_QUERY;

        String user = System.getenv("ORACLEDB_USER");
        String pass = System.getenv("ORACLEDB_PASS");
        String timeout = System.getenv("ORACLEDB_CONNTIMEOUT") != null ? System.getenv("ORACLEDB_CONNTIMEOUT") : DEFAULT_TIMEOUT;
        Integer maxRows = System.getenv("RESULTSET_MAXROWS") != null ? Integer.parseInt(System.getenv("RESULTSET_MAXROWS")) : DEFAULT_MAX_ROWS;
        if (user == null || pass == null){
            LOG.error("ORACLEDB_USER and ORACLEDB_PASS env variables must be set");
            return;
        }

        LOG.info("Connecting via jdbcUrl {}", jdbcUrl);
        LOG.info("Executing {}", sqlQuery == DEFAULT_QUERY ? "default query" : "provided query");
        LOG.info("User provided is {}", user);
        LOG.info("Connection timeout is set to {}{}", timeout, timeout == DEFAULT_TIMEOUT ? " (default)" : "");

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", pass);
        properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, timeout);

        try {
            LOG.info("****** Starting JDBC Connection test *******");
            Connection conn = DriverManager.getConnection(jdbcUrl, properties);
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            LOG.info("Printing query result");
            int count = 0;
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("|");
                System.out.print(rsmd.getColumnName(i));
                if (i == columnsNumber) System.out.print("\n");
            }
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("|");
                    System.out.print(resultSet.getString(i));
                    if (i == columnsNumber) System.out.print("\n");
                }
                count++;
                if(count >= maxRows){
                    break;
                }
            }
            statement.close();
            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.error("Exception occurred connecting to database: {}", ex.getMessage());
        }
    }
}