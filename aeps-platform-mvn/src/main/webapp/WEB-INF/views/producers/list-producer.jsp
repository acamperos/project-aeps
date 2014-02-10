<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head></head>
    <body>
        <div id="divMessage" style="display:none"></div>        
        <div class="container">
            <%@ include file="search-producer.jsp" %>
        </div>     
        <div class="container" id="divConListProducers">
            <%@ include file="info-producer.jsp" %>            
        </div>        
    </body>
</html>