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
        <div class="row-fluid" id="divPrepForm">
            <s:form id="formCropPrep" action="savePrep" cssClass="form-horizontal">
                <fieldset>
                    <legend>Formulario de preparación</legend>
                    <div class="row">
                        <div class="span5">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="prep.idPrep"/>
                            <div class="control-group">
                                <label for="formCropPrep_prep_preparationsTypes_idPreTyp" class="control-label req">
                                    Tipo de preparación:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="prep.preparationsTypes.idPreTyp"
                                        list="type_prep_typ" 
                                        listKey="idPreTyp" 
                                        listValue="namePreTyp"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElementPrep(this.value, 'divNewPasses', 'divNewTypePrep', 'lblDepthPrep')"
                                    />
                                </div>                          
                            </div>                          
                        </div>
                        <div class="span4" style="padding-left: 28px">                            
                            <div class="control-group">
                                <label for="formCropPrep_prep_datePrep" class="control-label req">
                                    Fecha de trabajo:
                                </label>
                                <div class="date controls">
                                    <s:date name="prep.datePrep" format="dd/MM/yyyy" var="dateTransformPrep"/>
                                    <s:textfield name="prep.datePrep" value="%{#dateTransformPrep}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <div class="row hide" id="divNewPasses">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropPrep_prep_depthPrep" id="lblDepthPrep" class="control-label req">
                                     Profundidad del trabajo (cm): 
                                </label>
                                <div class="controls">
                                    <s:number name="prep.depthPrep" type="integer" var="depthPreparation" />
                                    <s:textfield name="prep.depthPrep" value="%{#depthPreparation}"/>
                                </div> 
                            </div>                          
                        </div>     
                        <div class="span4" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formCropPrep_prep_passingsNumberPrep" id="lblPassingsNumberPrep" class="control-label">
                                    Numero de pases:
                                </label>
                                <div class="controls">
                                    <s:textfield name="prep.passingsNumberPrep"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hide" id="divNewTypePrep">
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropPrep_prep_otherPreparationTypePrep" class="control-label req">
                                        Nuevo tipo de preparación:
                                    </label>
                                    <div class="controls radioSelect">
                                        <s:textfield name="prep.otherPreparationTypePrep"/>
                                    </div>                         
                                </div>                         
                            </div>
                        </div>
                    </div>
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        showOtherElementPrep($("#formCropPrep_prep_preparationsTypes_idPreTyp").val(), 'divNewPasses', 'divNewTypePrep');
                        $("#formCropPrep_prep_datePrep").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropPrep_prep_datePrep").mask("99/99/9999", {placeholder: " "});
                        $("#formCropPrep_prep_depthPrep").numeric({decimal: false, negative: false});
                        $("#formCropPrep_prep_passingsNumberPrep").numeric({decimal: false, negative: false});
                        $("#formCropPrep_prep_depthPrep").val(parsePointSeparated($("#formCropPrep_prep_depthPrep").val())); 
                    </script>
                    <div id="divBtPrep">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropPrep'); addMessageProcess()" targets="divMessage" onCompleteTopics="completePrep" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Preparación</sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropPrep'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>    
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completePrep', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropPrep', 'divPrep', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchPrep.action?idCrop="+$("#formCropPrep_idCrop").val(), "divListPrep");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListPrepForm"></div>
    </body>
</html>