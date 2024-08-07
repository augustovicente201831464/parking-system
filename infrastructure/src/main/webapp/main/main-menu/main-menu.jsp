<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
            <button class="btn btn-primary" href="${pageContext.request.contextPath}/register.jsp">Register</button>
        </div>

        <script src="<c:url value='/main/theme/theme-toggle.js' />"></script>
    </div>
</html>