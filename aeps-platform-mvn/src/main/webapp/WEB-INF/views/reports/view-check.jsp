<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
    </head>
    <body>     
        <%@ include file="../generals/googleAnalytics.jsp" %>
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="%{request.getContextPath()}/dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><s:a href="%{request.getContextPath()}/reports.action" targets="divBodyLayout"><s:property value="getText('link.reports')" /></s:a></li>
                <li><span><s:property value="getText('label.optcropcheck')" /></span></li>
            </ul>
        </div>
        <div class="container">
            <div class="panel">
                <div class="panel-body">
                    <s:form id="formCrop" action="viewCropcheck.action" cssClass="form-horizontal">
                        <fieldset>         
                            <div class="control-group">
                                <s:label for="formCrop_nameCrop" cssClass="control-label req" value="%{getText('text.selectcrop.report')}:"></s:label>
                                <div class="controls">
                                    <s:hidden name="idCrop"/>
                                    <s:textfield name="nameCrop" readonly="true" onclick="viewForm('/crop/viewCrop.action?selected=cropcheck', 'idCrop', '', '%{getText('title.selectcrop.report')}', 1050, 550)" />
                                    <a class="btn" onclick="viewForm('/crop/viewCrop.action?selected=cropcheck', 'idCrop', '', '<s:property value="getText('title.selectcrop.report')" />', 1050, 550)"><i class="icon-search"></i></a>
                                </div>  
                            </div>    
                        </fieldset>  
                        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divInfoCrop" onCompleteTopics="completeSelection"><s:property value="getText('button.viewcheck.report')" /> <i class="icon-search"></i></sj:submit>
                    </s:form> 
                </div>      
            </div>      
        </div>                 
        <div class="container" id="divInfoCrop">    
        </div>
        <script>
            $.subscribe('completeSelection', function(event, data) {
                $.unblockUI();
            });
        </script>
    </body>
</html>           