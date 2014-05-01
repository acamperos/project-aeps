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
        <!--<link rel="stylesheet" href="<%// request.getContextPath() %>/scripts/css/bootstrap/bootstrap-responsive.min.css">-->
        <!-- <link rel="stylesheet" href="scripts/css/bootstrap-theme.min.css"> -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/beoro.css">        
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/generals/functions.js"></script>		
        <sj:head jqueryui="true"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/colorbox/colorbox.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/generals/main.css">
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery.blockUI.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery-ui/ui/jquery-ui.js"></script>	
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/jquery-ui/ui/jquery.ui.dialog.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/colorbox/jquery.colorbox.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/css/font-awesome/css/font-awesome.min.css">
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/js/jquery/pwdMeter/jquery.pwdMeter.min.js"></script>
        <script src="<%= request.getContextPath() %>/scripts/js/generals/responsiveslides.js"></script>
        <link href = 'http://fonts.googleapis.com/css?family=Istok+Web:400700400cursiva,700italicysubconjunto=latin,latin-ext' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <div id="divMessage"></div>
        <div id="dialog-form"></div>
        <div class="header">
            <%@ include file="header-private.jsp" %>
        </div>
        <div class="body" id="divBodyLayout">
            <%@ include file="dashboard.jsp" %>
        </div>
        <div class="footer">
            <%@ include file="footer.jsp" %>
        </div>
        <script>
            var actionName   = '<%= session.getAttribute("action") %>';
            var actionUrl    = '<%= session.getAttribute("actionUrl") %>';
            activeOption('ulOptionsMenu', actionName+'Cls');
            if (actionName!='' && actionName!='dashboard') {
                showInfoPage(''+actionUrl, 'divBodyLayout');                       
            }
            $(document).ready(function() {
                beoro_scrollToTop.init();
            })
        </script>
    </body>
</html>
