package com.cunoc.edu.gt.config;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.data.response.auth.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityService {

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserResponse userResponse = (UserResponse) request.getSession().getAttribute(AttributeNameConstant.LOGIN_RESPONSE);

        if (userResponse == null) {
            return false;
        }

        return userResponse.getRolResponses()
                .stream()
                .anyMatch(rolResponse -> rolResponse.getRolName().toString().equals(role));
    }

    public static boolean hasAccess(HttpServletRequest request, String access) {
        UserResponse userResponse = (UserResponse) request.getSession().getAttribute(AttributeNameConstant.LOGIN_RESPONSE);

        if (userResponse == null) {
            return false;
        }

        return userResponse.getAccessResponses()
                .stream()
                .anyMatch(accessResponse -> accessResponse.getAccessName().toString().equals(access));
    }
}
