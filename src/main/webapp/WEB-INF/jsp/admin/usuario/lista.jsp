<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="usuario.list.label" /></title>
    </head>
    <body>
        <nav>
            <ul class="nav">
                <li><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                <li><a href="<c:url value='/admin/articulo' />"><s:message code="articulo.list.label" /></a></li>
                <li class="active"><a href="<c:url value='/admin/usuario' />"><s:message code="usuario.list.label" /></a></li>
            </ul>
        </nav>

        <h1><s:message code="usuario.list.label" /></h1>
        <hr/>

        <form name="filtraUsuarios" class="form-search" method="post" action="<c:url value='/admin/usuario' />">
            <p class="well">
                <a class="btn btn-primary" href="<s:url value='/admin/usuario/nuevo'/>"><i class="icon-user icon-white"></i> <s:message code='usuario.nuevo.label' /></a>
                <input name="filtro" type="text" class="input-medium search-query" value="${param.filtro}">
                <button type="submit" class="btn"><s:message code="buscar.label" /></button>
            </p>
            <c:if test="${not empty message}">
                <div class="alert alert-block <c:choose><c:when test='${not empty messageStyle}'>${messageStyle}</c:when><c:otherwise>alert-success</c:otherwise></c:choose> fade in" role="status">
                    <a class="close" data-dismiss="alert">×</a>
                    <s:message code="${message}" arguments="${messageAttrs}" />
                </div>
            </c:if>
            <c:if test="${usuario != null}">
                <s:bind path="usuario.*">
                    <c:if test="${not empty status.errorMessages}">
                    <div class="alert alert-block alert-error fade in" role="status">
                        <a class="close" data-dismiss="alert">×</a>
                        <c:forEach var="error" items="${status.errorMessages}">
                            <c:out value="${error}" escapeXml="false"/><br />
                        </c:forEach>
                    </div>
                    </c:if>
                </s:bind>
            </c:if>
            
            <table id="lista" class="table table-striped">
                <thead>
                    <tr>
                        <th><s:message code="username.label" /></th>
                        <th><s:message code="nombre.label" /></th>
                        <th><s:message code="apellido.label" /></th>
                        <th><s:message code="rol.list.label" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${usuarios}" var="usuario" varStatus="status">
                        <tr>
                            <td><a href="<c:url value='/admin/usuario/ver/${usuario.username}' />">${usuario.username}</a></td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.apellido}</td>
                            <td>
                                <c:forEach items="${usuario.roles}" var="rol">
                                    <label class="label label-info" style="width:100px;"><i class="icon-user icon-white"></i> <s:message code="${rol.authority}" /></label>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>        
        <content>
            <script src="<c:url value='/js/lista.js' />"></script>
        </content>
    </body>
</html>
