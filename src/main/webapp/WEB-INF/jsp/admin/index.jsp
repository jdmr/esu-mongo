<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="admin.label" /></title>
    </head>
    <body>
        <nav>
            <ul class="nav">
                <li><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li class="active"><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                <li><a href="<c:url value='/admin/articulo' />"><s:message code="articulo.list.label" /></a></li>
                <li><a href="<c:url value='/admin/usuario' />"><s:message code="usuario.list.label" /></a></li>
            </ul>
        </nav>
        <div class="row">
            <div class="span8"><h1><s:message code="admin.label" /></h1></div>
        </div>
    </body>
</html>
