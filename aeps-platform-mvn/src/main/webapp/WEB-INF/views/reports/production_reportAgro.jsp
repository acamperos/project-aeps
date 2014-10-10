<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>							
		<script type="text/javascript" src="scripts/js/generals/reportAgro.js"></script>
	</head>
	<body>		
        <s:hidden name="info"/>
        <div id="container" style="height: 400px; margin: auto; width: auto"></div>
		<script>
//            alert($("#info").val());
            objResult = JSON.parse($("#info").val());
			chargeInfographic('container');
		</script>
	</body>
</html>