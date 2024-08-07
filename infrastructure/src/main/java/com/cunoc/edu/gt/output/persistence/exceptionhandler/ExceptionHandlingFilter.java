package com.cunoc.edu.gt.output.persistence.exceptionhandler;

import com.cunoc.edu.gt.exception.BadOperationException;
import com.cunoc.edu.gt.exception.InvalidDataException;
import com.cunoc.edu.gt.exception.NotFoundException;
import com.cunoc.edu.gt.exception.ValidationException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        Throwable rootCause = e;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }

        Logger.getLogger("ExceptionHandlingFilter").severe("Error: " + rootCause.getMessage());

        if (rootCause instanceof ValidationException) {
            HttpResponseUtil.sendErrorResponse(response, request,400, "Validation error: " + rootCause.getMessage());
        } else if (rootCause instanceof NotFoundException) {
            HttpResponseUtil.sendErrorResponse(response, request,404, "Resource not found: " + rootCause.getMessage());
        } else if (rootCause instanceof BadOperationException) {
            HttpResponseUtil.sendErrorResponse(response, request,400, "Bad operation: " + rootCause.getMessage());
        } else if(rootCause instanceof InvalidDataException) {
            HttpResponseUtil.sendErrorResponse(response, request,400, "Invalid data: " + rootCause.getMessage());
        } else {
            HttpResponseUtil.sendErrorResponse(response, request,500, "Internal server error: " + rootCause.getMessage());
        }
    }
}
