<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
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
        <sj:head jqueryui="false"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <link rel="stylesheet" href="scripts/css/generals/login.css">			        
    </head>
    <body>
        <div id="login-wrapper" style="width: 550px;">            
            <p><img src="img/logoAEPS.png"/></p>
            <div class="heading_main" style="font-size: 18px;">
                <p><s:property value="getText('area.correctvalidation.login')" />. </p> 
                <p><s:property value="getText('area.access.login')" />.</p> 
                <a href="/signin.action" class="btn btn-initial btn-large"><i class="icon-arrow-right icon-white"></i>  <s:property value="getText('link.signdirect.login')" /></a>
            </div>
        </div>
        <script type="text/javascript" src="scripts/js/generals/functions.min.js"></script>	
    </body>
</html>