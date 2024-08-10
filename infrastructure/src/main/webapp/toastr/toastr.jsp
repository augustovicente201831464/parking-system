<%@ page import="com.cunoc.edu.gt.constants.AttributeNameConstant" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

<%
    String errorMessage = (String) request.getSession().getAttribute(AttributeNameConstant.ERROR);
    if (errorMessage != null) {
%>
<script type="text/javascript">
    const errorMessage = '<%= errorMessage %>';
</script>
<script src="${pageContext.request.contextPath}/toastr/toastr.js"></script>
<%
        request.getSession().removeAttribute(AttributeNameConstant.ERROR);
    }
%>

<%
    String successMessage = (String) request.getSession().getAttribute(AttributeNameConstant.SUCCESS);
    if (successMessage != null) {
%>
<script type="text/javascript">
    const successMessage = '<%= successMessage %>';
</script>
<script src="${pageContext.request.contextPath}/toastr/toastr.js"></script>
<%
        request.getSession().removeAttribute(AttributeNameConstant.SUCCESS);
    }
%>