<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
    </head>
    <body>
        <!--<div id="divMessage" style="display:none"></div>-->      
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><s:a href="getting.action" targets="divBodyLayout">Recolectar datos</s:a></li>
                <li><span>Productores</span></li>
            </ul>
        </div>
        <%@ include file="view-producer.jsp" %>             
    </body>
</html>