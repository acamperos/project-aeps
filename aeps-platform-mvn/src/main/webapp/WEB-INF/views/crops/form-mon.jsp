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
        <div class="row-fluid" id="divMonForm">
            <s:form id="formCropMonGen" action="saveMon">
                <fieldset>
                    <legend>Formulario de Monitoreo</legend>
                    <div class="row">
                        <div class="span5 form-horizontal">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="mon.idMon"/>
                            <div class="control-group">
                                <label for="formCropMonGen_mon_dateMon" class="control-label req">
                                    Fecha del monitoreo:
                                </label>
                                <div class="date controls">
                                    <s:date name="mon.dateMon" format="dd/MM/yyyy" var="dateTransformMon"/>
                                    <s:textfield name="mon.dateMon" value="%{#dateTransformMon}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td style="width: 30%"></td>
                                <th>Plaga</th>
                                <th>Enfermedad</th>
                                <th>Maleza</th>
                            </tr>
                            <tr>
                                <th>Hace monitoreo a</th>
                                <td>
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorPestsMon"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorDiseasesMon"/>
                                    </div>
                                </td>
                                <td>
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorWeedsMon"/>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        $("#formCropMonGen_mon_dateMon").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropMonGen_mon_dateMon").mask("99/99/9999", {placeholder: " "});
                    </script>
                    <div id="divBtMon">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeMon" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Monitoreo</sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropMonGen'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>                
                $.subscribe('completeMon', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropMonGen', 'divMon', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/aeps-plataforma-mvn/crop/searchMon.action?idCrop="+$("#formCropMonGen_idCrop").val(), "divListMonGen");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListMonForm"></div>
    </body>
</html>