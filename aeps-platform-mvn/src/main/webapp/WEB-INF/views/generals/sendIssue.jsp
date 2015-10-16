<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/favicon.ico">
    </head>
    <body>     
        <%@ include file="googleAnalytics.jsp" %>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="container">
            <ul id="breadcrumbs">
                <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i><s:property value="getText('link.homeprivate')" /></s:a></li>
                <li><span><s:property value="getText('link.sendissue')" /></span></li>
            </ul>
        </div>
        <div class="container">
            <div class="panel">
                <div class="panel-heading">
                    <h4><s:property value="getText('title.sendissue.issue')" /></h4>
                </div>
                <div class="panel-body">
                    <s:form id="formReport" action="sendIssue.action" enctype="multipart/form-data">
                        <s:hidden name="actExe" />
                        <div class="control-group">
                            <label for="formReport_issRep_nameIss" class="control-label req"><s:property value="getText('text.subject.issue')" />:</label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="issRep.nameIss"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formReport_issRep_descriptionIss" class="control-label req"><s:property value="getText('text.descissue.issue')" />:</label>
                            <div class="controls">
                                <s:textarea rows="5" cssClass="span6" name="issRep.descriptionIss"></s:textarea>
                            </div>					 
                        </div>	
                        <div class="control-group">
                            <label for="formReport_archivo" class="control-label req<s:property value="getText('file.descissue.issue')" />Cargar Imagen del problema:</label>
                            <div class="controls">
                                <s:file name="archivo" accept="image/jpeg,image/jpg,image/png" onchange="showImageToSend('loadImage.action', 'formReport_archivo', 'imagenDinamica')"/>
                            </div>                            
                        </div> 
                        <div class="control-group" id="divImages">
                            <img id="imagenDinamica" />
                        </div>
                        <div class="row">
                            <div class="span6">
                                <sj:submit type="button" id="btSendIssue" cssClass="btn btn-large btn-initial" onclick="addMessageProcess(); ga('send', 'event', 'Errors', 'click', 'Report');" targets="divMessage" onCompleteTopics="completeReport" validate="true" validateFunction="validationForm">
                                    <s:property value="getText('button.sendissue.issue')" />
                                </sj:submit> 
                            </div>  
                        </div>  
                        <script>
                            $.subscribe('completeReport', function(event, data) {
                                completeFormChange('', 'formReport', event.originalEvent.request.responseText);
                                document.getElementById('imagenDinamica').setAttribute('src','');
                            });
                        </script>
                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>    