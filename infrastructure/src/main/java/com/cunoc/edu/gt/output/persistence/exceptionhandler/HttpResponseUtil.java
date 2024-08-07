package com.cunoc.edu.gt.output.persistence.exceptionhandler;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;


public class HttpResponseUtil {

    public static void sendErrorResponse(HttpServletResponse response, HttpServletRequest request, Integer statusCode, String message) {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        Logger.getLogger("HttpResponseUtil").info("Setting error message: " + message);
        request.getSession().setAttribute(AttributeNameConstant.ERROR, message);
    }
}
