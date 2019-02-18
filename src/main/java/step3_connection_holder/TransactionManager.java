package step3_connection_holder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-02-12 16:00
 */
public class TransactionManager {

    private MysqlDataSource dataSource;

    public TransactionManager(MysqlDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public final void start() throws SQLException {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
    }

    public final void commit() throws SQLException {
        Connection connection = getConnection();
        connection.commit();
    }

    public final void rollback() {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't rollback on connection[" + connection + "].", e);
        }
    }

    public final void close() {

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(true);
            connection.setReadOnly(false);
            connection.close();
            SingleThreadConnectionHolder.removeConnection(dataSource);
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't close the connection[" + connection + "].", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return SingleThreadConnectionHolder.getConnection(dataSource);
    }
}
