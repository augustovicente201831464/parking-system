<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>

<form class="form" id="user-form" action="${pageContext.request.contextPath}/usuario?accion=register" method="POST">

    <div class="form-group">

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.NAME%>" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="<%=AttributeNameConstant.NAME%>" name="<%=AttributeNameConstant.NAME%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.LAST_NAME%>" class="form-label">Apellido</label>
            <input type="text" class="form-control" id="<%=AttributeNameConstant.LAST_NAME%>" name="<%=AttributeNameConstant.LAST_NAME%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.EMAIL%>" class="form-label">Correo Electrónico</label>
            <input type="email" class="form-control" id="<%=AttributeNameConstant.EMAIL%>" name="<%=AttributeNameConstant.EMAIL%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.USERNAME%>" class="form-label">Username</label>
            <input type="text" class="form-control" id="<%=AttributeNameConstant.USERNAME%>" name="<%=AttributeNameConstant.USERNAME%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.PASSWORD%>" class="form-label">Password</label>
            <input type="password" class="form-control" name="<%=AttributeNameConstant.PASSWORD%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.CONFIRM_PASSWORD%>" class="form-label">Confirmar Password</label>
            <input type="password" class="form-control" name="<%=AttributeNameConstant.CONFIRM_PASSWORD%>">
        </div>

        <div class="mt-1">
            <label for="<%=AttributeNameConstant.PHONE%>" class="form-label">Teléfono</label>
            <input type="text" class="form-control" id="<%=AttributeNameConstant.PHONE%>" name="<%=AttributeNameConstant.PHONE%>">
        </div>
    </div>

    <div class="d-flex justify-content-center mt-2">
        <button type="submit" class="btn-submit">Registrarse</button>
    </div>
</form>