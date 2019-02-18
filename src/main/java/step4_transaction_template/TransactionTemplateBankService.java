package step4_transaction_template;

import base.BankService;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import step3_connection_holder.ConnectionHolderRechargeDao;
import step3_connection_holder.ConnectionHolderWithdrawDao;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 11:14
 */
public class TransactionTemplateBankService implements BankService {

    private MysqlDataSource dataSource;

    private ConnectionHolderRechargeDao connectionHolderRechargeDao;

    private ConnectionHolderWithdrawDao connectionHolderWithdrawDao;

    public TransactionTemplateBankService(MysqlDataSource dataSource) {
        this.dataSource = dataSource;
        connectionHolderRechargeDao = new ConnectionHolderRechargeDao(dataSource);
        connectionHolderWithdrawDao = new ConnectionHolderWithdrawDao(dataSource);
    }

    @Override
    public void transfer(int fromId, int toId, BigDecimal amount) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSource) {
            @Override
            protected void doJob() throws Exception {
                connectionHolderWithdrawDao.withdraw(fromId, amount);
                connectionHolderRechargeDao.recharge(toId, amount);
            }
        };
        transactionTemplate.doJobInTransaction();
    }
}
