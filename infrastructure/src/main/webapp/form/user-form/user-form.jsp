<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<form class = "form-group">
    <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username">
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password">
    </div>

    <c:if test="${!empty(message)}">
        <div id="error-alert" class="alert alert-danger alert-dismissible fade show" role="alert" style="display: none;">
            <strong>¡Login fallido!</strong> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="d-flex justify-content-center">
        <button class="btn btn-primary ">Iniciar sesión</button>
    </div>
</form>