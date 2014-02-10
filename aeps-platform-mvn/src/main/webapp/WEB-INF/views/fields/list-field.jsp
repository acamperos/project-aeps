<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <div id="divMessage" style="display:none"></div>        
        <div class="container">
            <%@ include file="search-field.jsp" %>
        </div>     
        <div class="container" id="divConListFields">
            <%@ include file="info-field.jsp" %>            
        </div>           
    </body>
</html>           