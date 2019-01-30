package step2_ugly;

import base.BankQueryHelper;
import base.DataSourceFactory;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.Test;

/**
 * Here be dragons Created by @author Ezio on 2019-01-29 18:07
 */
public class UglyBankServiceTest {

    public UglyBankService bankService;

    public BankQueryHelper helper;

    @Test
    public void test_() throws SQLException {

        helper = new BankQueryHelper(DataSourceFactory.createDataSource());

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }

    @Test
    public void test_success() throws SQLException {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());
        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));

        bankService = new UglyBankService(DataSourceFactory.createDataSource());
        bankService.transfer(1, 2, new BigDecimal("200"));

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }


    @Test
    public void test_fail() throws SQLException {
        helper = new BankQueryHelper(DataSourceFactory.createDataSource());
        int toNonExistId = -1;
        bankService = new UglyBankService(DataSourceFactory.createDataSource());

        bankService.transfer(1, toNonExistId, new BigDecimal("200"));

        System.err.println("bank 1 " + helper.queryAmount(1));
        System.err.println("bank 2 " + helper.queryAmount(2));
    }

}
