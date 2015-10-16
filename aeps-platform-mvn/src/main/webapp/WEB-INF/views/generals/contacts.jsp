<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@ include file="googleAnalytics.jsp" %>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="container">
            <div class="panel">
                <div class="panel-body">
                    <div class="row">
                        <div class="span4">
                            <div class="panel">
                                <div class="panel-heading">
                                    <h3><i class="icon-pushpin main-color"></i> <s:property value="getText('title.ouroffice.contact')" /></h3>
                                </div>
                                <div class="panel-body">
                                    <address>
                                        <strong><s:property value="getText('title.namecompany.contact')" /></strong><br>
                                        <s:text name="%{getText('area.locationcompany.contact')}"/>
                                    </address>
                                </div>
                            </div>
                        </div>
                        <div class="span7">
                            <div class="panel">
                                <div class="panel-heading">                                    
                                    <h3><i class="icon-envelope main-color"></i> <s:property value="getText('title.contactus.contact')" /></h3>
                                </div>
                                <div class="panel-body">
                                    <s:form id="formContact" action="sendInformation.action" method="post">
                                        <s:hidden name="actExe" value="contact"/>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_nameUser" class="control-label"><s:property value="getText('text.name.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textfield cssClass="form-control" id="formContact_nameUser" name="nameUser"/>
                                                </div>
                                            </div>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_emailUser" class="control-label"><s:property value="getText('text.email.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textfield cssClass="form-control" id="formContact_emailUser" name="emailUser" placeholder="%{getText('text.ingressemial.contact')}"/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="span3 control-group">
                                                    <label for="formContact_celphone" class="control-label"><s:property value="getText('text.cellphone.contact')" /> :</label>
                                                    <div class="controls">
                                                        <s:textfield cssClass="form-control" id="formContact_celphone" name="celphone"/>
                                                    </div>
                                                </div>
                                                <div class="span3 control-group">
                                                    <label for="formContact_telephone" class="control-label"><s:property value="getText('text.phone.contact')" /> :</label>
                                                    <div class="controls">
                                                        <s:textfield cssClass="form-control" id="formContact_telephone" name="telephone"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="control-group required_field_mark">
                                                <label for="formContact_whatneed" class="control-label"><s:property value="getText('text.whatneed.contact')" /> <span>*</span>:</label>
                                                <div class="controls">
                                                    <s:textarea rows="5" cssClass="span6" name="whatneed"></s:textarea>
                                                </div>					 
                                            </div>					 
                                            <div class="row">
                                                <div class="span6">
                                                    <sj:submit cssClass="btn btn-large btn-initial" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeContact" value="%{getText('button.sendinfo.contact')}" validate="true" validateFunction="validationForm"/>
                                                </div>  
                                            </div>  
                                            <script>
                                                $.subscribe('completeContact', function(event, data) {
                                                    completeFormChange('', 'formContact', event.originalEvent.request.responseText);
                                                });
                                            </script>
                                    </s:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>