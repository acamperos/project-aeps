<%@page import="java.util.HashMap"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divCropForm">
            <s:form id="formCrop" action="saveCrop" cssClass="form-horizontal">
                <fieldset>         
                    <legend><s:property value="getText('title.cropbasicinfo.crop')" /></legend>
                    <s:hidden name="idCrop"/>                           
                    <div class="control-group">
                        <s:label for="formCrop_nameField" cssClass="control-label req" value="%{getText('text.selectfield.crop')}:"></s:label>
                        <div class="controls">
                            <s:hidden name="idField"/>
                            <s:textfield name="nameField" readonly="true" onclick="listInfo('/viewField.action?selected=crop', 'formCrop_nameField', 'formCrop_idField', 'divListCropForm', 'divCropForm')" />
                            <a class="btn" onclick="listInfo('/viewField.action?selected=crop', 'formCrop_nameField', 'formCrop_idField', 'divListCropForm', 'divCropForm')"><i class="icon-search"></i></a>
                        </div>  
                    </div>  
                    <div class="control-group">
                        <s:label for="formCrop_typeCrop" cssClass="control-label req" value="%{getText('text.croptype.crop')}:"></s:label>
                        <div class="controls">
                            <s:select
                                name="typeCrop"
                                list="#{'1':'Maiz', '2':'Frijol'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>
                    </div>                             
                    <div class="row">
                        <div class="span5" style="padding-left: 20px;">
                            <div class="control-group">
                                <label for="formCrop_lastCrop" class="control-label">
                                    <s:property value="getText('text.lastcrop.crop')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="%{getText('help.lastcropsave.crop')}." data-title="%{getText('title.information')}" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="lastCrop"                                    
                                        list="type_crops" 
                                        listKey="idCroTyp" 
                                        listValue="nameCroTyp"              
                                        headerKey="-1" 
                                        headerValue="---" 
                                        onchange="showOtherElement(this.value, 'divNewCrop')"
                                    />
                                </div>
                            </div>
                        </div>
                        <% String classNewLast="hide"; %>
                        <s:set name="idOtherCrop" value="%{#attr.lastCrop}"/>
                        <s:if test="%{#idOtherCrop==1000000}">
                            <% classNewLast = "";%>
                        </s:if> 
                        <div class="span4 <%= classNewLast %>" style="padding-left: 28px" id="divNewCrop">
                            <div class="control-group">
                                <label for="formCrop_otherCrop" class="control-label req">
                                    <s:property value="getText('text.otherproduct.crop')" />:
                                </label>
                                <div class="controls">
                                    <s:textfield name="otherCrop" />
                                </div>
                            </div>
                        </div>
                    </div>          
                </fieldset>  
                <p class="warnField reqBef"><s:property value="getText('text.requirefields')" /></p>
                <div> 
                    <s:hidden name="page"/>
                    <s:hidden name="actExe"/>    
                    <s:hidden name="newRow" value="1"/>    
                    <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                    <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                        <sj:submit id="btnCrop" type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCrop'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeCrop" validate="true" validateFunction="validationForm"><i class="icon-save"></i> <s:property value="getText('button.prodeventsave.crop')" /></sj:submit>
                    <% } %>
                    <button class="btn btn-large bt_cancel_crop" onclick="resetForm('formCrop'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                </div>    
            </s:form>        
            <script>                
                var action = $("#formCrop_actExe").val();                
                $("#formCrop_performObj").numeric();
                $("#formCrop_performObj").val(parsePointSeparated($("#formCrop_performObj").val()));        
                $.subscribe('completeCrop', function(event, data) {
                    var actExeCrop = $("#formCrop_actExe").val();
                    if (actExeCrop=='create') {
                        $('#btnCrop').on('click', function() {
                            ga('send', 'event', 'Crops', 'click', 'Create');
                        });
                    } else if (actExeCrop=='modify') {
                        $('#btnCrop').on('click', function() {
                            ga('send', 'event', 'Crops', 'click', 'Update');
                        });                
                    }
                    completeFormGetting('dialog-form', 'formCrop', 'divCrops', event.originalEvent.request.responseText);
                    var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                    setTimeout(function() {
                        document.location = "/crop/dataCrop.action?idCrop="+json.idCrop;             
                    }, 2000);
                });
                if($('.pop-over').length) {
                    $('.pop-over').popover();
                }
            </script>
        </div>
        <div class="row-fluid" id="divListCropForm"></div>
    </body>
</html>