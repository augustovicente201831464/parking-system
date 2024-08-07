package com.cunoc.edu.gt.utils;

import com.cunoc.edu.gt.annotations.validation.Valid;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ValidatorInterceptor implements InvocationHandler {
    private final Object target;

    public ValidatorInterceptor(Object target) {
        this.target = target;
    }

    @Override
    @SneakyThrows
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.isAnnotationPresent(Valid.class)) {
            for (Object arg : args) {
                if (arg != null) {
                    ValidatorManager.validate(arg);
                }
            }
        }

        return method.invoke(target, args);
    }

    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new ValidatorInterceptor(target)
        );
    }
}