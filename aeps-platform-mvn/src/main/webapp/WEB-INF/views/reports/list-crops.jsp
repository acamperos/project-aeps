<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
        <link rel="stylesheet" href="scripts/js/spinner/jqamp-ui-spinner.min.css"/>
        <script type="text/javascript" src="scripts/js/spinner/globalize.min.js"></script>
        <script type="text/javascript" src="scripts/js/spinner/jqamp-ui-spinner.min.js"></script>
        <script type="text/javascript" src="scripts/js/spinner/jquery-mousewheel-3.0.6.min.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/highcharts.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/highcharts-more.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/exporting.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.time.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.symbol.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.axislabels.js"></script>
    </head>
    <body>   
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><s:a href="%{request.getContextPath()}/reports.action" targets="divBodyLayout">Reportes</s:a></li>
                <li><span>Reporte de Productividad</span></li>
            </ul>
        </div>
        <%@ include file="view-crops.jsp" %>              
    </body>
</html>           