<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale">
    <title>Register</title>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="../register/register.css"/>
</head>

<body class="background">
<%
    request.getSession().setAttribute(AttributeNameConstant.SHOW_LOGIN_BTN, true);
    request.getSession().setAttribute(AttributeNameConstant.SHOW_REGISTER_BTN, false);
%>
<jsp:include page="../../main/main-menu/main-menu.jsp"/>

<div class="wrapper">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col register">
                <div class="d-flex justify-content-center">
                    <h3 class="text-center">Registrarse</h3>
                </div>

                <div id="form-register" class="form-user mt-2">
                    <jsp:include page="../../form/user-form/user-form.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../modal-dynamic/form-modal/form-modal.jsp"/>
<script src="<c:url value='/modal-dynamic/form-modal/form-modal.js' />"></script>
</body>
</html>