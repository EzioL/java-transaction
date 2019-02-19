package step5_transaction_proxy;

import base.BankQueryHelper;
import base.BankService;
import base.DataSourceFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.Test;
import step3_connection_holder.TransactionManager;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 14:14
 */
public class BareBankServiceTest {

    public BankQueryHelper helper;

    @Test
    public void test_proxy() {
    }

    @Test
    public void test_success() throws Exception {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));

        MysqlDataSource dataSource = DataSourceFactory.createDataSource();

        BareBankService bareBankService = new BareBankService(dataSource);
        // 生成代理对象
        try {
            TransactionEnabledProxyManager transactionEnabledProxyManager = new TransactionEnabledProxyManager(new TransactionManager(dataSource));
            BankService proxyBankService = (BankService) transactionEnabledProxyManager.proxyFor(bareBankService);
            proxyBankService.transfer(1, 2, new BigDecimal("200"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }

    @Test
    public void test_fail() throws SQLException {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());
        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
        int toNonExistId = -1;
        MysqlDataSource dataSource = DataSourceFactory.createDataSource();
        // 生成代理对象
        TransactionEnabledProxyManager transactionEnabledProxyManager = new TransactionEnabledProxyManager(new TransactionManager(dataSource));
        BankService proxyBankService = (BankService) transactionEnabledProxyManager.proxyFor(new BareBankService(dataSource));
        // do
        proxyBankService.transfer(1, toNonExistId, new BigDecimal("200"));

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }
}
