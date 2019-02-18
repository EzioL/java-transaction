package step3_connection_holder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Here be dragons Created by @author Ezio on 2019-02-12 15:42
 * 多线程使用不安全
 */
public class ConnectionHolder {

    private Map<MysqlDataSource, Connection> connectionMap = new HashMap<MysqlDataSource, Connection>();

    public Connection getConnection(MysqlDataSource dataSource) throws SQLException {
        Connection connection = connectionMap.get(dataSource);
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
            connectionMap.put(dataSource, connection);
        }
        return connection;
    }

    public void removeConnection(MysqlDataSource dataSource) {
        connectionMap.remove(dataSource);
    }
}
