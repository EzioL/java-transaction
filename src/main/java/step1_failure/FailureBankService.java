package step1_failure;

import base.BankService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 16:48
 */
public class FailureBankService implements BankService {

    private ComboPooledDataSource dataSource;

    private FailureRechargeDao rechargeDao;

    private FailureWithdrawDao withdrawDao;

    public FailureBankService(ComboPooledDataSource dataSource) {

        this.dataSource = dataSource;
        this.rechargeDao = new FailureRechargeDao(dataSource);
        this.withdrawDao = new FailureWithdrawDao(dataSource);
    }

    public void transfer(int fromBankId, int toBankId, BigDecimal amount) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            withdrawDao.withdraw(fromBankId, amount);
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
