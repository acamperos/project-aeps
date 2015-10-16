<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
        <link rel="stylesheet" href="scripts/js/spinner/jqamp-ui-spinner.min.css"/>        
    </head>
    <body>   
        <%@ include file="../generals/googleAnalytics.jsp" %>
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><s:a href="%{request.getContextPath()}/reports.action" targets="divBodyLayout"><s:property value="getText('link.reports')" /></s:a></li>
                <li><span><s:property value="getText('label.yieldreport')" /></span></li>
            </ul>
        </div>
        <script type="text/javascript" src="scripts/js/spinner/globalize.min.js"></script>
        <script type="text/javascript" src="scripts/js/jquery/jquery.maskMoney.js"></script>
        <script type="text/javascript" src="scripts/js/spinner/jqamp-ui-spinner.min.js"></script>
        <script type="text/javascript" src="scripts/js/spinner/jquery-mousewheel-3.0.6.min.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/highcharts.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/highcharts-more.js"></script>
        <script type="text/javascript" src="scripts/js/highcharts/exporting.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.time.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.symbol.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.axislabels.js"></script>
        <%@ include file="view-crops.jsp" %>              
    </body>
</html>           