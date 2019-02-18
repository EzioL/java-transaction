package step3_connection_holder;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-02-12 17:32
 */
public class ConnectionHolderWithdrawDao {

    private MysqlDataSource dataSource;

    public ConnectionHolderWithdrawDao(MysqlDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void withdraw(int bankId, BigDecimal amount) throws SQLException {

        Connection connection = SingleThreadConnectionHolder.getConnection(dataSource);

        PreparedStatement selectStatement = connection.prepareStatement("SELECT amount "
            + "FROM d_bank.t_bank WHERE bankId = ?");
        selectStatement.setInt(1, bankId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        BigDecimal previousAmount = resultSet.getBigDecimal(1);
        resultSet.close();
        selectStatement.close();

        BigDecimal newAmount = previousAmount.subtract(amount);
        PreparedStatement updateStatement = connection.
            prepareStatement("UPDATE d_bank.t_bank SET amount = ? WHERE bankId = ?");
        updateStatement.setBigDecimal(1, newAmount);
        updateStatement.setInt(2, bankId);
        updateStatement.execute();
        updateStatement.close();
    }
}
