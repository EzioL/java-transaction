package step5_transaction_proxy;

import base.BankService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import step3_connection_holder.ConnectionHolderRechargeDao;
import step3_connection_holder.ConnectionHolderWithdrawDao;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 14:12
 */
public class BareBankService implements BankService {

    private ConnectionHolderRechargeDao connectionHolderRechargeDao;

    private ConnectionHolderWithdrawDao connectionHolderWithdrawDao;

    public BareBankService(MysqlDataSource dataSource) {
        connectionHolderRechargeDao = new ConnectionHolderRechargeDao(dataSource);
        connectionHolderWithdrawDao = new ConnectionHolderWithdrawDao(dataSource);
    }

    @Override
    public void transfer(int fromId, int toId, BigDecimal amount) {
        try {
            connectionHolderWithdrawDao.withdraw(fromId, amount);
            connectionHolderRechargeDao.recharge(toId, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
