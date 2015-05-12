<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<div id="divMessMon"></div>
<div id="divListMonGen">
    <%@ include file="../monitorings/info-monitorings.jsp" %>            
</div>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Integer entTypePhyId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<hr class="divider-inner-separator">
<s:form id="formCropMon" action="savePhys" cssClass="form-horizontal">
    <fieldset>
        <legend><s:property value="getText('title.formphysiology.monitoring')" /></legend>
        <div class="control-group">
            <s:hidden name="idCrop"/>
            <s:hidden name="typeCrop"/>
            <s:hidden name="actExe"/>
            <s:hidden name="valueIng" value="phys"/>
            <s:hidden name="phys.idPhyMon"/>
            <label for="formCropMon_phys_emergencePhyMon" class="control-label">
                <s:property value="getText('text.emergencydate.monitoring')" />:
            </label>
            <div class="date controls">
                <s:date name="phys.emergencePhyMon" format="MM/dd/yyyy" var="dateTransformPhy"/>
                <s:textfield name="phys.emergencePhyMon" value="%{#dateTransformPhy}" readonly="true"/>
                <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>                          
        </div>
        <div class="control-group">
            <label for="formCropMon_phys_daysPopulationMonFis" class="control-label">
                <s:property value="getText('text.dayspopulation.monitoring')" />:
            </label>
            <div class="controls">
                <s:textfield name="phys.daysPopulationMonFis"/>
            </div>
        </div>
        <div class="control-group">
            <label for="formCropMon_phys_floweringDatePhyMon" class="control-label">
                <s:property value="getText('text.floweringdate.monitoring')" />:
            </label>
            <div class="date controls">
                <s:date name="phys.floweringDatePhyMon" format="MM/dd/yyyy" var="dateTransformFlow"/>
                <s:textfield name="phys.floweringDatePhyMon" value="%{#dateTransformFlow}" readonly="true"/>
                <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>                          
        </div>
        <% if (coCode.equals("NI")) { %>
            <div class="control-group">
                <label for="formCropMon_phys_iniPrimorPhyMon" class="control-label">
                    <s:property value="getText('text.primordate.monitoring')" />:
                </label>
                <div class="date controls">
                    <s:date name="phys.iniPrimorPhyMon" format="MM/dd/yyyy" var="dateTransformPri"/>
                    <s:textfield name="phys.iniPrimorPhyMon" value="%{#dateTransformPri}" readonly="true"/>
                    <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                    <span class="add-on"><i class="icon-calendar"></i></span>
                </div>                          
            </div>
        <% } %>
        <div class="control-group">
            <label for="formCropMon_phys_percentageReseedingPhyMon" class="control-label">
                <s:property value="getText('text.percentagereseeding.monitoring')" />:
            </label>
            <div class="controls">
                <s:textfield name="phys.percentageReseedingPhyMon" />
            </div>                          
        </div>
        <% if (entTypePhyId!=3) { %>
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
        <% } %>
        <script>
            $("#formCropMon_phys_percentageReseedingPhyMon").numeric({negative: false});
            $("#formCropMon_phys_emergencePhyMon").datepicker({dateFormat: 'mm/dd/yy'});
            $("#formCropMon_phys_emergencePhyMon").mask("99/99/9999", {placeholder: " "});
            $("#formCropMon_phys_floweringDatePhyMon").datepicker({dateFormat: 'mm/dd/yy'});
            $("#formCropMon_phys_floweringDatePhyMon").mask("99/99/9999", {placeholder: " "});
            $("#formCropMon_phys_iniPrimorPhyMon").datepicker({dateFormat: 'mm/dd/yy'});
            $("#formCropMon_phys_iniPrimorPhyMon").mask("99/99/9999", {placeholder: " "});
            $("#formCropMon_phys_daysPopulationMonFis").numeric({ decimal: false, negative: false });
        </script>
    </fieldset>
</s:form>	
<div style="margin-bottom: 15px" id="divBtMonitoring">
    <% String actExeMon   = String.valueOf(request.getAttribute("actExe")); %>
    <% if ((actExeMon=="create" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExeMon=="modify" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
        <% if (entTypePhyId!=3) { %>
            <sj:submit type="button" formIds="formCropMon" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropMon'); addMessageProcess()" targets="divMessage" onCompleteTopics="completePhyMon" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.savephysiology.monitoring')" /></sj:submit>
        <% } %>
    <% } %>
</div>
<script>
    $.subscribe('completePhyMon', function(event, data) {
        completeFormCrop('', 'formCropMon', 'divMessMon', event.originalEvent.request.responseText);
    });
</script>