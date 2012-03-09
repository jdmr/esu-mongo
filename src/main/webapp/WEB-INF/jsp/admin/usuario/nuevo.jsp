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
        <title><s:message code="usuario.nuevo.label" /></title>
        <link rel="stylesheet" href="<c:url value='/css/tagit-simple-blue.css' />" type="text/css">
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

        <h1><s:message code="usuario.nuevo.label" /></h1>
        <hr/>
        <c:url var="creaUsuario" value="/admin/usuario/crea" />
        <form:form commandName="usuario" action="${creaUsuario}" method="post">
            <form:errors path="*">
                <div class="alert alert-block alert-error fade in" role="status">
                    <a class="close" data-dismiss="alert">×</a>
                    <c:forEach items="${messages}" var="message">
                        <p>${message}</p>
                    </c:forEach>
                </div>
            </form:errors>

            <fieldset>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.username">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="username">
                                    <s:message code="username.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:input path="username" required="true" class="span6" />
                                <form:errors path="username" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.password">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="password">
                                    <s:message code="password.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:password path="password" required="true" class="span6" />
                                <form:errors path="password" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.nombre">
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
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.apellido">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="apellido">
                                    <s:message code="apellido.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:input path="apellido" required="true" class="span6" />
                                <form:errors path="apellido" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.correo">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="correo">
                                    <s:message code="correo.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <form:input path="correo" required="true" class="span6" type="email" />
                                <form:errors path="correo" cssClass="alert alert-error" />
                            </div>
                        </s:bind>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span6">
                        <s:bind path="usuario.roles">
                            <div class="control-group <c:if test='${not empty status.errorMessages}'>error</c:if>">
                                <label for="roles">
                                    <s:message code="rol.list.label" />
                                    <span class="required-indicator">*</span>
                                </label>
                                <c:forEach items="${roles}" var="rol">
                                    <form:checkbox path="roles" value="${rol.authority}" /> <s:message code="${rol.authority}" />&nbsp;
                                </c:forEach>
                            </div>
                        </s:bind>
                    </div>
                </div>
            </fieldset>

            <p class="well" style="margin-top: 10px;">
                <button type="submit" name="crea" class="btn btn-primary btn-large" id="crea" ><i class="icon-ok icon-white"></i>&nbsp;<s:message code='crear.button'/></button>
                <a class="btn btn-large" href="<s:url value='/admin/usuario'/>"><i class="icon-remove"></i> <s:message code='cancelar.button' /></a>
            </p>
        </form:form>
        <content>
            <script src="<c:url value='/js/tagit.js' />"></script>
            <script>
                $(document).ready(function() {
                    $('input#username').focus();
                });
            </script>                    
        </content>
    </body>
</html>
