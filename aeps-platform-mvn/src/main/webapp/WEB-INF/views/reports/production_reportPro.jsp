<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>							
        <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="scripts/js/flot-charts/excanvas.min.js"></script><![endif]-->
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.time.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.symbol.js"></script>
        <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.axislabels.js"></script>
        <!--<script type="text/javascript" src="/js/flot/jshashtable-2.1.js"></script>-->    
        <!--<script type="text/javascript" src="/js/flot/jquery.numberformatter-1.2.3.min.js"></script>--> 
		<script type="text/javascript" src="scripts/js/generals/reportPro.js"></script>
	</head>
	<body>		
        <s:hidden name="info"/>
        <div style="width:450px;height:300px;text-align:center;margin:10px">        
            <div id="flot-placeholder" style="width:100%;height:100%;"></div>        
        </div>
		<script>
            //alert($("#info").val());
            objResult = JSON.parse($("#info").val());
			chargeInfographic();
		</script>
	</body>
</html>