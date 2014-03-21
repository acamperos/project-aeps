<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
<!--        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <link rel="icon" type="image/ico" href="img/logoAEPS.ico">
        <title>AEPS</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width"> 							
        <link rel="stylesheet" href="../scripts/css/generals/style.css">
        <link rel="stylesheet" href="../scripts/css/generals/responsiveslides.css" />	
        <link rel="stylesheet" href="../scripts/css/generals/beoro.css">      
        <link rel="stylesheet" href="../scripts/js/jquery/jquery-ui/themes/base/jquery.ui.all.css" />
        <link rel="stylesheet" href="../scripts/js/jquery/jquery-ui/themes/base/jquery-ui.css" />
        <script type="text/javascript" src="../scripts/js/jquery/jquery.maskedinput.js"></script>
        <script type="text/javascript" src="../scripts/js/jquery/jquery.numeric.js"></script>
        <script type="text/javascript" src="../scripts/js/jquery/jquery.blockUI.js"></script>    
        <script type="text/javascript" src="../scripts/js/jquery/jquery-ui/ui/jquery-ui.js"></script>	
        <script type="text/javascript" src="../scripts/js/jquery/jquery-ui/ui/i18n/jquery.ui.datepicker-es.js"></script>
        <script type="text/javascript" src="../scripts/js/jquery/jquery-ui/ui/jquery.ui.dialog.js"></script>
        <script type="text/javascript" src="../scripts/js/generals/functions.js"></script>		       
        <link rel="stylesheet" href="../scripts/css/generals/main.css">		
		<link rel="stylesheet" href="../scripts/js/jquery/jquery-ui/themes/base/jquery-ui.css" />
        <script type="text/javascript" src="../scripts/js/colorbox/jquery.colorbox.min.js"></script>
        <link rel="stylesheet" href="../scripts/css/colorbox/colorbox.css"/>-->
    </head>
    <body>
        <!--<div id="divMessage" style="display:none"></div>-->       
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><s:a href="%{request.getContextPath()}/getting.action" targets="divBodyLayout">Recolectar datos</s:a></li>
                <li><span>Rasta</span></li>
            </ul>
        </div>
        <%@ include file="view-soil.jsp" %>              
    </body>
</html>           