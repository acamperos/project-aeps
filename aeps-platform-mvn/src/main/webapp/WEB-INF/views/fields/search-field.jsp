<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<s:form id="formFieldSearch" theme="bootstrap" action="searchField.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="Buscar un lote">
    <s:hidden name="searchFrom" value="1"/>    
    <div class="control-group" id="searchBasic">
        <s:textfield cssClass="form-control" name="search_farm" placeholder="Buscar" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFields" onCompleteTopics="completeField"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 1)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
    </div>   
    <div id="searchAdvance" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 2)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
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