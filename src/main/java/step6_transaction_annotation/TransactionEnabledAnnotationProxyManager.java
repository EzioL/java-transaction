package step6_transaction_annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import step3_connection_holder.TransactionManager;

/**
 * Here be dragons Created by @author Ezio on 2019-02-19 14:59
 */
public class TransactionEnabledAnnotationProxyManager {

    private TransactionManager transactionManager;

    public TransactionEnabledAnnotationProxyManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Object proxyFor(Object object) {
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class<?>[] interfaces = object.getClass().getInterfaces();
        AnnotationTransactionInvocationHandler
            invocationHandler = new AnnotationTransactionInvocationHandler(object, transactionManager);

        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    class AnnotationTransactionInvocationHandler implements InvocationHandler {

        private Object object;
        private TransactionManager transactionManager;

        public AnnotationTransactionInvocationHandler(Object object, TransactionManager transactionManager) {
            this.object = object;
            this.transactionManager = transactionManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
            // 判断该方法是否标记有Transactional注解，如果没有，则任何额外功能都不加，直接调用原来service的transfer方法；
            // 否则，将其加入到事务处理中。
            Method originalMethod = proxy.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (originalMethod.isAnnotationPresent(KiteTransactional.class)) {
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

            return method.invoke(proxy, objects);
        }
    }
}
