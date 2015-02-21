package roguelike.DAO;

import org.sqlite.SQLiteConnectionPoolDataSource;
import roguelike.CONFIGURATION;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 17.01.14
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class StupidDataSource implements DataSource {

    private String dbName;

    public StupidDataSource(){
        this.dbName = CONFIGURATION.dbName;
    }

    public StupidDataSource(String dbName){
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
