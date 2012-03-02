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
                <h1><a href='<c:url value="/estudia" />'>Daniel 2 y la Providencia Divina en la historia</a></h1>
                <h3>Martes 28/02/2012</h3>
                <p>Pero los humanos tenemos libertad de elección. Dios nos hizo así. Como tenemos la capacidad de amar, necesitábamos la libertad de elegir, porque el amor forzado no es amor. Pero el poder de Dios es tan grande que, aun con la libertad humana de elegir, él sabe el perfectamente el futuro, más allá de las elecciones libres que hagamos.</p>
            </div>
        </div>
    </body>
</html>
