<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<nav id="left-side-menu" class="content">
    <div class="label">
        <div class="title-menu mt-3">
            USUARIOS
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/index">
        <i class="fa fa-home fa-menu"></i>
        <span class="nav-text">HOME</span>
    </a>

    <a href="#" onclick="loadUserPageFromMenu(); return false;">
        <i class="fa fa-users fa-menu"></i>
        <span class="nav-text">USUARIOS</span>
    </a>

    <a href="#"
       onclick="loadDynamicJSP('${pageContext.request.contextPath}/users/users-report/users-report.jsp'); return false;">
        <i class="fa fa-file-code fa-menu"></i>
        <span class="nav-text">REPORTES</span>
    </a>

    <script>
        function loadUserPageFromMenu(pageNumber = 1, size = 15, sort = 'codigo', direction = 'true') {
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
                    if (xhr.status === 403) {
                        window.location.href = `${pageContext.request.contextPath}/main/home/home.jsp`;
                    } else {
                        console.error('Error loading user page:', error);
                    }
                }
            });
        }
    </script>
</nav>