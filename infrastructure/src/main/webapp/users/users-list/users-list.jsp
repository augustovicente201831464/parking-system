<%@ page import="com.cunoc.edu.gt.data.response.auth.UserResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<%@ page import="com.cunoc.edu.gt.data.pagination.Page" %>
<%@ page import="com.cunoc.edu.gt.data.pagination.util.Sort" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:include page="../../toastr/toastr.jsp"/>

<div class="grid-container">
    <div class="grid-item">
        <div class="d-flex justify-content-center">
            <div class="title-container mt-3">
                LISTA DE USUARIOS
            </div>
        </div>
    </div>

    <div class="grid-item">

        <div class="page-request">

            <%
                Page<UserResponse> pageOfUsers = (Page<UserResponse>) request.getSession().getAttribute(AttributeNameConstant.PAGE_USER);
                List<UserResponse> users = pageOfUsers.getContent();
            %>
            <%
                int currentPage = pageOfUsers.getNumber() + 1;
                int totalPages = pageOfUsers.getTotalPages();

                int range = 3;
                int startPage, endPage;

                if (totalPages <= range) {
                    startPage = 1;
                    endPage = totalPages;
                } else {
                    startPage = Math.max(1, currentPage - (range / 2));
                    endPage = Math.min(totalPages, currentPage + (range / 2));

                    if (currentPage - (range / 2) < 1) {
                        endPage = Math.min(totalPages, range);
                    }
                    if (currentPage + (range / 2) > totalPages) {
                        startPage = Math.max(1, totalPages - range + 1);
                    }
                } %>

            <label for="session-total" style="display: none;">Current page</label>
            <input type="hidden" id="session-page" value="<%= currentPage %>"/>
            <label for="session-size">Size</label>
            <input id="session-size" value="<%= pageOfUsers.getSize() %>" class="form-control"/>
            <label for="session-sort">Order</label>
            <select id="session-sort" class="form-control" name="sort">
                <option value="codigo" <%= "codigo".equals(pageOfUsers.getSort().getOrder()) ? "selected" : "" %>>ID</option>
                <option value="nombre" <%= "nombre".equals(pageOfUsers.getSort().getOrder()) ? "selected" : "" %>>Nombre</option>
                <option value="apellido" <%= "apellido".equals(pageOfUsers.getSort().getOrder()) ? "selected" : "" %>>Apellido</option>
            </select>
            <%
                String direction = "false";
                if (Sort.Direction.ASC == pageOfUsers.getSort().getDirection()) {
                    direction = "true";
                }
            %>
            <label for="session-direction">Direction</label>
            <select id="session-direction" class="form-control">
                <option value="true" <%= (direction.equals("true")) ? "selected" : "" %>>ASC</option>
                <option value="false" <%= (direction.equals("false")) ? "selected" : "" %>>DESC</option>
            </select>
        </div>

        <div class="table-container mt-3">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                    <th>Usuario</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>


                    <%if (users != null) {
                        for (UserResponse user : users) { %>
                <tr>
                    <td><%= user.getName() %>
                    </td>
                    <td><%= user.getLastname() %>
                    </td>
                    <td><%= user.getEmail() %>
                    </td>
                    <td><%= user.getUsername() %>
                    </td>
                    <td></td>
                </tr>
                    <% }
                    } else { %>
                <tr>
                    <td colspan="5">No hay usuarios disponibles.</td>
                </tr>
                    <% } %>
            </table>
        </div>

        <!-- Pagination Controls -->
        <div class="d-flex justify-content-center mt-2">
            <nav aria-label="Page navigation example">
                <ul class="pagination">

                    <%if (currentPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="loadUserPage(<%= currentPage - 1 %>)">Anterior</a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#">Anterior</a>
                    </li>
                    <% }
                        if (startPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="loadUserPage(1)">1</a>
                    </li>
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                    <% }
                        for (int i = startPage; i <= endPage; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="#" onclick="loadUserPage(<%= i %>)"><%= i %>
                        </a>
                    </li>
                    <% }
                        if (endPage < totalPages) { %>
                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="loadUserPage(<%= totalPages %>)"><%= totalPages %>
                        </a>
                    </li>
                    <% }
                        if (currentPage < totalPages) { %>
                    <li class="page-item">
                        <a class="page-link" href="#" onclick="loadUserPage(<%= currentPage + 1 %>)">Siguiente</a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#">Siguiente</a>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </div>

    <div class="grid-item">
        <div class="footer mt-3">
            <div class="text-muted">
                © 2024 - Kojstar Innovations
            </div>
        </div>
    </div>
</div>

<script>
    function loadUserPage(currentPage) {
        const pageNumber = currentPage || document.getElementById('session-page').value;
        const size = document.getElementById('session-size').value;
        const sort = document.getElementById('session-sort').value;
        const direction = document.getElementById('session-direction').value;

        console.log('Page number:', pageNumber);
        console.log('Size:', size);
        console.log('Sort:', sort);
        console.log('Direction:', direction);

        $.ajax({
            url: `${pageContext.request.contextPath}/usuario`,
            method: 'POST',
            data: {
                accion: 'get-page',
                page_number: pageNumber,
                size: size,
                sort: sort,
                direction: direction
            },
            success: function (data) {
                $('#dynamic-content').html(data);
            },
            error: function (xhr, status, error) {
                console.error('Error loading user page:', error);
            }
        });
    }
</script>

