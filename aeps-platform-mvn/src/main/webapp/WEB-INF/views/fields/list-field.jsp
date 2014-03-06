<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <!--<div id="divMessage" style="display:none"></div>-->       
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><s:a href="getting.action" targets="divBodyLayout">Recolectar datos</s:a></li>
                <li><span>Lotes</span></li>
            </ul>
        </div>
        <div class="container">
            <%@ include file="search-field.jsp" %>
        </div>     
        <div class="container" id="divConListFields">
            <%@ include file="info-field.jsp" %>            
        </div>           
    </body>
</html>           