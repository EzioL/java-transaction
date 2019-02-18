package step3_connection_holder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Here be dragons Created by @author Ezio on 2019-02-12 15:45
 * 保证一个类的实例变量在各个线程中都有一份单独的拷贝，
 * 从而不会影响其他线程中的实例变量
 */
public class SingleThreadConnectionHolder {

    private static ThreadLocal<ConnectionHolder> connectionHolderThreadLocal = new ThreadLocal<ConnectionHolder>();

    public static Connection getConnection(MysqlDataSource dataSource) throws SQLException {
        return getConnectionHolder().getConnection(dataSource);
    }

    public static void removeConnection(MysqlDataSource dataSource) {
        getConnectionHolder().removeConnection(dataSource);
    }

    private static ConnectionHolder getConnectionHolder() {
        return Optional.ofNullable(connectionHolderThreadLocal.get())
            .orElseGet(() -> {
                ConnectionHolder connectionHolder = new ConnectionHolder();
                connectionHolderThreadLocal.set(connectionHolder);
                return connectionHolder;
            });
    }
}
