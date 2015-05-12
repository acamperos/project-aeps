<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="java.util.HashMap"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<% HashMap addFarm    = (HashMap) request.getAttribute("additionals");%>
<% String valueFarm   = (String) (addFarm.get("selected"));%>
<s:form id="formFarmSearch" theme="bootstrap" action="searchFarm.action?selected=%{selected}" cssClass="form-horizontal formClassProperty" label="%{getText('title.searchfarm.farm')}">
    <% if (entTypeId==3) { %>
        <div class="row-fluid">
            <div class="span5">
                <s:select        
                    label="%{getText('select.agronolist.farm')}:"
                    multiple="multiple"
                    name="name_agronomist" 
                    list="list_agronomist" 
                    listKey="idEnt" 
                    listValue="nameEnt" 
                />
            </div> 
            <div class="span1" style="padding-left: 28px">
                <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFarms" onCompleteTopics="completeFarm"><i class="icon-search"></i></sj:submit>
            </div> 
            <% if (valueFarm.equals("property")) {%>
                <div class="span2">
                    <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportFarm.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.farm')" /></s:submit>
                </div>
            <% } %>
        </div>
        <script>
            var allSelFarm = "";
            var numSelFarm = "";
            var notFoundFarm = "";
            if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
                    allSelFarm = "Todos";
                    numSelFarm = "# de % seleccionados";
                    notFoundFarm = "No. coincidencias encontradas";
            }

            if(navigator.language=='en-EN' || navigator.language=='en') {
                    allSelFarm = "All";
                    numSelFarm = "# of % selected";
                    notFoundFarm = "Number of results";
            }
						
            $("#formFarmSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: ''+allSelFarm,
                allSelected: ''+allSelFarm,
                countSelected: ''+numSelFarm,
                noMatchesFound: ''+notFoundFarm
            });
            $("#formFarmSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromFarm" value="1"/>    
    <div class="control-group" id="searchBasicFarm">
        <s:textfield cssClass="form-control" name="search_farm" placeholder="%{getText('text.searchfarm.farm')}" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFarms" onCompleteTopics="completeFarm"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicFarm', 'searchAdvanceFarm', 'formFarmSearch_searchFromFarm', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.farm')" /> </a><i class="icon-chevron-down"></i>
        <s:set name="valSel" value="selected"/> 
        <s:if test="%{#valSel.equals('property')}">
            <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.farm')" /></s:a>
        </s:if>     
        <% if (entTypeId!=3) { %>
            <% if (valueFarm.equals("property")) {%>
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportFarm.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.farm')" /></s:submit>
            <% } %>
        <% } %>
    </div>   
    <div id="searchAdvanceFarm" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicFarm', 'searchAdvanceFarm', 'formFarmSearch_searchFromFarm', 1)" class="radioSelect"><s:property value="getText('link.simplesearch.farm')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchproducer.farm')}:"
                    name="name_producer"
                    class="input-xlarge uneditable-input"          
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchnamefarm.farm')}:"
                    name="name_property"        
                    />
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchlatitude.farm')}:"
                    name="latitude_property"  
                    value=""
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchlongitude.farm')}:"
                    name="length_property"   
                    value=""
                    />
            </div>  
        </div>  
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchaltitude.farm')}:"
                    name="altitude_property"        
                    value=""
                    />
            </div>             
            <div class="span4" style="padding-left: 28px">
                <s:select
                    label="%{getText('select.searchdep.farm')}"
                    name="depFar" 
                    list="department_property" 
                    listKey="idDep" 
                    listValue="nameDep"          
                    headerKey=" " 
                    headerValue="---"
                    onchange="chargeValues('/comboMunicipalities.action', 'depId', this.value, 'formFarmSearch_cityFar', 'formFarmSearch')"
                    />
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:select
                    label="%{getText('select.searchmun.farm')}"
                    list="city_property" 
                    listKey="idMun" 
                    listValue="nameMun" 
                    headerKey=" " 
                    headerValue="---"
                    name="cityFar" />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchcom.farm')}:"
                    name="lane_property"                
                    />
            </div>          
        </div>          
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListFarms" onCompleteTopics="completeFarm"><s:property value="getText('button.searchfarm.farm')" /> <i class="icon-search"></i></sj:submit>
            <s:if test="%{#valSel.equals('property')}">
                <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.farm')" /></s:a>
            </s:if>
        </div>    
    </div>    
</s:form>        
<script>
    var page   = $("#formFarmSearch_page").val();
    // $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";
    $("#formFarmSearch_latitude_property").numeric();
    $("#formFarmSearch_length_property").numeric();
    $("#formFarmSearch_altitude_property").numeric({decimal: false, negative: false});
    $("#formFarmSearch_length_degrees_property").numeric({decimal: false});
    $("#formFarmSearch_length_minutes_property").numeric({decimal: false});
    $("#formFarmSearch_length_seconds_property").numeric();
    $("#formFarmSearch_latitude_degrees_property").numeric({decimal: false});
    $("#formFarmSearch_latitude_minutes_property").numeric({decimal: false});
    $("#formFarmSearch_latitude_seconds_property").numeric();
    $.subscribe('completeFarm', function(event, data) {
        $.unblockUI();
//        completeFormGetting('dialog-form', 'formFarmSearch', 'divFarms', event.originalEvent.request.responseText);
//        setTimeout( function() {
//            showInfo("searchFarm.action?page="+page, "divBodyLayout");
//        }, 2000);
    });
</script>