package step1_failure;

import base.BankService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:48
 */
public class FailureBankService implements BankService {

    private MysqlDataSource dataSource;

    private FailureRechargeDao rechargeDao;

    private FailureWithdrawDao withdrawDao;

    public FailureBankService(MysqlDataSource dataSource) {

        this.dataSource = dataSource;
        this.rechargeDao = new FailureRechargeDao(dataSource);
        this.withdrawDao = new FailureWithdrawDao(dataSource);
    }

    public void transfer(int fromBankId, int toBankId, BigDecimal amount) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            // 提现
            withdrawDao.withdraw(fromBankId, amount);
            // 充值
            rechargeDao.recharge(toBankId, amount);

            connection.commit();
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
