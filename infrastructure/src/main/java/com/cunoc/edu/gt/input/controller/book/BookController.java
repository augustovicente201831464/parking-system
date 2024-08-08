package com.cunoc.edu.gt.input.controller.book;

import com.cunoc.edu.gt.config.AuthorizationHandler;
import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.constants.FilenameConstant;
import com.cunoc.edu.gt.data.request.books.BookRequest;
import com.cunoc.edu.gt.data.response.auth.AccessResponse;
import com.cunoc.edu.gt.data.response.auth.RolResponse;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import com.cunoc.edu.gt.data.response.books.BookResponse;
import com.cunoc.edu.gt.enums.AccessName;
import com.cunoc.edu.gt.enums.RolName;
import com.cunoc.edu.gt.output.persistence.connection.CustomizedConnection;
import com.cunoc.edu.gt.service.AuditAttributeService;
import com.cunoc.edu.gt.service.book.BookService;
import com.cunoc.edu.gt.ucextends.books.BookUC;
import com.cunoc.edu.gt.utils.TransactionalInterceptor;
import com.cunoc.edu.gt.utils.ValidatorInterceptor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/libros")
public class BookController extends HttpServlet {

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getParameter(AttributeNameConstant.ACTION);
        if (action == null) {
            throw new IllegalArgumentException("Action is required");
        }

        switch (action) {
            case "enroll" -> {

                // Set the audit attribute service
                this.service.setAuditAttributeService(
                        new AuditAttributeService(
                                (UserResponse) req.getSession().getAttribute(AttributeNameConstant.LOGIN_RESPONSE)
                        )
                );

                // Set the request in the AuthorizationHandler proxy
                InvocationHandler handler = Proxy.getInvocationHandler(service);
                if (handler instanceof AuthorizationHandler) {
                    ((AuthorizationHandler) handler).setRequest(req);
                }

                UserResponse userResponse = (UserResponse) req.getSession().getAttribute(AttributeNameConstant.LOGIN_RESPONSE);
                userResponse.setRoleResponses(List.of(new RolResponse(1, RolName.ADMIN)));
                userResponse.setAccessResponses(List.of(new AccessResponse(1, AccessName.CREDIT)));

                req.getSession().setAttribute(AttributeNameConstant.LOGIN_RESPONSE, userResponse);

                Logger.getLogger("BookController").info("User login: " + userResponse);

                try {
                    BookResponse response = this.service.enrollBook(new BookRequest(1));
                    Logger.getLogger("BookController").info("Response: " + response);
                } catch (Exception e) {
                    Throwable rootCause = e;

                    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                        rootCause = rootCause.getCause();
                    }

                    req.getSession().setAttribute(AttributeNameConstant.ERROR, rootCause.getMessage());
                    Logger.getLogger("BookController").info("Book enrollment failed: " + rootCause.getMessage());
                    res.sendRedirect(FilenameConstant.HOME_JSP);
                }
            }
            case "update" -> throw new UnsupportedOperationException("Not implemented yet");
            default -> throw new UnsupportedOperationException("Action not supported");
        }
    }

    public BookController() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = CustomizedConnection.getInstance().getConnection();

        BookUC bookUC = BookService.getInstance();
        BookUC validationProxy = ValidatorInterceptor.createProxy(bookUC, BookUC.class);
        BookUC transactionalProxy = TransactionalInterceptor.createProxy(validationProxy, connection, BookUC.class);
        this.service = AuthorizationHandler.createProxy(transactionalProxy, null, BookUC.class);
    }

    private final BookUC service;
}