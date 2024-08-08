package com.cunoc.edu.gt.config;

import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationEvaluator {

    private static final Pattern ROLE_PATTERN = Pattern.compile("hasRole\\s*\\(\\s*'(.*?)'\\s*\\)");
    private static final Pattern ACCESS_PATTERN = Pattern.compile("hasAccess\\s*\\(\\s*'(.*?)'\\s*\\)");

    private final HttpServletRequest request;

    public AuthorizationEvaluator(HttpServletRequest request) {
        this.request = request;
    }

    private boolean evaluateRole(String role) {
        Logger.getLogger("AuthorizationEvaluator").info("Evaluating role: " + role);
        return SecurityService.hasRole(request, role);
    }

    private boolean evaluateAccess(String access) {
        Logger.getLogger("AuthorizationEvaluator").info("Evaluating access: " + access);
        return SecurityService.hasAccess(request, access);
    }

    public boolean evaluate(String expression) {
        expression = expression.trim();

        return evaluateExpression(expression);
    }

    private boolean evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s+", " ");

        if (expression.contains(" or ")) {
            String[] parts = expression.split(" or ", 2);
            return evaluateExpression(parts[0]) || evaluateExpression(parts[1]);
        }

        if (expression.contains(" and ")) {
            String[] parts = expression.split(" and ", 2);
            return evaluateExpression(parts[0]) && evaluateExpression(parts[1]);
        }

        if (expression.startsWith("hasRole")) {
            Matcher matcher = ROLE_PATTERN.matcher(expression);
            if (matcher.find()) {
                return evaluateRole(matcher.group(1));
            }
        } else if (expression.startsWith("hasAccess")) {
            Matcher matcher = ACCESS_PATTERN.matcher(expression);
            if (matcher.find()) {
                return evaluateAccess(matcher.group(1));
            }
        }

        Logger.getLogger("AuthorizationEvaluator").warning("Expression not recognized: " + expression);
        // Default to false if expression is unrecognized
        return false;
    }
}