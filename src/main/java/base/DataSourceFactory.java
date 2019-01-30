package base;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:59
 */
public class DataSourceFactory {

    private static final MysqlDataSource dataSource = new MysqlDataSource();

    static {

        //dataSource.setDatabaseName("d_bank");
        //dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql://localhost:3306/d_bank?useSSL=false");
    }

    public static MysqlDataSource createDataSource() {
        return dataSource;
    }
}
