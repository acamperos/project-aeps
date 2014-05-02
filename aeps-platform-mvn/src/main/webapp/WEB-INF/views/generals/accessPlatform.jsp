<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--<%@ taglib uri="http://code.google.com/p/jcaptcha4struts2/taglib/2.0" prefix="jcaptcha" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="refresh" content="10; url=signin.action">
        <link rel="icon" type="image/ico" href="favicon.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">       
        <sj:head jqueryui="true"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <link rel="stylesheet" href="scripts/css/generals/login.css">			
        <script type="text/javascript" src="scripts/js/generals/functions.js"></script>	
    </head>
    <body>
        <div id="login-wrapper" style="width: 550px;">            
            <p><img src="img/logoAEPS.png"/></p>
            <div class="heading_main" style="font-size: 18px;">
                <p>Se ha validado correctamente la contraseña! Será dirigido automáticamente en diez(10) segundos. </p> 
                <p>En caso contrario, puedes acceder directamente haciendo click en el botón.</p> 
                <a href="signin.action" class="btn btn-initial btn-large"><i class="icon-arrow-right icon-white"></i>  Ingresar</a>
            </div>
        </div>
    </body>
</html>