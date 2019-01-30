package step2_ugly;

import base.BankService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Here be dragons Created by @author Ezio on 2019-01-29 18:02
 */
public class UglyBankService implements BankService {

    private UglyWithdrawDao withdrawDao;
    private UglyRechargeDao rechargeDao;
    private DataSource dataSource;

    public UglyBankService(DataSource dataSource) {
        this.dataSource = dataSource;
        rechargeDao = new UglyRechargeDao();
        withdrawDao = new UglyWithdrawDao();
    }

    public void transfer(int fromId, int toId, BigDecimal amount) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            // 提现
            withdrawDao.withdraw(fromId, amount, connection);
            // 充值
            rechargeDao.recharge(toId, amount, connection);

            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
