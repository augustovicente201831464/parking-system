package com.cunoc.edu.gt.input.controller.auth;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.constants.FilenameConstant;
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

        switch (action) {
            case "login" -> {

                session.removeAttribute(AttributeNameConstant.LOGIN_RESPONSE);
                session.removeAttribute(AttributeNameConstant.ERROR);

                if (session.getAttribute(AttributeNameConstant.LOGIN_RESPONSE) != null) {
                    Logger.getLogger("UserController").info("User is already logged in, redirecting to home.");
                    res.sendRedirect(FilenameConstant.HOME_JSP);
                    return;
                }

                UserLoginRequest loginRequest = UserControllerHandling.userLoginHandling(req, res);

                Logger.getLogger("UserController").info("User login request: " + loginRequest);

                session.removeAttribute(AttributeNameConstant.LOGIN_RESPONSE);

                try{
                    session.setAttribute(AttributeNameConstant.LOGIN_RESPONSE, service.login(loginRequest));
                    res.sendRedirect(FilenameConstant.HOME_JSP);
                    Logger.getLogger("UserController").info("User logged in successfully.");
                }catch (Exception e){
                    Throwable rootCause = e;
                    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                        rootCause = rootCause.getCause();
                    }

                    req.getSession().setAttribute(AttributeNameConstant.ERROR, rootCause.getMessage());
                    Logger.getLogger("UserController").info("User login failed: " + rootCause.getMessage());
                    res.sendRedirect(FilenameConstant.LOGIN_JSP);
                }
            }
            case "logout" -> throw new UnsupportedOperationException("Not supported yet.");
            case "register" -> {
                UserRequest userRequest = UserControllerHandling.userRegisterHandling(req, res);

                session.removeAttribute(AttributeNameConstant.USER_RESPONSE);
                session.setAttribute(AttributeNameConstant.USER_RESPONSE, service.register(userRequest));

                Logger.getLogger("UserController").info("User registered successfully.");
            }
            default -> throw new BadOperationException(String.format("In the %s method of %s the action is invalid.", "POST", "UserController"));
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
        this.service = TransactionalInterceptor.createProxy(validationProxy, connection, UserUC.class);
    }

    private final UserUC service;
}