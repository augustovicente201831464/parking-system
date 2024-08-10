package com.cunoc.edu.gt.input.controller.auth;

import com.cunoc.edu.gt.config.AuthorizationHandler;
import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.constants.FilenameConstant;
import com.cunoc.edu.gt.data.pagination.util.PageRequest;
import com.cunoc.edu.gt.data.pagination.util.Sort;
import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.request.auth.UserRequest;
import com.cunoc.edu.gt.exception.BadOperationException;
import com.cunoc.edu.gt.input.handling.auth.UserControllerHandling;
import com.cunoc.edu.gt.output.persistence.adapter.auth.UserPA;
import com.cunoc.edu.gt.output.persistence.connection.CustomizedConnection;
import com.cunoc.edu.gt.service.auth.UserService;
import com.cunoc.edu.gt.ucextends.auth.UserUC;
import com.cunoc.edu.gt.utils.TransactionalInterceptor;
import com.cunoc.edu.gt.utils.ValidatorInterceptor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Controller for the user
 *
 * @Author: Augusto Vicente
 */
@WebServlet("/usuario")
public class UserController extends HttpServlet {

    /**
     * Do post method
     *
     * @param req the request
     * @param res the response
     */
    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        Logger.getLogger("UserController").info("POST request received.");

        HttpSession session = req.getSession();

        String action = req.getParameter(AttributeNameConstant.ACTION);

        if (action == null) {
            throw new BadOperationException(String.format("In the %s method of %s the action is required.", "POST", "UserController"));
        }

        Logger.getLogger("UserController").info("Action: " + action);

        // Set the request in the AuthorizationHandler proxy
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        if (handler instanceof AuthorizationHandler) {
            ((AuthorizationHandler) handler).setRequest(req);
        }

        switch (action) {
            case "login" -> {

                session.removeAttribute(AttributeNameConstant.LOGIN_RESPONSE);
                session.removeAttribute(AttributeNameConstant.ERROR);

                UserLoginRequest loginRequest = UserControllerHandling.userLoginHandling(req, res);

                try {
                    session.setAttribute(AttributeNameConstant.LOGIN_RESPONSE, service.login(loginRequest));
                    res.sendRedirect(FilenameConstant.HOME_JSP);
                    Logger.getLogger("UserController").info("User logged in successfully.");
                } catch (Exception e) {
                    Throwable rootCause = e;
                    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                        rootCause = rootCause.getCause();
                    }

                    req.getSession().setAttribute(AttributeNameConstant.ERROR, rootCause.getMessage());
                    Logger.getLogger("UserController").info("User login failed: " + rootCause.getMessage());
                    res.sendRedirect(FilenameConstant.LOGIN_JSP);
                }
            }
            case "logout" -> {
                session.removeAttribute(AttributeNameConstant.LOGIN_RESPONSE);
                session.removeAttribute(AttributeNameConstant.ERROR);
                res.sendRedirect(FilenameConstant.LOGIN_JSP);
                Logger.getLogger("UserController").info("User logged out successfully.");
            }
            case "register" -> {

                session.removeAttribute(AttributeNameConstant.USER_RESPONSE);
                session.removeAttribute(AttributeNameConstant.ERROR);

                UserRequest userRequest = UserControllerHandling.userRegisterHandling(req, res);

                try {
                    session.setAttribute(AttributeNameConstant.USER_RESPONSE, service.register(userRequest));
                    Logger.getLogger("UserController").info("User registered successfully.");
                    res.sendRedirect(FilenameConstant.LOGIN_JSP);
                } catch (Exception e) {
                    Throwable rootCause = e;
                    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                        rootCause = rootCause.getCause();
                    }

                    req.getSession().setAttribute(AttributeNameConstant.ERROR, rootCause.getMessage());
                    Logger.getLogger("UserController").info("User registration failed: " + rootCause.getMessage());
                    res.sendRedirect(FilenameConstant.REGISTER_JSP);
                }
            }
            case "get-page" -> {
                try {

                    int page = Integer.parseInt(req.getParameter(AttributeNameConstant.PAGE) == null ? "1" : req.getParameter(AttributeNameConstant.PAGE));
                    int size = Integer.parseInt(req.getParameter(AttributeNameConstant.SIZE) == null ? "10" : req.getParameter(AttributeNameConstant.SIZE));
                    String orders = req.getParameter(AttributeNameConstant.SORT) == null ? "id" : req.getParameter(AttributeNameConstant.SORT);
                    boolean asc = Boolean.parseBoolean(req.getParameter(AttributeNameConstant.DIRECTION) == null ? "true" : req.getParameter(AttributeNameConstant.DIRECTION));

                    req.getSession().setAttribute(AttributeNameConstant.PAGE_USER, service.getPage(PageRequest.of(page, size, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, orders))));
                    Logger.getLogger("UserController").info("User page retrieved successfully.");
                    res.sendRedirect(FilenameConstant.HOME_JSP); // For test purposes
                } catch (Exception e) {
                    Throwable rootCause = e;
                    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                        rootCause = rootCause.getCause();
                    }

                    req.getSession().setAttribute(AttributeNameConstant.ERROR, rootCause.getMessage());
                    Logger.getLogger("UserController").info("User page retrieval failed: " + rootCause.getMessage());
                    res.sendRedirect(FilenameConstant.HOME_JSP);
                }

            }
            default ->
                    throw new BadOperationException(String.format("In the %s method of %s the action is invalid.", "POST", "UserController"));
        }
    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        Logger.getLogger("UserController").info("GET request received.");

        HttpSession session = req.getSession();
        String action = session.getAttribute(AttributeNameConstant.ACTION).toString();

        if (action == null) {
            throw new BadOperationException("Action is required");
        }

        // Set the request in the AuthorizationHandler proxy
        InvocationHandler handler = Proxy.getInvocationHandler(service);
        if (handler instanceof AuthorizationHandler) {
            ((AuthorizationHandler) handler).setRequest(req);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @SneakyThrows
    protected void doPut(HttpServletRequest req, HttpServletResponse res) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @SneakyThrows
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public UserController() throws SQLException, IOException, ClassNotFoundException {
        Logger.getLogger("UserController").info("Initializing UserController...");

        Connection connection = CustomizedConnection.getInstance().getConnection();
        UserPA outputPort = UserPA.getInstance(connection);

        // Interceptors for the user service
        UserUC userUC = UserService.getInstance(outputPort);
        UserUC validationProxy = ValidatorInterceptor.createProxy(userUC, UserUC.class);
        UserUC transactionalProxy = TransactionalInterceptor.createProxy(validationProxy, connection, UserUC.class);
        this.service = AuthorizationHandler.createProxy(transactionalProxy, null, UserUC.class);
    }

    private final UserUC service;
}