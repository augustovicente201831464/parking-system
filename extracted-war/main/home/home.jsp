<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
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
    <title>Home</title>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="../home/home.css"/>
</head>

<body class="background">
<c:choose>
    <c:when test="${login_response == null}">
        <c:redirect url="/auth/login/login.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../../main/main-menu/main-menu.jsp"/>

        <div class="wrapper home">
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col home">
                        <div class="d-flex justify-content-center">
                            <h3 class="text-center">Bienvenido: <span
                                    class="span-user">${login_response.username}</span></h3>

                        </div>
                        <div class="d-flex justify-content-center mt-2">
                            <h3 class="text-center">Estado: <span class="span-user">${login_response.status}</span></h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../../modal-dynamic/form-modal/form-modal.jsp"/>
<script src="<c:url value='/modal-dynamic/form-modal/form-modal.js' />"></script>
</body>
</html>