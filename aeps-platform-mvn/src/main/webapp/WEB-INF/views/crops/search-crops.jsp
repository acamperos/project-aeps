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
<s:form id="formCropSearch" action="searchCrop.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="Busqueda de evento productivos">
    <% if (entTypeId==3) { %>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_name_agronomist" cssClass="control-label" value="Listado de agronomos:"></s:label>
                    <s:select        
                        multiple="multiple"
                        name="name_agronomist" 
                        list="list_agronomist" 
                        listKey="idEnt" 
                        listValue="nameEnt" 
                    />
                </div> 
            </div> 
            <div class="span0">
                <div class="control-group">
                    <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><i class="icon-search"></i></sj:submit>
                </div> 
            </div> 
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="getReportCsv('getReportCrop.action', 'formCropSearch', 'cropsData.csv')"><i class="icon-file-text"></i> Exportar Datos</s:submit>
            </div>    
        </div>
        <script>
            $("#formCropSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: 'Todos',
                allSelected: 'Todos',
                countSelected: '# de % seleccionados',
                noMatchesFound: 'No coincidencias encontradas'
            });
            $("#formCropSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromCrop" value="1"/>    
    <div class="control-group" id="searchBasicCrop">
        <!--<div class="span6">-->
            <s:textfield cssClass="form-control" name="search_crop" placeholder="Buscar" />
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><i class="icon-search"></i></sj:submit>
                    <!--</div>-->
        <!--<div class="span2">-->
            <a onclick="showSearchAdvance('searchBasicCrop', 'searchAdvanceCrop', 'formCropSearch_searchFromCrop', 2)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
            <s:a cssClass="btn btn-initial" href="listCrop.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
        <!--</div>-->
    </div> 
    <div id="searchAdvanceCrop" class="hide">
        <div class="control-group">
            <!--<div class="span2">-->
            <a onclick="showSearchAdvance('searchBasicCrop', 'searchAdvanceCrop', 'formCropSearch_searchFromCrop', 1)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
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
                    <s:label for="formCropSearch_idCrop" cssClass="control-label" value="Numero del cultivo:"></s:label>
                    <div class="controls">
                        <s:textfield name="idCrop" />
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
                        list="type_ident_producer" 
                        listKey="acronymDocTyp" 
                        listValue="nameDocTyp" 
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
                    <s:label for="formCropSearch_name_field" cssClass="control-label" value="Nombre de la finca:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_farm" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_num_field" cssClass="control-label" value="Numero del lote:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_field" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_name_field" cssClass="control-label" value="Nombre del lote:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_field" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_date_sowing" cssClass="control-label" value="Fecha de siembra"></s:label>
                    <div class="date controls">
                        <s:textfield name="date_sowing" readonly="true"/>
                        <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>     
        </div>
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop">Buscar evento productivo <i class="icon-search"></i></sj:submit>
            <s:a cssClass="btn btn-initial" href="listCrop.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
        </div>
    </div>       
</s:form>        
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";    
    $("#formCropSearch_date_sowing").datepicker({dateFormat: 'dd/mm/yy'});
    $("#formCropSearch_date_sowing").mask("99/99/9999", {placeholder: ""});
    $("#formCropSearch_idCrop").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_farm").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_field").numeric({decimal: false, negative: false});
    $.subscribe('completeSearchCrop', function(event, data) {
       $.unblockUI();
    });
</script>