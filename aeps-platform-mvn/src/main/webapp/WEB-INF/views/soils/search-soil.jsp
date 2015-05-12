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
<s:form id="formRastaSearch" action="searchSoil.action?selected=%{selected}" cssClass="form-horizontal formClassSoil" label="%{getText('title.searchsoil.soil')}">
    <% if (entTypeId==3) { %>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_name_agronomist" cssClass="control-label" value="%{getText('select.agronolist.soil')}:"></s:label>
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
                    <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListRasta" onCompleteTopics="completeSearchSoil"><i class="icon-search"></i></sj:submit>
                </div> 
            </div> 
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportSoil.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.soil')" /></s:submit>
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
            
            $("#formRastaSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: ''+allSelSoil,
                allSelected: ''+allSelSoil,
                countSelected: ''+numSelSoil,
                noMatchesFound: ''+notFoundSoil
            });
            $("#formRastaSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromSoil" value="1"/>    
    <div class="control-group" id="searchBasicSoil">
        <s:textfield cssClass="form-control" name="search_soil" placeholder="%{getText('text.searchsoil.soil')}" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListRasta" onCompleteTopics="completeSearchSoil"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicSoil', 'searchAdvanceSoil', 'formRastaSearch_searchFromSoil', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.soil')" /> </a><i class="icon-chevron-down"></i>
        <s:a cssClass="btn btn-initial" href="listSoil.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.soil')" /></s:a>
        <% if (entTypeId!=3) { %>
            <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportSoil.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.soil')" /></s:submit>
        <% } %>
    </div> 
    <div id="searchAdvanceSoil" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicSoil', 'searchAdvanceSoil', 'formRastaSearch_searchFromSoil', 1)" class="radioSelect"><s:property value="getText('link.simplesearch.soil')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_num_rasta" cssClass="control-label" value="%{getText('text.searchnumrasta.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_rasta" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formRastaSearch_date" cssClass="control-label" value="%{getText('text.searchdaterasta.soil')}:"></s:label>
                    <div class="date controls">
                        <s:textfield name="date" readonly="true" />
                        <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_pendant" cssClass="control-label" value="%{getText('text.searchpendant.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="pendant" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formRastaSearch_altitude" cssClass="control-label" value="%{getText('text.searchaltitude.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="altitude" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_latitude" cssClass="control-label" value="%{getText('text.searchlatitude.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="latitude" />
                    </div>
                </div>
            </div>
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formRastaSearch_length" cssClass="control-label" value="%{getText('text.searchlongitude.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="length" />
                    </div>  
                </div>  
            </div>  
        </div>
        <div class="row-fluid">           
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_ground" cssClass="control-label" value="%{getText('select.searchground.soil')}:"></s:label>
                    <div class="controls">
                        <s:select
                            name="ground"
                            list="{'Plano o Llano', 'Ondulado', 'Montañoso', 'Ondulado y montañoso'}"   
                            headerKey="-1" 
                            headerValue="---" />
                    </div>
                </div>
            </div>
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formRastaSearch_position" cssClass="control-label" value="%{getText('select.searchposition.soil')}:"></s:label>
                    <div class="controls">
                        <s:select
                            name="position"
                            list="{'Meseta', 'Cima', 'Ladera Convexa', 'Ladera Cóncava', 'Ladera Plana', 'Plano', 'Plano con ondulaciones', 'Pie de una elevación'}"           
                            headerKey="-1" 
                            headerValue="---" />
                    </div>    
                </div>    
            </div>    
        </div>    
        <div class="row-fluid">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formRastaSearch_ph" cssClass="control-label" value="%{getText('text.searchph.soil')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="ph" />
                    </div>
                </div>
            </div>
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formRastaSearch_carbonates" cssClass="control-label" value="%{getText('select.searchcarbonates.soil')}:"></s:label>
                    <div class="controls">
                        <s:select
                            name="carbonates"
                            list="{'No tiene', 'Bajos a muy bajos', 'Medios', 'Altos'}"           
                            headerKey="-1" 
                            headerValue="---" />
                    </div>  
                </div>  
            </div>  
        </div>      
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListRasta" onCompleteTopics="completeSearchSoil"><s:property value="getText('button.searchsoil.soil')" /> <i class="icon-search"></i></sj:submit>
            <s:a cssClass="btn btn-initial" href="listSoil.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.soil')" /></s:a>
        </div>
    </div>       
</s:form>        
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";
    $("#formRastaSearch_date").datepicker({dateFormat: 'mm/dd/yy'});
    $("#formRastaSearch_date").mask("99/99/9999", {placeholder: ""});
    $("#formRastaSearch_num_rasta").numeric({decimal: false, negative: false});
    $("#formRastaSearch_pendant").numeric({decimal: false, negative: false});
    $("#formRastaSearch_latitude").numeric();
    $("#formRastaSearch_length").numeric();    
    $("#formRastaSearch_altitude").numeric({decimal: false, negative: false});
    $("#formRastaSearch_ph").numeric();
    $.subscribe('completeSearchSoil', function(event, data) {
       $.unblockUI();
    });
</script>