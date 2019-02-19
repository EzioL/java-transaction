package step6_transaction_annotation;

import base.BankService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import step3_connection_holder.ConnectionHolderRechargeDao;
import step3_connection_holder.ConnectionHolderWithdrawDao;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 14:12
 */
public class AnnotationBankService implements BankService {

    private ConnectionHolderRechargeDao connectionHolderRechargeDao;

    private ConnectionHolderWithdrawDao connectionHolderWithdrawDao;

    public AnnotationBankService(MysqlDataSource dataSource) {
        connectionHolderRechargeDao = new ConnectionHolderRechargeDao(dataSource);
        connectionHolderWithdrawDao = new ConnectionHolderWithdrawDao(dataSource);
    }

    @Override
    @KiteTransactional
    public void transfer(int fromId, int toId, BigDecimal amount) {
        try {
            connectionHolderWithdrawDao.withdraw(fromId, amount);
            connectionHolderRechargeDao.recharge(toId, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
