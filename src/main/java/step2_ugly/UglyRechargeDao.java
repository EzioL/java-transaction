package step2_ugly;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:51
 */
public class UglyRechargeDao {

    void recharge(int bankId, BigDecimal amount, Connection connection) throws SQLException {

        PreparedStatement selectStatement = connection.prepareStatement("SELECT amount "
            + "FROM d_bank.t_bank WHERE bankId = ?");
        selectStatement.setInt(1, bankId);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        BigDecimal previousAmount = resultSet.getBigDecimal(1);
        resultSet.close();
        selectStatement.close();

        BigDecimal newAmount = previousAmount.add(amount);
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE t_bank SET amount = ? WHERE bankId = ?");
        updateStatement.setBigDecimal(1, newAmount);
        updateStatement.setInt(2, bankId);
        updateStatement.execute();
        updateStatement.close();
    }
}
