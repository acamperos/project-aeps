<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<s:form id="formCropSearch" action="searchCrop.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="Busqueda de evento productivos">
    <s:hidden name="searchFrom" value="1"/>    
    <div class="control-group" id="searchBasic">
        <!--<div class="span6">-->
            <s:textfield cssClass="form-control" name="search_crop" placeholder="Buscar" />
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><i class="icon-search"></i></sj:submit>
        <!--</div>-->
        <!--<div class="span2">-->
            <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 1)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
        <!--</div>-->
    </div> 
    <div id="searchAdvance" class="hide">
        <div class="control-group">
            <!--<div class="span2">-->
            <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 2)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
            <!--</div>-->
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_name_producer" cssClass="control-label" value="Nombre del productor:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_producer" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_num_crop" cssClass="control-label" value="Numero del cultivo:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_crop" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_type_doc" cssClass="control-label" value="Tipo de documento:"></s:label>
                    <div class="controls">
                       <s:select
                        name="type_doc"
                        list="type_property_lot" 
                        listKey="idFieTyp" 
                        listValue="nameFieTyp" 
                        onchange="selConf(this.value, 'formCropSearch_num_doc');"
                        headerKey="-1" 
                        headerValue="---" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_num_doc" cssClass="control-label" value="Numero de documento:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_doc" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_num_farm" cssClass="control-label" value="Numero de la finca:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_farm" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_num_field" cssClass="control-label" value="Numero del lote:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_field" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_date_sowing" cssClass="control-label" value="Fecha de siembra"></s:label>
                    <div class="controls">
                        <s:textfield name="date_sowing" />
                    </div>                          
                </div>                          
            </div>     
        </div>
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop">Buscar evento productivo <i class="icon-search"></i></sj:submit>
        </div>
    </div>       
</s:form>        
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";
    $("#formCropSearch_date_sowing").datepicker({dateFormat: 'dd/mm/yy'});
    $("#formCropSearch_date_sowing").mask("99/99/9999", {placeholder: ""});
    $("#formCropSearch_num_crop").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_farm").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_field").numeric({decimal: false, negative: false});
    $.subscribe('completeSearchCrop', function(event, data) {
       $.unblockUI();
    });
</script>