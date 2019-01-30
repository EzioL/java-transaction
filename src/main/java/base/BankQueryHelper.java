package base;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-29 09:52
 */

public class BankQueryHelper {

    MysqlDataSource dataSource;

    public BankQueryHelper(MysqlDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public BigDecimal queryAmount(int bankId) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement selectStatement = connection.prepareStatement("SELECT amount "
            + "FROM d_bank.t_bank WHERE bankId = ?");
        selectStatement.setInt(1, bankId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        BigDecimal Amount = resultSet.getBigDecimal(1);
        resultSet.close();
        selectStatement.close();

        return Amount;
    }
}
