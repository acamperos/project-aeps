<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% Integer entTypeId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<s:form id="formFarmSearch" theme="bootstrap" action="searchFarm.action?selected=%{selected}" cssClass="form-horizontal formClassProperty" label="Buscar una finca">
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
                <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFarms" onCompleteTopics="completeFarm"><i class="icon-search"></i></sj:submit>
            </div> 
            <div class="span2">
                <s:submit type="button" cssClass="btn btn-default" onclick="getReportCsv('getReportFarm.action', 'formFarmSearch', 'farmsData.csv')"><i class="icon-file-text"></i> Exportar Datos</s:submit>
            </div>
        </div>
        <script>
            $("#formFarmSearch_name_agronomist").multipleSelect({
                placeholder: "---",
                selectAllText: 'Todos',
                allSelected: 'Todos',
                countSelected: '# de % seleccionados',
                noMatchesFound: 'No coincidencias encontradas'
            });
            $("#formFarmSearch_name_agronomist").multipleSelect('checkAll');
        </script>
    <% } %>
    <s:hidden name="searchFromFarm" value="1"/>    
    <div class="control-group" id="searchBasicFarm">
        <s:textfield cssClass="form-control" name="search_farm" placeholder="Buscar" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListFarms" onCompleteTopics="completeFarm"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasicFarm', 'searchAdvanceFarm', 'formFarmSearch_searchFromFarm', 2)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
        <s:set name="valSel" value="selected"/> 
        <s:if test="%{#valSel.equals('property')}">
            <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
        </s:if>        
    </div>   
    <div id="searchAdvanceFarm" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasicFarm', 'searchAdvanceFarm', 'formFarmSearch_searchFromFarm', 1)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Productor:"
                    name="name_producer"
                    class="input-xlarge uneditable-input"          
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Nombre Finca:"
                    name="name_property"        
                    />
            </div>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Latitud de la Finca:"
                    name="latitude_property"  
                    value=""
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Longitud de la Finca:"
                    name="length_property"   
                    value=""
                    />
            </div>  
        </div>  
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Altitud de la Finca (metros):"
                    name="altitude_property"        
                    value=""
                    />
            </div>             
            <div class="span4" style="padding-left: 28px">
                <s:select
                    label="Departamento"
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
                    label="Municipio"
                    list="city_property" 
                    listKey="idMun" 
                    listValue="nameMun" 
                    headerKey=" " 
                    headerValue="---"
                    name="cityFar" />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:textfield
                    label="Vereda:"
                    name="lane_property"                
                    />
            </div>          
        </div>          
        <!--</fieldset>-->
        <div> 
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListFarms" onCompleteTopics="completeFarm">Buscar Finca <i class="icon-search"></i></sj:submit>
            <s:if test="%{#valSel.equals('property')}">
                <s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout"><i class="icon-rotate-left"></i> Volver al listado</s:a>
            </s:if>
        </div>    
    </div>    
</s:form>        
<script>
    var page   = $("#formFarmSearch_page").val();
    //For property
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
    //chargeValues('../actions/Actions.php?action=ListarDeps', 'depId', '', 'params_department_property', 'divMessage');
</script>