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
        <jsp:include page="../menu.jsp" >
            <jsp:param name="menu" value="usuario" />
        </jsp:include>

        <h1><s:message code="usuario.list.label" /></h1>
        <hr/>

        <form name="filtraLista" class="form-search" method="post" action="<c:url value='/admin/usuario' />">
            <input type="hidden" name="pagina" id="pagina" value="${pagina}" />
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
                        <th><s:message code="correo.label" /></th>
                        <th><s:message code="rol.list.label" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${usuarios}" var="usuario" varStatus="status">
                        <tr>
                            <td><a href="<c:url value='/admin/usuario/ver/${usuario.username}' />">${usuario.username}</a></td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.apellido}</td>
                            <td>${usuario.correo}</td>
                            <td>
                                <c:forEach items="${usuario.roles}" var="rol">
                                    <label class="label label-info" style="width:100px;"><i class="icon-user icon-white"></i> <s:message code="${rol.authority}" /></label>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="span12">
                    <div class="pagination">
                        <ul>
                            <li class="disabled"><a href="#"><s:message code="mensaje.paginacion" arguments="${paginacion}" /></a></li>
                            <c:forEach items="${paginas}" var="paginaId">
                                <li <c:if test="${pagina == paginaId}" >class="active"</c:if>>
                                    <a href="javascript:buscaPagina(${paginaId});" >${paginaId}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </form>
        <content>
            <script src="<c:url value='/js/lista.js' />"></script>
        </content>
    </body>
</html>
