<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<!--<html>
    <head>-->
<!--    </head>
    <body>-->
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="container">
            <div class="w-box">
                <div class="w-box-header">
                    <!-- <h4>Tabs</h4> -->
                </div>
                <div class="w-box-content cnt_b">
                    <s:form id="formContact" action="sendInformation.action" method="post">
                        <s:hidden name="actExe" value="contact"/>
                        <fieldset>
                            <legend><s:property value="getText('text.titlecontact.contact')" /></legend>
                            <div class="control-group required_field_mark">
                                <s:label for="formContact_nameUser" cssClass="control-label"><s:property value="getText('text.name.contact')" /> <span>*</span>:</s:label>
                                <div class="controls">
                                    <s:textfield cssClass="form-control" id="formContact_nameUser" name="nameUser"/>
                                </div>
                            </div>
                            <div class="control-group required_field_mark">
                                <s:label for="formContact_emailUser" cssClass="control-label"><s:property value="getText('text.email.contact')" /> <span>*</span>:</s:label>
                                <div class="controls">
                                    <s:textfield cssClass="form-control" id="formContact_emailUser" name="emailUser" placeholder="%{getText('text.ingressemial.contact')}"/>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span4 control-group">
                                    <s:label for="formContact_celphone" cssClass="control-label"><s:property value="getText('text.cellphone.contact')" /> :</s:label>
                                    <div class="controls">
                                        <s:textfield cssClass="form-control" id="formContact_celphone" name="celphone"/>
                                    </div>
                                </div>
                                <div class="span6 control-group">
                                    <s:label for="formContact_telephone" cssClass="control-label"><s:property value="getText('text.phone.contact')" /> :</s:label>
                                    <div class="controls">
                                        <s:textfield cssClass="form-control" id="formContact_telephone" name="telephone"/>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group required_field_mark">
                                <s:label for="formContact_whatneed" cssClass="control-label"><s:property value="getText('text.whatneed.contact')" /> <span>*</span>:</s:label>
                                <div class="controls">
                                    <textarea rows="5" id="formContact_whatneed" name="whatneed"></textarea>
                                </div>					 
                            </div>					 
                            <div class="row-fluid">
                                <div class="span6">
                                    <!--<button type="submit" class="btn btn-primary">Enviar</button>-->
                                    <!-- <button class="btn btn-default" data-href="home.action">Volver</button> -->
                                    <%--<sj:a href="home.action" cssClass="btn btn-default" targets="divBodyLayout">Volver</sj:a>--%>
                                    <!--<a href="home.action" class="btn btn-default">Volver</a>-->
                                    <sj:submit cssClass="btn btn-primary" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeContact" value="Enviar informacion" validate="true" validateFunction="validationForm"/>
                                </div>  
                            </div>  
                            <script>
                                $.subscribe('completeContact', function(event, data) {
                                    completeFormChange('', 'formContact', event.originalEvent.request.responseText);
                                });
                            </script>
                        </fieldset>
                    </s:form>
                </div>
            </div>
        </div>
<!--    </body>
</html>-->