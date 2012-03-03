<%-- 
    Document   : nuevo
    Created on : Jan 27, 2012, 10:37:52 AM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="articulo.nuevo.label" /></title>
    </head>
    <body>
        <nav>
            <ul class="nav">
                <li><a href="<c:url value='/inicio' />"><s:message code="inicio.label" /></a></li>
                <li><a href="<s:url value='/admin'/>" ><s:message code="admin.label" /></a></li>
                <li class="active"><a href="<c:url value='/admin/articulo' />"><s:message code="articulo.list.label" /></a></li>
            </ul>
        </nav>

        <h1><s:message code="articulo.nuevo.label" /></h1>
        <hr/>
        <form:form commandName="articulo" action="crea" method="post">
            <form:errors path="*">
                <div class="alert alert-block alert-error fade in" role="status">
                    <a class="close" data-dismiss="alert">×</a>
                    <c:forEach items="${messages}" var="message">
                        <p>${message}</p>
                    </c:forEach>
                </div>
            </form:errors>

            <fieldset>
                <s:bind path="articulo.nombre">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="nombre">
                            <s:message code="nombre.label" />
                            <span class="required-indicator">*</span>
                        </label>
                        <form:input path="nombre" required="true" />
                        <form:errors path="nombre" cssClass="alert alert-error" />
                    </div>
                </s:bind>
                <s:bind path="articulo.descripcion">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="descripcion">
                            <s:message code="descripcion.label" />
                            <span class="required-indicator">*</span>
                        </label>
                        <form:textarea path="descripcion" required="true" />
                        <form:errors path="descripcion" cssClass="alert alert-error" />
                    </div>
                </s:bind>
                <s:bind path="articulo.contenido">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="contenido">
                            <s:message code="contenido.label" />
                            <span class="required-indicator">*</span>
                        </label>
                        <form:textarea path="contenido" required="true" />
                        <form:errors path="contenido" cssClass="alert alert-error" />
                    </div>
                </s:bind>
                <s:bind path="articulo.fechaPublicacion">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="fechaPublicacion">
                            <s:message code="fechaPublicacion.label" />
                            <span class="required-indicator">*</span>
                        </label>
                        <form:input path="fechaPublicacion" required="true" />
                        <form:errors path="nombre" cssClass="alert alert-error" />
                    </div>
                </s:bind>
                <s:bind path="articulo.ubicaciones">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="ubicaciones">
                            <s:message code="ubicaciones.label" />
                        </label>
                        <form:input path="ubicaciones" />
                        <form:errors path="ubicaciones" cssClass="alert alert-error" />
                    </div>
                </s:bind>
                <s:bind path="articulo.etiquetas">
                    <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                        <label for="etiquetas">
                            <s:message code="etiquetas.label" />
                        </label>
                        <form:input path="etiquetas" />
                        <form:errors path="etiquetas" cssClass="alert alert-error" />
                    </div>
                </s:bind>
            </fieldset>

            <p class="well" style="margin-top: 10px;">
                <button type="submit" name="crea" class="btn btn-primary btn-large" id="crea" ><i class="icon-ok icon-white"></i>&nbsp;<s:message code='crear.button'/></button>
                <a class="btn btn-large" href="<s:url value='/admin/articulo'/>"><i class="icon-remove"></i> <s:message code='cancelar.button' /></a>
            </p>
        </form:form>
        <content>
            <script>
                $(document).ready(function() {
                    $('input#nombre').focus();
                });
            </script>                    
        </content>
    </body>
</html>
