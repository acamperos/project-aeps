<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<s:form id="formProducerSearch" action="searchProducer.action?selected=%{selected}" theme="bootstrap" cssClass="form-horizontal formClassProducer" label="%{getText('title.searchproducer.producer')}">
    <s:hidden name="searchFromProducer" value="1"/>
    <% if (entTypeId==3) { %>
        <div class="row-fluid">
            <div class="span5">
                <s:select        
                    label="%{getText('select.agronolist.producer')}:"
                    multiple="multiple"
                    name="name_agronomist" 
                    list="list_agronomist" 
                    listKey="idEnt" 
                    listValue="nameEnt" 
                />
            </div> 
            <div class="span1" style="padding-left: 28px">
                <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListProducers" onCompleteTopics="completeProducer"><i class="icon-search"></i></sj:submit>
            </div>
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportCsv('getReportProducer.action', 'formProducerSearch', 'producersData.csv')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.producer')" /></s:submit>
                <%--<s:url id="fileDownload" action="getReportProducer.action" includeParams="all" ></s:url>--%>
                <%--<s:a type="button" href="%{fileDownload}" onclick="getReportCsv('getReportProducer.action', 'formProducerSearch', 'divMessage')" cssClass="btn btn-default"><i class="icon-file-text"></i> Exportar Datos</s:a>--%>
            </div>
        </div>        
        <script>
						var allSelProducer = "";
						var numSelProducer = "";
						var notFoundProducer = "";
						if(navigator.language=='es-ES' || navigator.language=='es') {
							allSelProducer = "Todos";
							numSelProducer = "# de % seleccionados";
							notFoundProducer = "No. coincidencias encontradas";
						}
						
						if(navigator.language=='en-EN' || navigator.language=='en') {
							allSelProducer = "All";
							numSelProducer = "# of % selected";
							notFoundProducer = "Number of results";
						}				
				
            $("#formProducerSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: allSelProducer,
                allSelected: allSelProducer,
                countSelected: numSelProducer,
                noMatchesFound: notFoundProducer
            });
            $("#formProducerSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <div class="control-group" id="searchBasicProducer">
        <s:textfield cssClass="form-control" name="search_producer" placeholder="%{getText('text.searchproducer.producer')}" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListProducers" onCompleteTopics="completeProducer"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicProducer', 'searchAdvanceProducer', 'formProducerSearch_searchFromProducer', 2)" class="radioSelect"><s:property value="getText('link.advancesearch.producer')" /> </a><i class="icon-chevron-down"></i>
        <s:set name="valSel" value="selected"/> 
        <s:if test="%{#valSel.equals('producer')}">
            <s:a cssClass="btn btn-initial" href="listProducer.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.producer')" /></s:a>
        </s:if>
        <% if (entTypeId!=3) { %>
            <s:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess(); getReportCsv('getReportProducer.action', 'formProducerSearch', 'producersData.csv')"><i class="icon-file-text"></i> <s:property value="getText('button.dataexport.producer')" /></s:submit>
        <% } %>
    </div>   
    <div id="searchAdvanceProducer" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicProducer', 'searchAdvanceProducer', 'formProducerSearch_searchFromProducer', 1)" class="radioSelect"><s:property value="getText('link.simplesearch.producer')" /> </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:select            
                    label="%{getText('select.searchdocumenttype.producer')}:"
                    name="typeIdent" 
                    list="type_ident_producer" 
                    listKey="acronymDocTyp" 
                    listValue="nameDocTyp" 
                    headerKey="-1" 
                    headerValue="---"
                    onchange="selConf(this.value, 'formProducerSearch_num_ident_producer');
                              showOtherTypeDocument(this.value, 'divSearchInfoCompany', 'divSearchInfoPerson');"
                    />
            </div>
            <div class="span3" style="padding-left: 28px">
                <s:textfield
                    label="%{getText('text.searchdocumentnumber.producer')}:"
                    name="num_ident_producer"         
                    />
            </div>  
        </div>  
        <div class="hide" id="divSearchInfoCompany">
            <div class="row-fluid">
                <div class="span5">
                    <s:textfield
                        label="%{getText('text.searchcompanyname.producer')}"
                        name="nameCompany"
                        />
                </div>  
                <div class="span3" style="padding-left: 28px">
                   <s:textfield
                        label="%{getText('text.searchnamerep.producer')}:"
                        name="firstNameRep"
                        />
                </div>
            </div> 
        </div>   
        <div class="hide" id="divSearchInfoPerson">
            <div class="row-fluid">
                <div class="span5">
                    <s:textfield
                        label="%{getText('text.searchnamepro.producer')}:"
                        name="names_producer_1"
                        />
                </div>  
                <div class="span3" style="padding-left: 28px">
                   <s:textfield
                        label="%{getText('text.searchlastnamepro.producer')}:"
                        name="last_names_producer_1"
                        />
                </div>
            </div> 
        </div> 
        <div class="row-fluid">
            <div class="span5">
                <s:select
                    label="%{getText('text.searchdep.producer')}"
                    name="depPro" 
                    list="department_producer" 
                    listKey="idDep" 
                    listValue="nameDep"          
                    headerKey=" " 
                    headerValue="---"
                    onchange="chargeValues('/comboMunicipalities.action', 'depId', this.value, 'formProducerSearch_cityPro', 'formFarm')"
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:select
                    label="%{getText('text.searchdep.producer')}"
                    list="city_producer" 
                    listKey="idMun" 
                    listValue="nameMun" 
                    headerKey=" " 
                    headerValue="---"
                    name="cityPro" />
            </div>      
        </div>   
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="%{getText('text.searchdir.producer')}:"
                    name="direction_producer"
                    />
            </div>  
            <div class="span3" style="padding-left: 28px">
               <s:textfield
                    label="%{getText('text.searchemail.producer')}:"
                    name="email_producer"
                    />
            </div>
        </div> 
        <div>   
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListProducers" onCompleteTopics="completeProducer"><s:property value="getText('button.searchproducer.producer')" /> <i class="icon-search"></i></sj:submit>
            <s:if test="%{#valSel.equals('producer')}">
                <s:a cssClass="btn btn-initial" href="listProducer.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> <s:property value="getText('link.returnlist.producer')" /></s:a>
            </s:if>
        </div>
    </div>
</s:form>
<script>
    $.mask.definitions['h'] = "[3]";
    $("#formProducerSearch_dig_ver_producer").mask("9",{placeholder:""});
    $("#formProducerSearch_telephone_producer").mask("9999999",{placeholder:""});
    $("#formProducerSearch_celphone_producer").mask("h999999999",{placeholder:""});
    $.subscribe('completeProducer', function(event, data) {
        $.unblockUI();
    });
</script>