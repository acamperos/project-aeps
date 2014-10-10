<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<s:form id="formFieldSearch" theme="bootstrap" action="searchField.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="Buscar un lote">
    <% if (entTypeId==3) { %>
        <div class="row-fluid">
            <div class="span5">
                <s:select        
                    label="Listado de agronomos:"
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
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="getReportCsv('getReportField.action', 'formFieldSearch', 'fieldsData.csv')"><i class="icon-file-text"></i> Exportar Datos</s:submit>
            </div>
        </div>
        <script>
            $("#formFieldSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: 'Todos',
                allSelected: 'Todos',
                countSelected: '# de % seleccionados',
                noMatchesFound: 'No coincidencias encontradas'
            });
            $("#formFieldSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>    
    <s:hidden name="searchFromField" value="1"/>    
    <div class="control-group" id="searchBasicField">
        <s:textfield cssClass="form-control" name="search_field" placeholder="Buscar" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFields" onCompleteTopics="completeField"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicField', 'searchAdvanceField', 'formFieldSearch_searchFromField', 2)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
        <s:set name="valSel" value="selected"/> 
        <s:if test="%{#valSel.equals('lot')}">
            <s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
        </s:if>
    </div>   
    <div id="searchAdvanceField" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicField', 'searchAdvanceField', 'formFieldSearch_searchFromField', 1)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Productor:"
                    name="name_producer_lot"
                    />
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Nombre Finca:"
                    name="name_property_lot"   
                    />
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:select
                    label="Tipo lote:"
                    name="typeLot"
                    list="type_property_lot" 
                    listKey="idFieTyp" 
                    listValue="nameFieTyp"              
                    headerKey="-1" 
                    headerValue="---" />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Nombre de Lote:"
                    name="name_lot"                    
                    />
            </div>    
        </div>    
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Latitud del Lote:"
                    name="latitude_lot"           
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Longitud del Lote:"
                    name="length_lot"      
                    />
            </div>  
        </div>  
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Altitud del Lote (metros):"
                    name="altitude_lot"              
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Area del Lote (hectarea):"
                    name="area_lot"                    
                    />
            </div>         
        </div>         
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListFields" onCompleteTopics="completeField">Buscar Lote <i class="icon-search"></i></sj:submit>
            <s:if test="%{#valSel.equals('lot')}">
                <s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
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