<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="articulo.list.label" /></title>
    </head>
    <body>
        <jsp:include page="../menu.jsp" >
            <jsp:param name="menu" value="articulo" />
        </jsp:include>

        <h1><s:message code="articulo.list.label" /></h1>
        <hr/>

        <form name="filtraLista" class="form-search" method="post" action="<c:url value='/admin/articulo' />">
            <input type="hidden" name="pagina" id="pagina" value="${pagina}" />
            <p class="well">
                <a class="btn btn-primary" href="<s:url value='/admin/articulo/nuevo'/>"><i class="icon-file icon-white"></i> <s:message code='articulo.nuevo.label' /></a>
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
                <s:bind path="articulo.*">
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
                        <th><s:message code="nombre.label" /></th>
                        <th><s:message code="descripcion.label" /></th>
                        <th><s:message code="fechaPublicacion.label" /></th>
                        <th><s:message code="autor.label" /></th>
                        <th><s:message code="editor.label" /></th>
                        <th><s:message code="estatus.label" /></th>
                        <th><s:message code="ubicaciones.label" /></th>
                        <th><s:message code="etiquetas.label" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${articulos}" var="articulo" varStatus="status">
                        <tr>
                            <td><a href="<c:url value='/admin/articulo/ver/${articulo.id}' />">${articulo.nombre}</a></td>
                            <td>${articulo.descripcion}</td>
                            <td><fmt:formatDate value="${articulo.fechaPublicacion}" pattern="yyyy/MM/dd" /></td>
                            <td>${articulo.autor}</td>
                            <td>${articulo.editor}</td>
                            <td>${articulo.estatus}</td>
                            <td>
                                <c:forEach items="${articulo.ubicaciones}" var="carpeta">
                                    <label class="label label-info"><i class="icon-tag icon-white"></i> ${carpeta}</label>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${articulo.etiquetas}" var="etiqueta">
                                    <label class="label label-info"><i class="icon-tag icon-white"></i> ${etiqueta}</label>
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
