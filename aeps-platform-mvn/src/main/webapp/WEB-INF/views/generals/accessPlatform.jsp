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
        <link rel="stylesheet" href="scripts/css/generals/beoro.css">
        <link rel="stylesheet" href="scripts/css/generals/login.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>				
        <script type="text/javascript" src="scripts/js/generals/functions.js"></script>	
    </head>
    <body>
        <div id="divMessage" style="display:none"></div>        
        <div id="login-wrapper" class="clearfix">            
            <div class="heading_main">
                <p>Se ha validado correctamente la contraseña! Será dirigido automáticamente en diez segundos. </p> 
                <p>En caso contrario, puedes acceder haciendo click <a href="signin.action" class="btn btn-primary btn-success btn-lg">Ingresar</a></p>
            </div>
        </div>
    </body>
</html>