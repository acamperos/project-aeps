<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="java.util.HashMap"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<% HashMap addField    = (HashMap) request.getAttribute("additionals");%>
<% String valueField   = (String) addField.get("selected");%>
<s:form id="formFieldSearch" theme="bootstrap" action="searchField.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="%{getText('title.searchfield.field')}">
    <% if (entTypeId==3) { %>
        <div class="row-fluid">
            <div class="span5">
                <s:select        
                    label="%{getText('select.agronomistlist.field')}:"
                    multiple="multiple"
                    name="name_agronomist" 
                    list="list_agronomist" 
                    listKey="idEnt" 
                    listValue="nameEnt" 
                />
            </div> 
            <div class="span1" style="padding-left: 28px">
                <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFields" onCompleteTopics="completeField"><i class="icon-search"></i></sj:submit>
            </div> 
            <% if (valueField.equals("lot")) {%>
                <div class="span2">
                    <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('/getReportField.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.exportdata.field')" /></s:submit>
                </div>
            <% } %>
        </div>
        <script>
						var allSelField = "";
						var numSelField = "";
						var notFoundField = "";
						if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
							allSelField = "Todos";
							numSelField = "# de % seleccionados";
							notFoundField = "No. coincidencias encontradas";
						}
						
						if(navigator.language=='en-EN' || navigator.language=='en') {
							allSelField = "All";
							numSelField = "# of % selected";
							notFoundField = "Number of results";
						}
						
				
            $("#formFieldSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: ''+allSelField,
                allSelected: ''+allSelField,
                countSelected: ''+numSelField,
                noMatchesFound: ''+notFoundField
            });
            $("#formFieldSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>    
    <s:hidden name="searchFromField" value="1"/>    
    <div class="control-group" id="searchBasicField">
        <s:textfield cssClass="form-control" name="search_field" placeholder="%{getText('text.searchfield.field')}" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFields" onCompleteTopics="completeField"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicField', 'searchAdvanceField', 'formFieldSearch_searchFromField', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.field')" /> </a><i class="icon-chevron-down"></i>
        <s:set name="valSel" value="selected"/> 
        <s:if test="%{#valSel.equals('lot')}">
            <s:a cssClass="btn btn-initial" href="/listField.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.field')" /></s:a>
        </s:if>
        <% if (entTypeId!=3) { %>
            <% if (valueField.equals("lot")) {%>
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('/getReportField.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.field')" /></s:submit>
            <% } %>
        <% } %>
    </div>   
    <div id="searchAdvanceField" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicField', 'searchAdvanceField', 'formFieldSearch_searchFromField', 1); clearForm('formFieldSearch');" class="radioSelect"><s:property value="getText('link.simplesearch.field')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchproducer.field')}:"
                    name="name_producer_lot"
                    />
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchnamefarm.field')}:"
                    name="name_property_lot"   
                    />
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:select
                    label="%{getText('select.searchtypefield.field')}:"
                    name="typeLot"
                    list="type_property_lot" 
                    listKey="idFieTyp" 
                    listValue="nameFieTyp"              
                    headerKey="-1" 
                    headerValue="---" />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchnamefield.field')}:"
                    name="name_lot"                    
                    />
            </div>    
        </div>    
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchlatitude.field')}:"
                    name="latitude_lot"           
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchlongitude.field')}:"
                    name="length_lot"      
                    />
            </div>  
        </div>  
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchaltitude.field')}:"
                    name="altitude_lot"              
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searcharea.field')}:"
                    name="area_lot"                    
                    />
            </div>         
        </div>         
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListFields" onCompleteTopics="completeField"><s:property value="getText('button.searchfield.field')" /> <i class="icon-search"></i></sj:submit>
            <s:if test="%{#valSel.equals('lot')}">
                <s:a cssClass="btn btn-initial" href="/listField.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.field')" /></s:a>
            </s:if>
        </div>    
    </div>    
</s:form>        
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";
    $("#formFieldSearch_latitude_lot").numeric();
    $("#formFieldSearch_length_lot").numeric();
    $("#formFieldSearch_altitude_lot").numeric({decimal: false, negative: false});
    $("#formFieldSearch_area_lot").numeric();
    $("#formFieldSearch_length_degrees_lot").numeric({decimal: false});
    $("#formFieldSearch_length_minutes_lot").numeric({decimal: false});
    $("#formFieldSearch_length_seconds_lot").numeric();
    $("#formFieldSearch_latitude_degrees_lot").numeric({decimal: false});
    $("#formFieldSearch_latitude_minutes_lot").numeric({decimal: false});
    $("#formFieldSearch_latitude_seconds_lot").numeric();
    $.subscribe('completeField', function(event, data) {
       $.unblockUI();
    });
</script>