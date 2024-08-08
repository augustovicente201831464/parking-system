package com.cunoc.edu.gt.config;

import com.cunoc.edu.gt.annotations.auth.PreAuthorize;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@Setter
public class AuthorizationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(PreAuthorize.class)) {
            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
            String expression = preAuthorize.value();

            Logger.getLogger("AuthorizationHandler").info("Evaluating expression: " + expression);

            // Use AuthorizationEvaluator to evaluate the expression
            AuthorizationEvaluator evaluator = new AuthorizationEvaluator(request);
            boolean authorized = evaluator.evaluate(expression);

            if (authorized) {
                Logger.getLogger("AuthorizationHandler").info("Access granted");
                return method.invoke(target, args);
            } else {
                throw new IllegalAccessException("You do not have permission to access this resource.");
            }
        }

        return method.invoke(target, args);
    }

    public static <T> T createProxy(T target, HttpServletRequest request, Class<T> interfaceType) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new AuthorizationHandler(target, request)
        );
    }

    public AuthorizationHandler(Object target, HttpServletRequest request) {
        this.target = target;
        this.request = request;
    }

    private final Object target;
    private HttpServletRequest request;
}