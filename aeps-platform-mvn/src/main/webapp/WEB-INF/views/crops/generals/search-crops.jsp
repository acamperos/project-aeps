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
<s:form id="formCropSearch" action="searchCrop.action?selected=%{selected}" cssClass="form-horizontal formClassLot" label="%{getText('title.searchcrop.crop')}">
    <% if (entTypeId==3) { %>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_name_agronomist" cssClass="control-label" value="%{getText('select.agronolist.crop')}:"></s:label>
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
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportCrop.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.crop')" /></s:submit>
            </div>    
        </div>
        <script>          
            
            var allSelCrop = "";
            var numSelCrop = "";
            var notFoundCrop = "";
            if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
                allSelCrop = "Todos";
                numSelCrop = "# de % seleccionados";
                notFoundCrop = "No. coincidencias encontradas";
            }

            if(navigator.language=='en-EN' || navigator.language=='en') {
                allSelCrop = "All";
                numSelCrop = "# of % selected";
                notFoundCrop = "Number of results";
            }
            
            $("#formCropSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: ''+allSelCrop,
                allSelected: ''+allSelCrop,
                countSelected: ''+numSelCrop,
                noMatchesFound: ''+notFoundCrop
            });
            $("#formCropSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromCrop" value="1"/>    
    <div class="control-group" id="searchBasicCrop">
        <s:textfield cssClass="form-control" name="search_crop" placeholder="%{getText('text.searchcrop.crop')}" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicCrop', 'searchAdvanceCrop', 'formCropSearch_searchFromCrop', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.crop')" /> </a><i class="icon-chevron-down"></i>
        <s:a cssClass="btn btn-initial" href="listCrop.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.crop')" /></s:a>
        <% if (entTypeId!=3) { %>
            <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportXls('getReportCrop.action', 'selectAllname_agronomist', 'selectItemname_agronomist')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.crop')" /></s:submit>
            <%--<s:submit type="button" formIds="formCropSearch" action="getReportCrop.action" cssClass="btn btn-default" onclick="addMessageProcess();"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.crop')" /></s:submit>--%>
            <!--<a href="/crop/getReportCrop.action" style="float: left;text-align: center;">Export To Excel</a>-->
        <% } %>
    </div> 
    <div id="searchAdvanceCrop" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicCrop', 'searchAdvanceCrop', 'formCropSearch_searchFromCrop', 1)" class="radioSelect"><s:property value="getText('link.simplesearch.crop')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_name_producer" cssClass="control-label" value="%{getText('text.searchnamepro.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_producer" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_idCrop" cssClass="control-label" value="%{getText('text.searchnumcrop.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="idCrop" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_type_doc" cssClass="control-label" value="%{getText('select.searchdocumenttype.crop')}:"></s:label>
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
                    <s:label for="formCropSearch_num_doc" cssClass="control-label" value="%{getText('text.searchdocumentnumber.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_doc" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_num_farm" cssClass="control-label" value="%{getText('text.searchnumfarm.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_farm" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_name_field" cssClass="control-label" value="%{getText('text.searchnamefarm.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_farm" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_num_field" cssClass="control-label" value="%{getText('text.searchnumfield.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="num_field" />
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <s:label for="formCropSearch_name_field" cssClass="control-label" value="%{getText('text.searchnamefield.crop')}:"></s:label>
                    <div class="controls">
                        <s:textfield name="name_field" />
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <s:label for="formCropSearch_date_sowing" cssClass="control-label" value="%{getText('text.searchdatesow.crop')}"></s:label>
                    <div class="date controls">
                        <s:textfield name="date_sowing" readonly="true"/>
                        <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>     
        </div>
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListCrop" onCompleteTopics="completeSearchCrop"><s:property value="getText('button.searchcrop.crop')" /> <i class="icon-search"></i></sj:submit>
            <s:a cssClass="btn btn-initial" href="listCrop.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.crop')" /></s:a>
        </div>
    </div>       
</s:form>        
<script>
    $.mask.definitions['i'] = "[-0-9]";
    $.mask.definitions['f'] = "[-.0-9]";    
    $("#formCropSearch_date_sowing").datepicker({dateFormat: 'mm/dd/yy'});
    $("#formCropSearch_date_sowing").mask("99/99/9999", {placeholder: ""});
    $("#formCropSearch_idCrop").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_farm").numeric({decimal: false, negative: false});
    $("#formCropSearch_num_field").numeric({decimal: false, negative: false});
    $.subscribe('completeSearchCrop', function(event, data) {
       $.unblockUI();
    });
</script>