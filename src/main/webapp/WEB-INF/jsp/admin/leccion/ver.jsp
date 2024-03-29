<%-- 
    Document   : ver
    Created on : Jan 27, 2012, 6:52:45 AM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="leccion.ver.label" /></title>
    </head>
    <body>
        <jsp:include page="../menu.jsp" >
            <jsp:param name="menu" value="leccion" />
        </jsp:include>

        <h1><s:message code="leccion.ver.label" /></h1>

        <p class="well">
            <a class="btn btn-primary" href="<s:url value='/admin/leccion'/>"><i class="icon-list icon-white"></i> <s:message code='leccion.list.label' /></a>
            <a class="btn btn-primary" href="<s:url value='/admin/leccion/nuevo'/>"><i class="icon-plus icon-white"></i> <s:message code='leccion.nuevo.label' /></a>
            <a href="<c:url value='/admin/leccion/edita/${leccion.id}' />" class="btn btn-primary"><i class="icon-edit icon-white"></i> <s:message code="editar.button" /></a>
        </p>
        <c:if test="${not empty message}">
            <div class="alert alert-block <c:choose><c:when test='${not empty messageStyle}'>${messageStyle}</c:when><c:otherwise>alert-success</c:otherwise></c:choose> fade in" role="status">
                <a class="close" data-dismiss="alert">×</a>
                <s:message code="${message}" arguments="${messageAttrs}" />
            </div>
        </c:if>

        <c:url var="eliminaUrl" value="/admin/leccion/elimina" />
        <form:form commandName="leccion" action="${eliminaUrl}" >
            <form:errors path="*" cssClass="alert alert-error" element="ul" />
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="nombre.label" /></h4>
                    <h3>${leccion.nombre}</h3>
                </div>
                <div class="span6">
                    <h4><s:message code="estatus.label" /></h4>
                    <h3>${leccion.estatus}</h3>
                </div>
            </div>
            
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span12">
                    <h4><s:message code="descripcion.label" /></h4>
                    <h3>${leccion.descripcion}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="fechaPublicacion.label" /></h4>
                    <h3><fmt:formatDate value="${leccion.fechaPublicacion}" pattern="yyyy/MM/dd" /></h3>
                </div>
                <div class="span6">
                    <h4><s:message code="creador.label" /></h4>
                    <h3>${leccion.creador}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="ubicaciones.label" /></h4>
                    <c:forEach items="${leccion.ubicaciones}" var="carpeta">
                        <label class="label label-info"><i class="icon-tag icon-white"></i>&nbsp;${carpeta}</label>
                    </c:forEach>
                </div>
                <div class="span6">
                    <h4><s:message code="etiquetas.label" /></h4>
                    <c:forEach items="${leccion.etiquetas}" var="etiqueta">
                        <label class="label label-info"><i class="icon-tag icon-white"></i>&nbsp;${etiqueta}</label>
                    </c:forEach>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="autor.label" /></h4>
                    <h3>${leccion.autor}</h3>
                </div>
                <div class="span6">
                    <h4><s:message code="editor.label" /></h4>
                    <h3>${leccion.editor}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="fechaCreacion.label" /></h4>
                    <h3>${leccion.fechaCreacion}</h3>
                </div>
                <div class="span6">
                    <h4><s:message code="fechaModificacion.label" /></h4>
                    <h3>${leccion.fechaModificacion}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <h4><s:message code="contenido.label" /></h4>
                <div>${leccion.contenido}</div>
            </div>

            <p class="well">
                <a href="<c:url value='/admin/leccion/edita/${leccion.id}' />" class="btn btn-primary"><i class="icon-edit icon-white"></i> <s:message code="editar.button" /></a>
                <form:hidden path="id" />
                <button type="submit" name="elimina" class="btn btn-danger" onclick="return confirm('<s:message code="confirma.elimina.message" />');" ><i class="icon-remove icon-white"></i>&nbsp;<s:message code='eliminar.button'/></button>
            </p>
        </form:form>
    </body>
</html>
