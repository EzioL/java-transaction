package step4_transaction_template;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import step3_connection_holder.TransactionManager;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 11:06
 */
public abstract class TransactionTemplate {

    private TransactionManager transactionManager;

    public TransactionTemplate(MysqlDataSource dataSource) {
        this.transactionManager = new TransactionManager(dataSource);
    }

    protected abstract void doJob() throws Exception;

    public void doJobInTransaction() {
        try {
            transactionManager.start();
            doJob();
            transactionManager.commit();
        } catch (Exception e) {
            transactionManager.rollback();
        } finally {
            transactionManager.close();
        }
    }
}
