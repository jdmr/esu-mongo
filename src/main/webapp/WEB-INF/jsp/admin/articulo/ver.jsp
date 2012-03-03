<%-- 
    Document   : ver
    Created on : Jan 27, 2012, 6:52:45 AM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="articulo.ver.label" /></title>
    </head>
    <body>
        <nav>
            <ul class="nav">
                <li><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                <li class="active"><a href="<c:url value='/admin/articulo' />"><s:message code="articulo.list.label" /></a></li>
            </ul>
        </nav>

        <h1><s:message code="articulo.ver.label" /></h1>

        <p class="well">
            <a class="btn btn-primary" href="<s:url value='/admin/articulo'/>"><i class="icon-list icon-white"></i> <s:message code='articulo.list.label' /></a>
            <a class="btn btn-primary" href="<s:url value='/admin/articulo/nuevo'/>"><i class="icon-plus icon-white"></i> <s:message code='articulo.nuevo.label' /></a>
        </p>
        <c:if test="${not empty message}">
            <div class="alert alert-block <c:choose><c:when test='${not empty messageStyle}'>${messageStyle}</c:when><c:otherwise>alert-success</c:otherwise></c:choose> fade in" role="status">
                <a class="close" data-dismiss="alert">×</a>
                <s:message code="${message}" arguments="${messageAttrs}" />
            </div>
        </c:if>

        <c:url var="eliminaUrl" value="/admin/articulo/elimina" />
        <form:form commandName="articulo" action="${eliminaUrl}" >
            <form:errors path="*" cssClass="alert alert-error" element="ul" />
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span4">
                    <h4><s:message code="nombre.label" /></h4>
                    <h3>${articulo.nombre}</h3>
                </div>
                <div class="span4">
                    <h4><s:message code="descripcion.label" /></h4>
                    <h3>${articulo.descripcion}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span4">
                    <h4><s:message code="fechaPublicacion.label" /></h4>
                    <h3>${articulo.fechaPublicacion}</h3>
                </div>
                <div class="span4">
                    <h4><s:message code="estatus.label" /></h4>
                    <h3>${articulo.estatus}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span4">
                    <h4><s:message code="ubicaciones.label" /></h4>
                    <h3>${articulo.ubicaciones}</h3>
                </div>
                <div class="span4">
                    <h4><s:message code="etiquetas.label" /></h4>
                    <h3>${articulo.etiquetas}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span4">
                    <h4><s:message code="autor.label" /></h4>
                    <h3>${articulo.autor}</h3>
                </div>
                <div class="span4">
                    <h4><s:message code="editor.label" /></h4>
                    <h3>${articulo.editor}</h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <h4><s:message code="contenido.label" /></h4>
                <h3>${articulo.contenido}</h3>
            </div>

            <p class="well">
                <a href="<c:url value='/admin/articulo/edita/${articulo.id}' />" class="btn btn-primary"><i class="icon-edit icon-white"></i> <s:message code="editar.button" /></a>
                <form:hidden path="id" />
                <button type="submit" name="elimina" class="btn btn-danger" onclick="return confirm('<s:message code="confirma.elimina.message" />');" ><i class="icon-remove icon-white"></i>&nbsp;<s:message code='eliminar.button'/></button>
            </p>
        </form:form>
    </body>
</html>
