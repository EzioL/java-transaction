package step1_failure;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:51
 */
public class FailureRechargeDao {

    ComboPooledDataSource dataSource;

    public FailureRechargeDao(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void recharge(int bankId, BigDecimal amount) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement selectStatement = connection.prepareStatement("SELECT BANK_AMOUNT "
            + "FROM BANK_ACCOUNT WHERE BANK_ID = ?");
        selectStatement.setInt(1, bankId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        BigDecimal previousAmount = resultSet.getBigDecimal(1);
        resultSet.close();
        selectStatement.close();

        BigDecimal newAmount = previousAmount.subtract(amount);
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE BANK_ACCOUNT SET BANK_AMOUNT = ? WHERE BANK_ID = ?");
        updateStatement.setBigDecimal(1, newAmount);
        updateStatement.setInt(2, bankId);
        updateStatement.execute();
        updateStatement.close();

        connection.close();
    }
}