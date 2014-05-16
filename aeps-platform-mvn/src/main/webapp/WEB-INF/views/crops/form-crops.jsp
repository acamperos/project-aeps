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
                    <legend>Informacion basica del cultivo</legend>
                    <s:hidden name="idCrop"/>                           
                    <div class="control-group">
                        <s:label for="formCrop_nameField" cssClass="control-label req" value="Seleccione el lote al cual pertenece:"></s:label>
                        <div class="controls">
                            <s:hidden name="idField"/>
                            <s:textfield name="nameField" readonly="true" onclick="listInfo('/aeps-plataforma-mvn/viewField.action?selected=crop', 'formCrop_nameField', 'formCrop_idField', 'divListCropForm', 'divCropForm')" />
                            <a class="btn" onclick="listInfo('/aeps-plataforma-mvn/viewField.action?selected=crop', 'formCrop_nameField', 'formCrop_idField', 'divListCropForm', 'divCropForm')"><i class="icon-search"></i></a>
                        </div>  
                    </div>  
                    <div class="control-group">
                        <s:label for="formCrop_typeCrop" cssClass="control-label req" value="Tipo de cultivo:"></s:label>
                        <div class="controls">
                            <s:select
                                name="typeCrop"
                                list="#{'1':'Maiz', '2':'Frijol'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>
                    </div>                                 
                    <div class="control-group">
                        <label for="formCrop_performObj" class="control-label req">
                            Objetivo de rendimiento (kg/ha):
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el objetivo de rendimiento de su cultivo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield cssClass="form-control" name="performObj"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="formCrop_lastCrop" class="control-label">
                            Cultivo anterior:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el cultivo anterior." data-title="Información" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:select
                                name="lastCrop"                                    
                                list="type_crops" 
                                listKey="idCroTyp" 
                                listValue="nameCroTyp"              
                                headerKey="-1" 
                                headerValue="---" />
                        </div>
                    </div>
<!--                    <div class="control-group">
                        <s:label for="formCrop_drainPlot" cssClass="control-label req" value="Se hace drenaje a la parcela:"></s:label>
                        <div class="controls radioSelect">
                            <s:radio list="#{'true':'Si', 'false':'No'}" name="drainPlot" />
                        </div>
                    </div>   -->
                </fieldset>  
                <p class="warnField reqBef">Campos Requeridos</p>
                <div> 
                    <s:hidden name="page"/>
                    <s:hidden name="actExe"/>    
                    <s:hidden name="newRow" value="1"/>    
                    <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                    <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                        <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCrop'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeCrop" validate="true" validateFunction="validationForm"><i class="icon-save"></i> Guardar Evento Productivo</sj:submit>
                    <% } %>
                    <button class="btn btn-large bt_cancel_crop" onclick="resetForm('formCrop'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                </div>    
            </s:form>        
            <script>
                //var page = $("#formCrop_page").val();                
                var action = $("#formCrop_actExe").val();                
                $("#formCrop_performObj").numeric();
                $("#formCrop_performObj").val(parsePointSeparated($("#formCrop_performObj").val()));        
                $.subscribe('completeCrop', function(event, data) {
                    completeFormGetting('dialog-form', 'formCrop', 'divCrops', event.originalEvent.request.responseText);
//                    alert(action);
                    var json = jQuery.parseJSON(event.originalEvent.request.responseText);
//                    alert(json.idCrop);
//                    alert(data);
                    setTimeout(function() {
//                        if (action=='create') {
                            document.location = "/aeps-plataforma-mvn/crop/dataCrop.action?idCrop="+json.idCrop;
//                        } else if (action=='modify') {
//                            showInfo("/aeps-plataforma-mvn/crop/showDataCrop.action?idCrop="+json.idCrop, "divDataInfoCrop");
//                        }                      
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