<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="inicio.label" /></title>
    </head>
    <body>
        <nav>
            <ul class="nav">
                <li><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li><a href="<c:url value='/estudia' />"><s:message code="estudia.label" /></a></li>
                <li><a href="<s:url value='/profundiza'/>" ><s:message code="profundiza.label" /></a></li>
                <li class="active"><a href="<s:url value='/comparte'/>" ><s:message code="comparte.label" /></a></li>
                <li><a href="<s:url value='/foros'/>" ><s:message code="foros.label" /></a></li>
                <li><a href="<s:url value='/conocenos'/>" ><s:message code="conocenos.label" /></a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="span8">
                <h1>${articulo.nombre}</h1>
                <h3>${articulo.autor.nombreCompleto}</h3>
                <div>${articulo.contenido}</div>
            </div>
            <div class="span4">
                <p>Foto del autor</p>
            </div>
        </div>
    </body>
</html>
