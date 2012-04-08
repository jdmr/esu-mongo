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
        <link rel="stylesheet" href="<c:url value='/css/font-awesome.css' />" type="text/css">
        <link rel="stylesheet" href="<c:url value='/css/custom-theme/jquery-ui-1.8.17.custom.css' />" type="text/css">
        <link rel="stylesheet" href="<c:url value='/css/app.css' />" type="text/css">
    <sitemesh:write property='head'/>
</head>
<body>
    <div class="container">
        <header>
            <div class="row">
                <div class="span6">
                    <a href="<c:url value='/inicio' />"><img src="<c:url value='/images/logo.jpg'/>" /></a>
                </div>
                <div class="span6 pull-right" style="text-align: right;">
                    <sec:authorize access="isAnonymous()">
                        <form action="<c:url value='/j_spring_openid_security_check'/>" id="googleOpenId" method="post" target="_top" class="form-inline" style="display: inline;" >
                            <input id="openid_identifier" name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
                            <input type="image" src="<c:url value="/images/google.png" />" border="0" name="googleSignInBtn" id="googleSignInBtn" style="height: 26px; vertical-align: top;">
                        </form>
                        <form action="<c:url value='/signin/facebook'/>" id="facebookOpenId" method="post" target="_top" class="form-inline" style="display: inline;" >
                            <input type="hidden" name="scope" value="email,publish_stream,offline_access" />
                            <input type="image" src="<c:url value="/images/facebook.png" />" border="0" name="facebookSignInBtn" id="facebookSignInBtn" style="height: 26px; vertical-align: top;">
                        </form>
                        <form action="<c:url value='/j_spring_openid_security_check'/>" id="twitterOpenId" method="post" target="_top" class="form-inline" style="display: inline;" >
                            <input id="openid_identifier" name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id"/>
                            <input type="image" src="<c:url value="/images/twitter.png" />" border="0" name="twitterSignInBtn" id="twitterSignInBtn" style="height: 26px; vertical-align: top; padding-right: 5px;">
                        </form>
                        <form action='<c:url value="/entrar" />' method='POST' id='loginForm' class="pull-right form-inline" style="display: inline;">
                            <input type="text" class="input-small" placeholder="<s:message code='correo.label'/>" name="j_username" id="loginUsername" />
                            <input type="password" class="input-small" placeholder="<s:message code='password.label' />" name="j_password" id="loginPassword" />
                            <label class="checkbox">
                                <input type="checkbox" name='_spring_security_remember_me' id='loginRememberMe' /> <s:message code="recuerdame.label" />
                            </label>
                                <button type="submit" class="btn"><s:message code="login.entrar" /><i class="icon-signin" style="margin-left: 3px;"></i></button> 
                        </form>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <p><sec:authentication property="principal.nombreCompleto" /><a href="<c:url value='/salir' />" style="margin-left: 10px;"><s:message code="salir.label" /><i class="icon-signout" style="margin-left: 3px;"></i></a></p>
                    </sec:authorize>
                </div>
            </div>
        </header>
        <nav class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="i-bar"></span>
                        <span class="i-bar"></span>
                        <span class="i-bar"></span>
                    </a>
                    <div class="nav-collapse">
                        <sitemesh:write property="nav"/>
                    </div>
                </div>
            </div>
        </nav>
        <sitemesh:write property='body'/>
        <footer>
            <div class="row" style="margin: 20px 0px 0 0px; padding-top: 5px; background-color: #2C2C2C; color: #999;" >
                <div class="span4">
                    <p><s:message code="mapa.del.sitio.label" /></p>
                    <p><a href="<c:url value='/inicio'/>"><s:message code="inicio.label" /></a></p>
                    <p><a href="<c:url value='/estudia'/>"><s:message code="estudia.label" /></a></p>
                    <p><a href="<c:url value='/profundiza'/>"><s:message code="profundiza.label" /></a></p>
                    <p><a href="<c:url value='/comparte'/>"><s:message code="comparte.label" /></a></p>
                    <p><a href="<c:url value='/foros'/>"><s:message code="foros.label" /></a></p>
                    <p><a href="<c:url value='/conocenos'/>"><s:message code="conocenos.label" /></a></p>
                </div>
                <div class="span2">
                    <p><s:message code="siguenos.en.label" /></p>
                    <p><a href="http://twitter.com/esuniversitaria"><i class="icon-facebook-sign icon-white" style="margin-right: 5px;"></i>facebook</a></p>
                    <p><a href="http://twitter.com/esuniversitaria"><i class="icon-twitter-sign icon-white" style="margin-right: 5px;"></i>twitter</a></p>
                </div>
                <div>
                    <p class="pull-right">&copy; <s:message code="proyecto.copyright.year.label" /> Powered by <a href="http://www.um.edu.mx"><s:message code="proyecto.empresa.label" /></a> </p>
                </div>
            </div>
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
    <script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-4d8a78014d97ad87"></script>
    <script type="text/javascript">
        $("#googleSignInBtn").popover({placement:'bottom', title:'Entrar con Google', content:'¿Tienes cuenta en Google? Entra con Google.'})
        $("#facebookSignInBtn").popover({placement:'bottom', title:'Entrar con Facebook', content:'¿Tienes cuenta en Facebook? Entra con Facebook.'})
        $("#twitterSignInBtn").popover({placement:'bottom', title:'Entrar con Twitter', content:'¿Tienes cuenta en Twitter? Entra con Twitter.'})
    </script>
    <sitemesh:write property="content"/>
</body>
</html>
