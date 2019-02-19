package step5_transaction_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import step3_connection_holder.TransactionManager;

/**
 * Here be dragons Created by @author Ezio on 2019-02-18 13:57
 */
public class TransactionEnabledProxyManager {

    private TransactionManager transactionManager;

    public TransactionEnabledProxyManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Object proxyFor(Object object) {
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class<?>[] interfaces = object.getClass().getInterfaces();
        TransactionInvocationHandler invocationHandler = new TransactionInvocationHandler(object, transactionManager);

        return Proxy.newProxyInstance(classLoader,interfaces , invocationHandler);
    }

    class TransactionInvocationHandler implements InvocationHandler {

        private Object object;
        private TransactionManager transactionManager;

        public TransactionInvocationHandler(Object object, TransactionManager transactionManager) {
            this.object = object;
            this.transactionManager = transactionManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {

            transactionManager.start();

            Object result = null;
            try {
                result = method.invoke(proxy, objects);
                transactionManager.commit();
            } catch (Exception e) {
                transactionManager.rollback();
            } finally {
                transactionManager.close();
            }
            return result;
        }
    }
}
