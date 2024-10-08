<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@ page import="com.cunoc.edu.gt.constants.FilenameConstant" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    request.getSession().setAttribute(AttributeNameConstant.SHOW_LOGIN_BTN, false);
    request.getSession().setAttribute(AttributeNameConstant.SHOW_REGISTER_BTN, false);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale">
    <title>Usuarios</title>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="../main-users/main-users.css">
    <jsp:include page="/toastr/toastr.jsp"/>
</head>

<%
    if (request.getSession().getAttribute(AttributeNameConstant.LOGIN_RESPONSE) == null) {
        request.getSession().setAttribute(AttributeNameConstant.ERROR, "No se ha iniciado sesión.");
        response.sendRedirect(String.format("%s/%s", request.getContextPath(), FilenameConstant.LOGIN_JSP));
    }
%>

<body class="background user">
<jsp:include page="../../main/main-menu/main-menu.jsp"/>

<div class="wrapper">
    <div class="left--side-menu">
        <jsp:include page="../../users/users-menu/users-menu.jsp"/>
    </div>

    <div class="content-comp" id="dynamic-content">
        <!-- jsp dinamico -->
    </div>
</div>

<jsp:include page="../../modal-dynamic/form-modal/form-modal.jsp"/>
<script src="<c:url value='/modal-dynamic/form-modal/form-modal.js' />"></script>
<script src="<c:url value='/users/users-menu/users-menu.js' />"></script>
</body>
</html>