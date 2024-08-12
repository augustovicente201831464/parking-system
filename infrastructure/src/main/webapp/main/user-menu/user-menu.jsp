<%@ page import="com.cunoc.edu.gt.data.response.auth.UserResponse" %>
<%@ page import="com.cunoc.edu.gt.enums.RolName" %>
<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@ page import="com.cunoc.edu.gt.data.validator.UserHelper" %>
<%@ page import="com.cunoc.edu.gt.enums.AccessName" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="grid-layout user-menu">

    <%
        UserResponse userLogin = (UserResponse) session.getAttribute(AttributeNameConstant.LOGIN_RESPONSE);
        boolean displayableUserItem = UserHelper.containAccessOrRole(AccessName.USER, RolName.ADMIN, userLogin);
        boolean displayableVehicleItem = UserHelper.containAccessOrRole(AccessName.VEHICLE, RolName.ADMIN, userLogin);
        boolean displayableCustomerItem = UserHelper.containAccessOrRole(AccessName.CUSTOMER, RolName.ADMIN, userLogin);
    %>

    <% if (displayableUserItem) { %>
    <div class="grid-item"
         onclick="location.href='${pageContext.request.contextPath}/main/main-users/main-users.jsp';">
        <i class="fa fa-users"></i>
        <div class="a-index">Usuarios</div>
    </div>
    <% } %>

    <% if (displayableVehicleItem) { %>
    <div class="grid-item"
         onclick="location.href='${pageContext.request.contextPath}/main/main-vehicles/main-vehicles.jsp';">
        <i class="fa fa-car"></i>
        <div class="a-index">Vehículos</div>
    </div>
    <% } %>

    <% if (displayableCustomerItem) { %>
    <div class="grid-item"
         onclick="location.href='${pageContext.request.contextPath}/main/main-customers/main-customers.jsp';">
        <i class="fa fa-user-tag"></i>
        <div class="a-index">Clientes</div>
    </div>
    <% } %>
</div>