<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.cunoc.edu.gt.data.response.auth.UserResponse" %>
<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <div class = "navbar navbar-expand-lg navbar-dark bg-dark main">
        <div class = "d-flex">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="logo" width="50" height="50">
            <a href="${pageContext.request.contextPath}/index" class = "a-index">Parking system</a>
        </div>

        <div class = "right-buttons">
            <button class="btn btn-primary" onclick="openModal('../../main/theme/theme-toggle.jsp')">Setting</button>

            <c:if test="${show_login_btn}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/auth/login/login.jsp">Login</a>
            </c:if>

            <c:if test="${show_register_btn}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/auth/register/register.jsp">Register</a>
            </c:if>

            <c:if test="${(login_response != null)}">
                <button class="btn btn-primary logout" onclick="logout()">Logout</button>
            </c:if>

        </div>

        <form id="logout-form" action="${pageContext.request.contextPath}/usuario?accion=logout" method="POST" style="display:none;">
        </form>

        <script src="<c:url value='/main/main-menu/main-menu.js' />"></script>
        <script src="<c:url value='/main/theme/theme-toggle.js' />"></script>
    </div>
</html>