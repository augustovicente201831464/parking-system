<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale">
    <title>Authentication</title>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="../login/login.css"/>
</head>

<body class="background">
<%
    request.getSession().setAttribute(AttributeNameConstant.SHOW_LOGIN_BTN, false);
    request.getSession().setAttribute(AttributeNameConstant.SHOW_REGISTER_BTN, true);
    //request.getSession().removeAttribute(AttributeNameConstant.ERROR);
%>
<jsp:include page="../../main/main-menu/main-menu.jsp"/>

<div class="wrapper">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col login">
                <div class="card">
                    <div class="card-body">
                        <h3 class="card-title text-center">Iniciar sesión</h3>
                        <form id="form-login" class="form-group"
                              action="${pageContext.request.contextPath}/usuario?accion=login" method="POST">

                            <div class="mb-3">
                                <label for="<%=AttributeNameConstant.USERNAME%>" class="form-label">Username</label>
                                <input type="text" class="form-control" id="<%=AttributeNameConstant.USERNAME%>"
                                       name="<%=AttributeNameConstant.USERNAME%>"></div>
                            <div class="mb-3">
                                <label for="<%=AttributeNameConstant.PASSWORD%>" class="form-label">Password</label>
                                <input type="password" class="form-control" id="<%=AttributeNameConstant.PASSWORD%>"
                                       name="<%=AttributeNameConstant.PASSWORD%>">
                            </div>

                            <% String errorMessage = (String) request.getSession().getAttribute(AttributeNameConstant.ERROR);
                                if (errorMessage != null) { %>
                            <div class="error">
                                <p><%= errorMessage %>
                                </p>
                            </div>
                            <% } %>

                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary">Iniciar sesión</button>
                            </div>
                        </form>
                        <div class="text-center mt-3">
                            <p class="card-text">¿Se te olvidó tu contraseña?</p>
                            <a class="btn-link mt-1" href="${pageContext.request.contextPath}/auth/forgot-password/forgot-password.jsp">
                                Recuperar contraseña
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../modal-dynamic/form-modal/form-modal.jsp"/>
<script src="<c:url value='/modal-dynamic/form-modal/form-modal.js' />"></script>
</body>
</html>