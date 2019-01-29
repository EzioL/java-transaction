package step1_failure;

import base.BankQueryHelper;
import base.DataSourceFactory;
import java.math.BigDecimal;
import java.sql.SQLException;
import junit.textui.TestRunner;
import org.junit.Test;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 17:39
 */

public class FailureBankServiceTest extends TestRunner {

    public FailureBankService bankService;

    public BankQueryHelper helper;

    @Test
    public void test_connection() throws SQLException {

        helper = new BankQueryHelper(DataSourceFactory.createDataSource());
        BigDecimal bigDecimal = helper.queryAmount(1);
        System.err.println(bigDecimal);
    }

    public void test_success() {
        bankService = new FailureBankService(DataSourceFactory.createDataSource());
        bankService.transfer(1, 2, new BigDecimal("200"));
    }

    public void test_fail() {

        int toNonExistId = -1;
        bankService = new FailureBankService(DataSourceFactory.createDataSource());
        bankService.transfer(1, toNonExistId, new BigDecimal("200"));
    }
}
