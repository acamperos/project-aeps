<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>		
        <s:hidden name="info"/>
        <div id="container" style="height: 400px; margin: auto; width: auto"></div>
        <script type="text/javascript" src="scripts/js/generals/reportAgro.js"></script>
        <script>
//            alert($("#info").val());
            objResult = JSON.parse($("#info").val());
            chargeInfographic('container');
        </script>
    </body>
</html>