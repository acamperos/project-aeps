<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
        <!--<script src="/scripts/js/generals/googleAnalytics.js"></script>-->
    </head>
    <body>
        <%@ include file="../../generals/googleAnalytics.jsp" %>    
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><s:a href="%{request.getContextPath()}/getting.action" targets="divBodyLayout"><s:property value="getText('link.collectdata')" /></s:a></li>
                <li><span><s:property value="getText('label.optcrops')" /></span></li>
            </ul>
        </div>
        <%@ include file="view-crops.jsp" %>              
    </body>
</html>           