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
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/beoro.min.css">       		
        <sj:head jqueryui="true"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/colorbox/colorbox.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/main.css">        
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/font-awesome/css/font-awesome.min.css">               
        <link href='http://fonts.googleapis.com/css?family=Istok+Web:500,700' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/geoxml/gmap.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/multiple-select.css"/>        
    </head>
    <body>
        <div id="divMessage"></div>
        <div id="dialog-form"></div>
        <div class="header">
            <%@ include file="header-private.jsp" %>
        </div>
        <div class="body" id="divBodyLayout">
            <%--<%@ include file="dashboard.jsp" %>--%>
        </div>
        <div class="footer">
            <%@ include file="footer.jsp" %>
        </div>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/generals/functions.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.maskMoney.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.base64.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery-ui/ui/jquery-ui.js"></script>	
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery-ui/ui/jquery.ui.dialog.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/colorbox/jquery.colorbox.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/pwdMeter/jquery.pwdMeter.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/generals/responsiveslides.js"></script>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/geoxml/geoxml3.js"></script>    
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/generals/jquery.multiple.select.js"></script>
        <script>
            var actionName   = '<%= session.getAttribute("action") %>';
            var actionUrl    = '<%= session.getAttribute("actionUrl") %>';
            activeOption('ulOptionsMenu', actionName+'Cls');
            if (actionName!='' && actionName!='dashboard') {
                showInfoPage(''+actionUrl, 'divBodyLayout');                       
            } else if(actionName=='dashboard' || actionName=='') {
                showInfoPage('homePrivate.action', 'divBodyLayout');
            }
            $(document).ready(function() {
                beoro_scrollToTop.init();
            })
        </script>
    </body>
</html>
