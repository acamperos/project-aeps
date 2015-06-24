<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="img/logoAEPS.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 							
        <link rel="stylesheet" href="scripts/css/generals/beoro.min.css">        
        <sj:head jqueryui="false"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>        
        <link rel="stylesheet" href="scripts/css/generals/main.css">
        <link rel="stylesheet" href="scripts/css/font-awesome/css/font-awesome.min.css">   
        <link href='http://fonts.googleapis.com/css?family=Istok+Web:500,700' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <div id="divMessage"></div>
        <div id="dialog-form"></div>
        <div class="header">
            <%@ include file="WEB-INF/views/generals/header.jsp" %>
        </div>
        <div class="body" id="divBodyLayout">
            <%--<%@ include file="WEB-INF/views/generals/home.jsp" %>--%>
        </div>
        <div class="footer">
            <%@ include file="WEB-INF/views/generals/footer.jsp" %>            
        </div>
        <script type="text/javascript" src="scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.blockUI.js"></script>        
        <script type="text/javascript" src="scripts/js/generals/functions.js"></script>	 
        <script src="scripts/js/generals/responsiveslides.js"></script>        
        <script>            
            function doAction() {
//            alert(countryCode)
                var actionName = '<%= session.getAttribute("action") %>';
                if (actionName!='null' && actionName!='' && actionName!='dashboard' && actionName!='initial' && actionName!='principal' && actionName!='login') {
                    showInfoPage(''+actionName+'.action?countryCode='+countryCode, 'divBodyLayout');                 
                } else if(actionName=='null' || actionName=='') {
                    document.location = '<%= request.getContextPath() %>/initial.action?countryCode='+countryCode;
                } else if(actionName=='initial') {
                    showInfoPageCountry('home.action', countryCode, 'divBodyLayout');
                }
                if (actionName=='null' || actionName=='' || actionName=='dashboard' || actionName=='initial' || actionName=='principal') {
                    actionName = 'home';                  
                }
                activeOption('ulOptionsMenu', actionName+'Cls');
            }
//            getCountry();
//            doAction();
            $.when(getCountry()).then(doAction);
            $(document).ready(function() {
                beoro_scrollToTop.init();
            })
        </script>
    </body>
</html>
