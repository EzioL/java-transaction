package base;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:59
 */
public class DataSourceFactory {

    private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();

    static {

        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/d_bank");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
    }

    public static ComboPooledDataSource createDataSource() {
        return dataSource;
    }
}
