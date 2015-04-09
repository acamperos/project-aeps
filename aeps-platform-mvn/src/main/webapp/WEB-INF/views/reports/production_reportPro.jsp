<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>		
        <s:hidden name="info"/>
        <div style="width:auto;height:300px;text-align:center;margin:10px">        
            <div id="flot-placeholder" style="width:100%;height:100%;"></div>        
        </div>
        <script type="text/javascript" src="scripts/js/generals/reportPro.js"></script>
        <script>
//            alert($("#info").val());
            objResult = JSON.parse($("#info").val());
            chargeInfographic();
        </script>
    </body>
</html>