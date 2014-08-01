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
        <div class="row-fluid" id="divResForm">
            <s:form id="formCropRes" action="saveResidual" cssClass="form-horizontal">
                <fieldset>
                    <legend>Formulario de manejo de rastrojo</legend>
                    <div class="row">
                        <div class="span5">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="resMan.idResMan"/>                          
                            <div class="control-group">
                                <label for="formCropRes_resMan_dateResMan" class="control-label req">
                                    Fecha de manejo del rastrojo:
                                </label>
                                <div class="date controls">
                                    <s:date name="resMan.dateResMan" format="dd/MM/yyyy" var="dateTransformRes"/>
                                    <s:textfield name="resMan.dateResMan" value="%{#dateTransformRes}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <div class="row">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropRes_resMan_residualsClasification_idResCla" class="control-label req">
                                    Tipo de manejo del rastrojo:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="resMan.residualsClasification.idResCla"
                                        list="type_res_clas" 
                                        listKey="idResCla" 
                                        listValue="nameResCla"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElement(this.value, 'divNewManageStub');"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                        <% String classNewRes="hide"; %>
                        <s:set name="idResidual" value="resMan.residualsClasification.idResCla"/>
                        <s:if test="%{#idResidual==1000000}">
                            <% classNewRes = "";%>
                        </s:if>  
                        <div class="span4 <%= classNewRes %>" id="divNewManageStub" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formCropRes_resMan_otherResidualsManagementResMan" class="control-label req">
                                     Nuevo manejo del rastrojo:
                                </label>
                                <div class="controls">
                                    <s:textfield name="resMan.otherResidualsManagementResMan"/>
                                </div>
                            </div>
                        </div>
                    </div>	
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        $("#formCropRes_resMan_dateResMan").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropRes_resMan_dateResMan").mask("99/99/9999", {placeholder: " "});
                    </script>
                    <div id="divBtRes">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeRes" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Rastrojo</sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropRes'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>    
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeRes', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropRes', 'divRes', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchResidual.action?idCrop="+$("#formCropRes_idCrop").val(), "divListRes");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListResForm"></div>
    </body>
</html>