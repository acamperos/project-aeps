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
                                <td style="width: 230px;">
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorPestsMon" onclick="optSel('mon.monitorPestsMon', 'divMonPest')" />
                                        <div id="divMonPest" class="span2 hide control-group" style="margin-top: 30px;">
                                            <s:select
                                                name="mon.pests.idPes"
                                                list="type_pest_con" 
                                                listKey="idPes" 
                                                listValue="namePes"            
                                                headerKey="-1" 
                                                headerValue="---"
                                                onchange="showOtherElement(this.value, 'divNewObjControlPes')"
                                            />
                                            <s:textfield name="mon.perImpactPestMon" placeholder="Porcentaje de incidencia" />
                                        </div>
                                    </div>
                                </td>
                                <td style="width: 230px;">
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorDiseasesMon" onclick="optSel('mon.monitorDiseasesMon', 'divMonDisease')"/>
                                        <div id="divMonDisease" class="span2 hide control-group" style="margin-top: 30px;">
                                            <s:select
                                                name="mon.diseases.idDis"
                                                list="type_dis_con" 
                                                listKey="idDis" 
                                                listValue="nameDis"            
                                                headerKey="-1" 
                                                headerValue="---"
                                                onchange="showOtherElement(this.value, 'divNewObjControlDis')"
                                            />
                                            <s:textfield name="mon.perImpactDiseaseMon" placeholder="Porcentaje de incidencia" />
                                        </div>
                                    </div>
                                </td>
                                <td style="width: 230px;">
                                    <div class="controls radioSelect">
                                        <s:radio list="#{'true':'Si', 'false':'No'}" name="mon.monitorWeedsMon" onclick="optSel('mon.monitorWeedsMon', 'divMonWeed')"/>
                                        <div id="divMonWeed" class="span2 hide control-group" style="margin-top: 30px;">
                                            <s:select
                                                name="mon.weeds.idWee"
                                                list="type_weeds_con" 
                                                listKey="idWee" 
                                                listValue="nameWee"            
                                                headerKey="-1" 
                                                headerValue="---"
                                                onchange="showOtherElement(this.value, 'divNewObjControlWee')"
                                            />
                                            <s:textfield name="mon.perImpactWeedMon" placeholder="Porcentaje de incidencia" />                                            
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        optSel('mon.monitorPestsMon', 'divMonPest');
                        optSel('mon.monitorDiseasesMon', 'divMonDisease');
                        optSel('mon.monitorWeedsMon', 'divMonWeed');
                        $("#formCropMonGen_mon_dateMon").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropMonGen_mon_dateMon").mask("99/99/9999", {placeholder: " "});
                    </script>
                    <div id="divBtMon">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropMonGen'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeMon" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Monitoreo</sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropMonGen'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>       
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeMon', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropMonGen', 'divMon', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchMon.action?idCrop="+$("#formCropMonGen_idCrop").val(), "divListMonGen");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListMonForm"></div>
    </body>
</html>