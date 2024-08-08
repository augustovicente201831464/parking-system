package com.cunoc.edu.gt.input.controller;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.constants.FilenameConstant;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Controller for the index page
 *
 * @Author: Augusto Vicente
 */
@WebServlet("/index")
public class IndexController extends HttpServlet {

    /**
     * Method to handle the get request
     *
     * @param req the request
     * @param resp the response
     */
    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FilenameConstant.LOGGING_PROPERTIES);
        LogManager.getLogManager().readConfiguration(stream);

        Logger.getLogger("IndexServlet").info("Initializing IndexServlet...");

        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(3600);

        Logger.getLogger("IndexServlet").info("IndexServlet initialized successfully.");

        UserResponse loginResponse = (UserResponse) session.getAttribute(AttributeNameConstant.LOGIN_RESPONSE);

        if (loginResponse == null) {
            req.getSession().setAttribute(AttributeNameConstant.IS_LOGIN_FORM, true);
            resp.sendRedirect(FilenameConstant.LOGIN_JSP);
        } else {
            resp.sendRedirect(FilenameConstant.HOME_JSP);
        }
    }
}