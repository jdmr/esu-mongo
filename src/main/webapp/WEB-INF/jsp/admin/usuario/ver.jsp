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
        <title><s:message code="usuario.ver.label" /></title>
    </head>
    <body>
        <jsp:include page="../menu.jsp" >
            <jsp:param name="menu" value="usuario" />
        </jsp:include>

        <h1><s:message code="usuario.ver.label" /></h1>

        <p class="well">
            <a class="btn btn-primary" href="<s:url value='/admin/usuario'/>"><i class="icon-list icon-white"></i> <s:message code='usuario.list.label' /></a>
            <a class="btn btn-primary" href="<s:url value='/admin/usuario/nuevo'/>"><i class="icon-plus icon-white"></i> <s:message code='usuario.nuevo.label' /></a>
        </p>
        <c:if test="${not empty message}">
            <div class="alert alert-block <c:choose><c:when test='${not empty messageStyle}'>${messageStyle}</c:when><c:otherwise>alert-success</c:otherwise></c:choose> fade in" role="status">
                <a class="close" data-dismiss="alert">×</a>
                <s:message code="${message}" arguments="${messageAttrs}" />
            </div>
        </c:if>

        <c:url var="eliminaUrl" value="/admin/usuario/elimina" />
        <form:form commandName="usuario" action="${eliminaUrl}" >
            <form:errors path="*" cssClass="alert alert-error" element="ul" />
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="username.label" /></h4>
                    <h3>${usuario.username}</h3>
                </div>
            </div>
                
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="nombre.label" /></h4>
                    <h3>${usuario.nombre}</h3>
                </div>
            </div>
                
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="apellido.label" /></h4>
                    <h3>${usuario.apellido}</h3>
                </div>
            </div>
            
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="correo.label" /></h4>
                    <h3>${usuario.correo}</h3>
                </div>
            </div>
            
            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="fechaRegistro.label" /></h4>
                    <h3><fmt:formatDate value="${usuario.fechaRegistro}" pattern="yyyy/MM/dd HH:mm:ss Z" /></h3>
                </div>
            </div>

            <div class="row-fluid" style="padding-bottom: 10px;">
                <div class="span6">
                    <h4><s:message code="rol.list.label" /></h4>
                    <h3>
                        <c:forEach items="${roles}" var="rol">
                            <form:checkbox path="roles" value="${rol.authority}" disabled="true" /> <s:message code="${rol.authority}" />&nbsp;
                        </c:forEach>
                    </h3>
                </div>
            </div>
                    
            <p class="well">
                <a href="<c:url value='/admin/usuario/edita/${usuario.username}' />" class="btn btn-primary"><i class="icon-edit icon-white"></i> <s:message code="editar.button" /></a>
                <form:hidden path="username" />
                <button type="submit" name="elimina" class="btn btn-danger" onclick="return confirm('<s:message code="confirma.elimina.message" />');" ><i class="icon-remove icon-white"></i>&nbsp;<s:message code='eliminar.button'/></button>
            </p>
        </form:form>
    </body>
</html>
