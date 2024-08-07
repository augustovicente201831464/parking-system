package com.cunoc.edu.gt.input.handling.auth;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.data.request.auth.UserLoginRequest;
import com.cunoc.edu.gt.data.request.auth.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class UserControllerHandling {

    @SneakyThrows
    public static UserRequest userRegisterHandling(HttpServletRequest req, HttpServletResponse res) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Handles the user login
     *
     * @param req the request
     * @param res the response
     * @return the user login request
     */
    @SneakyThrows
    public static UserLoginRequest userLoginHandling(HttpServletRequest req, HttpServletResponse res) {
        return new UserLoginRequest(
                req.getParameter(AttributeNameConstant.USERNAME),
                req.getParameter(AttributeNameConstant.PASSWORD)
        );
    }
}
