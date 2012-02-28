<%-- 
    Document   : main
    Created on : Jan 24, 2012, 1:43:27 PM
    Author     : jdmr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s"   uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title><s:message code="proyecto.nombre.label" /> - <sitemesh:write property='title'/></title>
        <meta name="description" content="Escuela Sabatica Universitaria">
        <meta name="author" content="Universidad de Montemorelos">

        <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="<c:url value='/images/favicon.ico' />" type="image/x-icon">
        <link rel="apple-touch-icon" href="<c:url value='/images/apple-touch-icon.png' />">
        <link rel="apple-touch-icon" sizes="114x114" href="<c:url value='/images/apple-touch-icon-retina.png' />">
        <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css' />" type="text/css">
        <link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.min.css' />" type="text/css">
        <link rel="stylesheet" href="<c:url value='/css/custom-theme/jquery-ui-1.8.17.custom.css' />" type="text/css">
        <link rel="stylesheet" href="<c:url value='/css/app.css' />" type="text/css">
        <sitemesh:write property='head'/>
    </head>
    <body>
        <div class="container">
            <sitemesh:write property='body'/>
            <footer>
                <hr />
                <p class="pull-right">&copy; 2012 Universidad de Montemorelos</p>
            </footer>
        </div>

        <!-- JavaScript at the bottom for fast page loading -->

        <!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="<c:url value='/js/jquery-1.7.1.min.js'/>"><\/script>')</script>

        <!-- end scripts -->        
        <script src="<c:url value='/js/jquery-ui-1.8.17.custom.min.js' />"></script>
        <script src="<c:url value='/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/js/app.js' />"></script>
        <sitemesh:write property="content"/>
    </body>
</html>
