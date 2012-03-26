<%-- 
    Document   : nuevo
    Created on : Jan 27, 2012, 10:37:52 AM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="articulo.edita.label" /></title>
        <link rel="stylesheet" href="<c:url value='/css/tagit-simple-blue.css' />" type="text/css">
    </head>
    <body>
        <jsp:include page="../menu.jsp" >
            <jsp:param name="menu" value="articulo" />
        </jsp:include>

        <h1><s:message code="articulo.edita.label" /></h1>
        <hr/>
        <c:url var="actualizaUrl" value="/admin/articulo/actualiza" />
        <form:form commandName="articulo" action="${actualizaUrl}" method="post">
            <form:errors path="*">
                <div class="alert alert-block alert-error fade in" role="status">
                    <a class="close" data-dismiss="alert">×</a>
                    <c:forEach items="${messages}" var="message">
                        <p>${message}</p>
                    </c:forEach>
                </div>
            </form:errors>
            
            <form:hidden path="id" />

            <fieldset>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="articulo.nombre">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="nombre">
                                    <s:message code="nombre.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:input path="nombre" required="true" class="span6" />
                                <form:errors path="nombre" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                    <div class="span6">
                        <s:bind path="articulo.descripcion">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="descripcion">
                                    <s:message code="descripcion.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:textarea path="descripcion" required="true" class="span6" />
                                <form:errors path="descripcion" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
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
                    </div>
                    <div class="span6">
                        <s:bind path="articulo.estatus">
                            <div class="control-group <c:if test='${not empty status.errorMessage}'>error</c:if>">
                                <label for="estatus">
                                    <s:message code="estatus.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:select items="${estados}" path="estatus" itemLabel="nombre" itemValue="nombre" />
                                <form:errors path="estatus" cssClass="alert alert-error" />
                            </div> 
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="articulo.autor">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="autor">
                                    <s:message code="autor.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:select items="${autores}" path="autor.username" itemLabel="nombreCompleto" itemValue="username" />
                                <form:errors path="autor" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                    <div class="span6">
                        <s:bind path="articulo.editor">
                            <div class="control-group <c:if test='${not empty status.errorMessage}'>error</c:if>">
                                <label for="editor">
                                    <s:message code="editor.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:select items="${editores}" path="editor.username" itemLabel="nombreCompleto" itemValue="username" />
                                <form:errors path="editor" cssClass="alert alert-error" />
                            </div> 
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span12">
                        <s:bind path="articulo.contenido">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="contenido">
                                    <s:message code="contenido.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:textarea path="contenido" required="true" class="span12" />
                                <form:errors path="contenido" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="articulo.ubicaciones">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="ubicaciones">
                                    <s:message code="ubicaciones.label" />
                                </label>
                                <ul id="ubicacionesTags" name="ubicaciones"></ul>
                                <form:errors path="ubicaciones" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                    <div class="span6">
                        <s:bind path="articulo.etiquetas">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="etiquetas">
                                    <s:message code="etiquetas.label" />
                                </label>
                                <ul id="etiquetasTags" name="etiquetas"></ul>
                                <form:errors path="etiquetas" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
            </fieldset>

            <p class="well" style="margin-top: 10px;">
                <button type="submit" name="actualizarBtn" class="btn btn-primary btn-large" id="actualizar" ><i class="icon-ok icon-white"></i>&nbsp;<s:message code='actualizar.button'/></button>
                <a class="btn btn-large" href="<s:url value='/admin/articulo'/>"><i class="icon-remove"></i> <s:message code='cancelar.button' /></a>
            </p>
        </form:form>
        <content>
            <script src="<c:url value='/js/tagit.js' />"></script>
            <script>
                $(document).ready(function() {
                    $('#ubicacionesTags').tagit({
                        select:true
                        , triggerKeys : ['enter', 'comma']
                        , tagSource:'<c:url value="/admin/articulo/carpetas"/>'
                        , initialTags : [<c:forEach items="${articulo.ubicaciones}" var="carpeta">'${carpeta}',</c:forEach>]
                    });
                    $('#etiquetasTags').tagit({
                        select:true
                        , triggerKeys : ['enter', 'comma']
                        , tagSource:'<c:url value="/admin/articulo/etiquetas"/>'
                        , initialTags : [<c:forEach items="${articulo.etiquetas}" var="etiqueta">'${etiqueta}',</c:forEach>]
                    });
                    
                    $('input#nombre').focus();
                });
            </script>                    
            <c:url var="ckeditorBase" value="/ckeditor/" />
            <ckeditor:replace  replace="contenido" basePath="${ckeditorBase}" />
        </content>
    </body>
</html>
