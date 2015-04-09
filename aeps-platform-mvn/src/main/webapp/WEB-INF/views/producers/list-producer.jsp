<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
    </head>
    <body>
        <%@ include file="../generals/googleAnalytics.jsp" %>   
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><s:a href="getting.action" targets="divBodyLayout"><s:property value="getText('link.collectdata')" /></s:a></li>
                <li><span><s:property value="getText('label.optproducer')" /></span></li>
            </ul>
        </div>
        <%@ include file="view-producer.jsp" %>             
    </body>
</html>