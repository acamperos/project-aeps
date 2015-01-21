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
        <div class="row-fluid" id="divConForm">
            <s:form id="formCropCon" action="saveCon" cssClass="form-horizontal">
                <fieldset>
                    <legend><s:property value="getText('title.controlform.crop')" /></legend>  
                    <div class="row">
                        <div class="span5">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="con.idCon"/>
                            <div class="control-group">
                                <label for="formCropCon_con_dateCon" class="control-label req">
                                    <s:property value="getText('text.controldate.crop')" />:
                                </label>
                                <div class="date controls">
                                    <s:date name="con.dateCon" format="dd/MM/yyyy" var="dateTransformCont"/>
                                    <s:textfield name="con.dateCon" value="%{#dateTransformCont}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <div class="row">
                        <div class="span5">
                            <div class="control-group">
                                <div class="controls radioSelect">
                                    <s:radio
                                        name="con.targetsTypes.idTarTyp"
                                        list="type_tar_typ" 
                                        listKey="idTarTyp" 
                                        listValue="nameTarTyp"            
                                        headerKey="-1" 
                                        headerValue="---"                                                                                
                                        onclick="chargeValuesObjective('con.targetsTypes.idTarTyp', 'divListPest', 'divListWee', 'divListDis');
                                                  chargeValuesControls('/crop/comboControls.action?typeCrop=%{typeCrop}', 'idTarget', 'con.targetsTypes.idTarTyp', 'typeCon', 'formCropCon_con_controlsTypes_idConTyp', 'formCropCon_con_chemicalsControls_idCheCon', 'formCropCon_con_organicControls_idOrgCon', 'divMessage');
                                                  hideInformationControls('divNewObjControlPes', 'divNewObjControlWee', 'divNewObjControlDis', 'divNewProCheCon', 'divNewProOrgCon');"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                    </div>
                    <% String classTarPet="hide"; %>
                    <% String classTarDis="hide"; %>
                    <% String classTarWee="hide"; %>
                    <s:set name="idTarget" value="con.targetsTypes.idTarTyp"/>
                    <s:if test="%{#idTarget==1}">
                        <% classTarPet = "";%>
                    </s:if>
                    <s:elseif test="%{#idTarget==2}">
                        <% classTarWee = "";%>
                    </s:elseif>
                    <s:elseif test="%{#idTarget==3}">
                        <% classTarDis = "";%>
                    </s:elseif>
                                
                    <div class="<%= classTarPet %> row" id="divListPest">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropCon_con_pests_idPes" class="control-label req">
                                    <s:property value="getText('text.targetpest.crop')" />:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="con.pests.idPes"
                                        list="type_pest_con" 
                                        listKey="idPes" 
                                        listValue="namePes"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElement(this.value, 'divNewObjControlPes')"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                        <% String classNewObjConPes="hide"; %>
                        <s:set name="objConPes" value="con.pests.idPes"/>
                        <s:if test="%{#objConPes==1000000}">
                            <% classNewObjConPes = "";%>
                        </s:if> 
                        <div class="<%= classNewObjConPes %>" id="divNewObjControlPes">
                            <div class="span4" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropCon_con_otherPestCon" class="control-label req">
                                        <s:property value="getText('text.newtargetpest.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="con.otherPestCon"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="<%= classTarWee %> row" id="divListWee">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropCon_con_weeds_idWee" class="control-label req">
                                    <s:property value="getText('text.targetweed.crop')" />:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="con.weeds.idWee"
                                        list="type_weeds_con" 
                                        listKey="idWee" 
                                        listValue="nameWee"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElement(this.value, 'divNewObjControlWee')"
                                    />
                                </div>                         
                            </div>                          
                        </div>  
                        <% String classNewObjConWee="hide"; %>
                        <s:set name="objConWee" value="con.weeds.idWee"/>
                        <s:if test="%{#objConWee==1000000}">
                            <% classNewObjConWee = "";%>
                        </s:if> 
                        <div class="<%= classNewObjConWee %>" id="divNewObjControlWee">
                            <div class="span4" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropCon_con_otroWeedCon" class="control-label req">
                                        <s:property value="getText('text.newtargetweed.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="con.otroWeedCon"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="<%= classTarDis %> row" id="divListDis">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropCon_con_diseases_idDis" class="control-label req">
                                    <s:property value="getText('text.targetdiseases.crop')" />:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="con.diseases.idDis"
                                        list="type_dis_con" 
                                        listKey="idDis" 
                                        listValue="nameDis"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElement(this.value, 'divNewObjControlDis')"
                                    />
                                </div>                         
                            </div>                          
                        </div> 
                        <% String classNewObjConDis="hide"; %>
                        <s:set name="objConDis" value="con.diseases.idDis"/>
                        <s:if test="%{#objConDis==1000000}">
                            <% classNewObjConDis = "";%>
                        </s:if> 
                        <div class="<%= classNewObjConDis %>" id="divNewObjControlDis">
                            <div class="span4" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropCon_con_otherDiseaseCon" class="control-label req">
                                        <s:property value="getText('text.newtargetdiseases.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="con.otherDiseaseCon"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropCon_con_controlsTypes_idConTyp" class="control-label req">
                                    <s:property value="getText('text.controltype.crop')" />:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="con.controlsTypes.idConTyp"
                                        list="type_con_typ" 
                                        listKey="idConTyp" 
                                        listValue="nameConType"            
                                        headerKey="-1" 
                                        headerValue="---"       
                                        onchange="showTypeFertilizerControl('formCropCon_con_controlsTypes_idConTyp', 'divOrganicCon', 'divChemicalCon', 'divMechanicCon', 'divMechanizedCon', 'divManualCon');
                                                  chargeValuesControls('/crop/comboControls.action?typeCrop=%{typeCrop}', 'idTarget', 'con.targetsTypes.idTarTyp', 'typeCon', 'formCropCon_con_controlsTypes_idConTyp', 'formCropCon_con_chemicalsControls_idCheCon', 'formCropCon_con_organicControls_idOrgCon', 'divMessage');
                                                  hideInformationControls('divNewObjControlPes', 'divNewObjControlWee', 'divNewObjControlDis', 'divNewProCheCon', 'divNewProOrgCon');"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                    </div>                        
                    <div id="divChemicalCon" class="hide">
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_con_chemicalsControls_idCheCon" class="control-label req">
                                        <s:property value="getText('text.chemicalcontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="con.chemicalsControls.idCheCon"
                                            list="type_prod_che_con" 
                                            listKey="idCheCon" 
                                            listValue="nameCheCon"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElement(this.value, 'divNewProCheCon')"
                                        />
                                    </div>                         
                                </div>                          
                            </div> 
                            <% String classNewProCheCon="hide"; %>
                            <s:set name="chemCon" value="con.chemicalsControls.idCheCon"/>
                            <s:if test="%{#chemCon==1000000}">
                                <% classNewProCheCon = "";%>
                            </s:if> 
                            <div class="<%= classNewProCheCon %>" id="divNewProCheCon">
                                <div class="span4" style="padding-left: 28px">
                                    <div class="control-group">
                                        <label for="formCropCon_con_otherChemicalProductCon" class="control-label req">
                                            <s:property value="getText('text.newchemicalcontrol.crop')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="con.otherChemicalProductCon"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_dosisConChe" class="control-label">
                                        <s:property value="getText('text.dosechemicalcontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="dosisConChe"/>
                                    </div>                         
                                </div>                          
                            </div> 
                            <div class="span4" style="padding-left: 6px">
                                <div class="control-group">
                                    <label for="formCropCon_doseUnitsChe" class="control-label">
                                        <s:property value="getText('text.doseunitchecontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="doseUnitsChe"
                                            list="type_dose_units" 
                                            listKey="idDosUni" 
                                            listValue="nameDosUni"            
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>                            
                    </div>                           
                    <div id="divOrganicCon" class="hide">
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_con_organicControls_idOrgCon" class="control-label req">
                                        <s:property value="getText('text.organiccontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="con.organicControls.idOrgCon"
                                            list="type_prod_org_con" 
                                            listKey="idOrgCon" 
                                            listValue="nameOrgCon"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElement(this.value, 'divNewProOrgCon')"
                                        />
                                    </div>                         
                                </div>                          
                            </div> 
                            <% String classNewProOrgCon="hide"; %>
                            <s:set name="orgCon" value="con.organicControls.idOrgCon"/>
                            <s:if test="%{#orgCon==1000000}">
                                <% classNewProOrgCon = "";%>
                            </s:if> 
                            <div class="<%= classNewProOrgCon %>" id="divNewProOrgCon">
                                <div class="span4" style="padding-left: 28px">
                                    <div class="control-group">
                                        <label for="formCropCon_con_otherOrganicProductCon" class="control-label req">
                                            <s:property value="getText('text.neworganiccontrol.crop')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="con.otherOrganicProductCon"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_dosisConOrg" class="control-label">
                                        <s:property value="getText('text.doseorgcontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="dosisConOrg"/>
                                    </div>                         
                                </div>                          
                            </div> 
                            <div class="span4" style="padding-left: 6px">
                                <div class="control-group">
                                    <label for="formCropCon_doseUnitsOrg" class="control-label">
                                        <s:property value="getText('text.doseunitorgcontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="doseUnitsOrg"
                                            list="type_dose_units" 
                                            listKey="idDosUni" 
                                            listValue="nameDosUni"            
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="divMechanicCon" class="hide">                        
                    </div>                
                    <div id="divMechanizedCon" class="hide">      
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_dosisConMec" class="control-label">
                                        <s:property value="getText('text.dosemeccontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="dosisConMec"/>
                                    </div>                         
                                </div>                          
                            </div> 
                            <div class="span4" style="padding-left: 6px">
                                <div class="control-group">
                                    <label for="formCropCon_doseUnitsMec" class="control-label">
                                        <s:property value="getText('text.doseunitmeccontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="doseUnitsMec"
                                            list="type_dose_units" 
                                            listKey="idDosUni" 
                                            listValue="nameDosUni"            
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="divManualCon" class="hide">      
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropCon_dosisConMan" class="control-label">
                                        <s:property value="getText('text.dosemancontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="dosisConMan"/>
                                    </div>                         
                                </div>                          
                            </div> 
                            <div class="span4" style="padding-left: 6px">
                                <div class="control-group">
                                    <label for="formCropCon_doseUnitsMan" class="control-label">
                                        <s:property value="getText('text.doseunitmancontrol.crop')" />:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="doseUnitsMan"
                                            list="type_dose_units" 
                                            listKey="idDosUni" 
                                            listValue="nameDosUni"            
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <p class="warnField reqBef"><s:property value="getText('text.requirefields')" /></p>
                    <script>
                        $("#formCropCon_con_dateCon").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropCon_con_dateCon").mask("99/99/9999", {placeholder: " "});
                        $("#formCropCon_dosisConOrg").numeric({ negative: false });
                        $("#formCropCon_dosisConOrg").val(parsePointSeparated($("#formCropCon_dosisConOrg").val())); 
                        $("#formCropCon_dosisConChe").numeric({ negative: false });
                        $("#formCropCon_dosisConChe").val(parsePointSeparated($("#formCropCon_dosisConChe").val()));                         
                        showTypeFertilizerControl('formCropCon_con_controlsTypes_idConTyp', 'divOrganicCon', 'divChemicalCon', 'divMechanicCon', 'divMechanizedCon', 'divManualCon');
                    </script>
                    <div id="divBtCon">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropCon'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeCon" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.controlsave.crop')" /></sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropCon'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                    </div>
                </fieldset>
            </s:form>﻿  
            <script>                
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeCon', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropCon', 'divPro', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchCon.action?idCrop="+$("#formCropCon_idCrop").val(), "divListPro");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListConForm"></div>
    </body>
</html>