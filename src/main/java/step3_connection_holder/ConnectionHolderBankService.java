package step3_connection_holder;

import base.BankService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Here be dragons Created by @author Ezio on 2019-02-13 10:32
 */
public class ConnectionHolderBankService implements BankService {

    private TransactionManager transactionManager;

    private ConnectionHolderRechargeDao connectionHolderRechargeDao;

    private ConnectionHolderWithdrawDao connectionHolderWithdrawDao;

    public ConnectionHolderBankService(MysqlDataSource dataSource) {
        transactionManager = new TransactionManager(dataSource);
        connectionHolderRechargeDao = new ConnectionHolderRechargeDao(dataSource);
        connectionHolderWithdrawDao = new ConnectionHolderWithdrawDao(dataSource);
    }

    @Override
    public void transfer(int fromId, int toId, BigDecimal amount) {
        try {
            // 当前线程共用同一个Connection
            transactionManager.start();
            connectionHolderWithdrawDao.withdraw(fromId, amount);
            connectionHolderRechargeDao.recharge(toId, amount);
            transactionManager.commit();
        } catch (SQLException e) {
            transactionManager.rollback();
        } finally {
            transactionManager.close();
        }
    }
}
