package com.cunoc.edu.gt.proxies;

import com.cunoc.edu.gt.annotations.persistence.Transactional;
import com.cunoc.edu.gt.proxies.manager.TransactionalManager;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Interceptor that manages transactions for methods annotated with {@link Transactional}.
 *
 * @Author: Augusto Vicente
 */
public class TransactionalInterceptor implements InvocationHandler {
    private final Object target;
    private final Connection connection;

    public TransactionalInterceptor(Object target, Connection connection) {
        this.target = target;
        this.connection = connection;
    }

    @Override
    @SneakyThrows
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.isAnnotationPresent(Transactional.class)) {
            TransactionalManager transactionalManager = TransactionalManager.getInstance(connection);
            Object result;
            try {
                transactionalManager.beginTransaction();
                result = method.invoke(target, args);
                transactionalManager.commit();
            } catch (Exception e) {
                transactionalManager.rollback();
                Logger.getLogger("TransactionalInterceptor").severe("Error in transactional method: " + method.getName());
                throw e;
            }
            return result;
        }
        return method.invoke(target, args);
    }

    public static <T> T createProxy(T target, Connection connection, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new TransactionalInterceptor(target, connection)
        );
    }
}