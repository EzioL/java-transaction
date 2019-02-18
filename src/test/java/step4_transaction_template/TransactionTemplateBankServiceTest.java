package step4_transaction_template;

import base.BankQueryHelper;
import base.DataSourceFactory;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.Test;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 11:19
 */
public class TransactionTemplateBankServiceTest {

    public TransactionTemplateBankService bankService;

    public BankQueryHelper helper;

    @Test
    public void test_success() throws SQLException {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));

        bankService = new TransactionTemplateBankService(DataSourceFactory.createDataSource());
        bankService.transfer(1, 2, new BigDecimal("200"));

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }

    @Test
    public void test_fail() throws SQLException {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());
        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
        int toNonExistId = -1;
        bankService = new TransactionTemplateBankService(DataSourceFactory.createDataSource());

        bankService.transfer(1, toNonExistId, new BigDecimal("200"));

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }

}
