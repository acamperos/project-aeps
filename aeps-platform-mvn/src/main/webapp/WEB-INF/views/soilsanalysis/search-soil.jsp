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
<s:form id="formSoilSearch" action="searchSoilChemical.action?selected=%{selected}" cssClass="form-horizontal formClassSoil" label="%{getText('title.searchsoil.soilanalysis')}">
    <% if (entTypeId==3) { %>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formSoilSearch_name_agronomist" cssClass="control-label" value="%{getText('select.agronolist.soilanalysis')}:"></s:label>
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
                    <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListSoil" onCompleteTopics="completeSearchSoil"><i class="icon-search"></i></sj:submit>
                </div> 
            </div> 
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportSoilChemical.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.soilanalysis')" /></s:submit>
            </div>   
        </div>
        <script>
            
            var allSelSoil = "";
            var numSelSoil = "";
            var notFoundSoil = "";
            if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
                    allSelSoil = "Todos";
                    numSelSoil = "# de % seleccionados";
                    notFoundSoil = "No. coincidencias encontradas";
            }

            if(navigator.language=='en-EN' || navigator.language=='en') {
                    allSelSoil = "All";
                    numSelSoil = "# of % selected";
                    notFoundSoil = "Number of results";
            }
            
            $("#formSoilSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: ''+allSelSoil,
                allSelected: ''+allSelSoil,
                countSelected: ''+numSelSoil,
                noMatchesFound: ''+notFoundSoil
            });
            $("#formSoilSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromSoil" value="1"/>    
    <div class="control-group" id="searchBasicSoil">
        <s:textfield cssClass="form-control" name="search_soil" placeholder="%{getText('text.searchsoil.soil')}" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListSoil" onCompleteTopics="completeSearchSoil"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicSoil', 'searchAdvanceSoil', 'formSoilSearch_searchFromSoil', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.soilanalysis')" /> </a><i class="icon-chevron-down"></i>
        <s:a cssClass="btn btn-initial" href="listSoilChemical.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.soilanalysis')" /></s:a>
        <% if (entTypeId!=3) { %>
            <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportSoilChemical.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.soilanalysis')" /></s:submit>
        <% } %>
    </div> 
    <div id="searchAdvanceSoil" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicSoil', 'searchAdvanceSoil', 'formSoilSearch_searchFromSoil', 1); clearForm('formSoilSearch');" class="radioSelect"><s:property value="getText('link.simplesearch.soilanalysis')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formSoilSearch_sample_number" cssClass="control-label" value="%{getText('text.searchnumsoil.soilanalysis')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="sample_number" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formSoilSearch_date_sampling" cssClass="control-label" value="%{getText('text.searchdatesoil.soilanalysis')}:"></s:label>
                    <div class="date controls">
                        <s:textfield name="date_sampling" readonly="true" />
                        <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formSoilSearch_id_crop_type" cssClass="control-label" value="%{getText('select.searchcroptype.soilanalysis')}:"></s:label>
                    <div class="controls">
                        <s:select
                            name="id_crop_type"
                            list="#{'1':'Maiz', '2':'Frijol', '3':'Yuca', '4':'Arroz', '5':'Algodón', '6':'Soya', '7':'Pastos'}"           
                            headerKey="-1" 
                            headerValue="---" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formSoilSearch_name_field" cssClass="control-label" value="%{getText('text.searchnamefield.soilanalysis')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_field" />
                    </div>
                </div>
            </div>
        </div> 
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formSoilSearch_name_dep" cssClass="control-label" value="%{getText('select.searchnamedep.soilanalysis')}:"></s:label>
                    <div class="controls">
                        <s:select
                            name="name_dep" 
                            list="department_producer" 
                            listKey="idDep" 
                            listValue="nameDep"          
                            headerKey=" " 
                            headerValue="---"
                            onchange="chargeValues('/comboMunicipalities.action', 'depId', this.value, 'formSoilSearch_name_mun', 'formSoilSearch')"
                            />
                    </div>
                </div>
            </div>
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formSoilSearch_name_mun" cssClass="control-label" value="%{getText('select.searchnamemun.soilanalysis')}:"></s:label>
                    <div class="controls">
                        <s:select
                            list="city_producer" 
                            listKey="idMun" 
                            listValue="nameMun" 
                            headerKey=" " 
                            headerValue="---"
                            name="name_mun" />   
                    </div>      
                </div>      
            </div>      
        </div>    
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListSoil" onCompleteTopics="completeSearchSoil"><s:property value="getText('button.searchsoil.soilanalysis')" /> <i class="icon-search"></i></sj:submit>
            <s:a cssClass="btn btn-initial" href="listSoilChemical.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.soilanalysis')" /></s:a>
        </div>
    </div>       
</s:form>
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";
    $("#formSoilSearch_date_sampling").datepicker({dateFormat: 'mm/dd/yy'});
    $("#formSoilSearch_date_sampling").mask("99/99/9999", {placeholder: ""});
    $("#formSoilSearch_sample_number").numeric({decimal: false, negative: false});
    $.subscribe('completeSearchSoil', function(event, data) {
       $.unblockUI();
    });
</script>