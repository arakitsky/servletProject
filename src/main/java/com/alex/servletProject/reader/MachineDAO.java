package com.alex.servletProject.reader;

import com.alex.servletProject.Constants;
import com.alex.servletProject.exceptions.SystemException;
import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Layer to work directly with the database.
 * Working with information about errors machine.
 * Connects to the database via jdbc driver
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class MachineDAO implements AutoCloseable {

    public static final String SQL_CREATE_TABLE = "CREATE TABLE sample_table ( id INTEGER IDENTITY, machine_id VARCHAR(256), machine_error VARCHAR(256))";
    public static final String SQL_INSERT_PATTERN = "INSERT INTO sample_table(machine_id,machine_error) VALUES('%s','%s')";
    public static final String SQL_DROP_TABLE = "DROP TABLE sample_table";
    public static final String SQL_FIND_ERROR = "select top 1 machine_error from sample_table where machine_id = %s ";
    public static Logger LOG = Logger.getLogger(MachineDAO.class);

    private String driver;
    private String url;
    private String dbName;
    private String user;

    private String password;
    Connection conn;

    MachineDAO(String driver, String url, String dbName, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    public MachineDAO() throws SystemException {
        driver = Constants.JDBC_DRIVER;
        url = Constants.JDBC_URL;
        dbName = Constants.JDBC_DB_NAME;
        user = Constants.JDBS_USER;
        password = Constants.JDBC_PASSWORD;

    }

    public void createTable() throws SQLException, ClassNotFoundException {
        try {
            update(SQL_CREATE_TABLE);
        } catch (SQLSyntaxErrorException e) {
            LOG.warn("Table already exist " + e.getMessage());
        }
    }

    public void addMachineError(String id, String message) throws SQLException, ClassNotFoundException {
        String query = String.format(SQL_INSERT_PATTERN, id, message);
        update(query);
    }

    public void dropTable() throws SQLException, ClassNotFoundException {
        update(SQL_DROP_TABLE);
    }

    public String findErrorById(String idMachine) throws SQLException, ClassNotFoundException {
        String query = String.format(SQL_FIND_ERROR, idMachine);
        LOG.debug("run query :: " + query);
        try (Statement stmt = getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String message = rs.getString("machine_error");
                LOG.debug("Found in db :: " + message);
                return message;
            } else {
                throw new IllegalArgumentException("Machine with id " + idMachine + " not found");
            }
        }
    }

    //use for SQL commands CREATE, DROP, INSERT and UPDATE
    private synchronized void update(String expression) throws SQLException, ClassNotFoundException {
        LOG.debug("run query :: " + expression);
        Statement st = getConnection().createStatement();    // statements
        int i = st.executeUpdate(expression);    // run the query
        if (i == -1) {
            LOG.error("db error : " + expression);
        }
        st.close();
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName(driver);
            LOG.info("Try connection to database " + url + dbName + " ...");
            conn = DriverManager.getConnection(url + dbName, user, password);
            LOG.info("Connected to " + url + dbName);
        }
        return conn;
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
