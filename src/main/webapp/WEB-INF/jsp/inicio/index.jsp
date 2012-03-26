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
                <li class="active"><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li><a href="<c:url value='/estudia' />"><s:message code="estudia.label" /></a></li>
                <li><a href="<s:url value='/profundiza'/>" ><s:message code="profundiza.label" /></a></li>
                <li><a href="<s:url value='/comparte'/>" ><s:message code="comparte.label" /></a></li>
                <li><a href="<s:url value='/foros'/>" ><s:message code="foros.label" /></a></li>
                <li><a href="<s:url value='/conocenos'/>" ><s:message code="conocenos.label" /></a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="span8"><img src="<c:url value='/images/home.jpg' />" /></div>
            <div class="span4">
                <h1><a href='<c:url value="/estudia" />'>${leccion.nombre}</a></h1>
                <h3>Martes 28/02/2012</h3>
                <p>${leccion.descripcion}</p>
            </div>
        </div>
            <div class="row">
                <div class="span6">
                    <c:forEach items="${articulosDialoga}" var="articulo">
                        <h1><a href="<c:url value='/dialoga/ver${articulo.url}' />">${articulo.nombre}</a></h1>
                        <div>${articulo.descripcion}</div>
                    </c:forEach>
                </div>
                <div class="span6">
                    <c:forEach items="${articulosComunica}" var="articulo">
                        <h1><a href="<c:url value='/comunica/ver${articulo.url}' />">${articulo.nombre}</a></h1>
                        <div>${articulo.descripcion}</div>
                    </c:forEach>
                </div>
            </div>
    </body>
</html>
