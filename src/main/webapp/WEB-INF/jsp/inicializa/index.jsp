<%-- 
    Document   : index
    Created on : Feb 6, 2012, 2:32:48 PM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s"    uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="inicializa.title" /></title>
    </head>
    <body>
        <h2><s:message code="inicializa.title" /></h2>
        <form action="<c:url value='/inicializa' />" method="post">
            <fieldset>
                <div class="control-group">
                    <label for='username'><s:message code="username.label" /></label>
                    <input id="username" name="username" type="text" value=""/>
                </div>
                <div class="control-group">
                    <label for='password'><s:message code="password.label" /></label>
                    <input id="password" name="password" type="password" value=""/>
                </div>
                <div class="control-group">
                    <label for='nombre'><s:message code="nombre.label" /></label>
                    <input id="nombre" name="nombre" type="text" value=""/>
                </div>
                <div class="control-group">
                    <label for='apellido'><s:message code="apellido.label" /></label>
                    <input id="apellido" name="apellido" type="text" value=""/>
                </div>
                <div class="control-group">
                    <label for='correo'><s:message code="correo.label" /></label>
                    <input id="correo" name="correo" type="email" value=""/>
                </div>
                <div class="control-group">
                    <input type="submit" value="<s:message code='inicializa.title' />" class="btn btn-large btn-primary" style="width:170px;"/>
                </div>
            </fieldset>
        </form>
    </body>
</html>
<content>
    <script>
        $(document).ready(function() {
            $('input#username').focus();
        });
    </script>                    
</content>
