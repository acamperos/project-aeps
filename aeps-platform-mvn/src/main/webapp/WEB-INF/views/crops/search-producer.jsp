<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<s:form id="formProducerSearch" action="searchProducer.action?selected=%{selected}" theme="bootstrap" cssClass="form-horizontal formClassProducer" label="Buscar un productor">
    <s:hidden name="searchFrom" value="1"/>    
    <div class="control-group" id="searchBasic">
        <s:textfield cssClass="form-control" name="search_producer" placeholder="Buscar" theme="simple" />
        <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" theme="simple" targets="divConListProducers" onCompleteTopics="completeProducer"><i class="icon-search"></i></sj:submit>
        <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 1)" class="radioSelect">Busqueda avanzada </a><i class="icon-chevron-down"></i>
    </div>   
    <div id="searchAdvance" class="hide">
        <div class="control-group">
            <a onclick="showSearchAdvance('searchBasic', 'searchAdvance', 'searchFrom', 2)" class="radioSelect">Busqueda sencilla </a><i class="icon-chevron-up"></i>
        </div>
        <div class="row-fluid">
            <div class="span5">
                <s:select            
                    label="Tipo de documento:"
                    name="typeIdent" 
                    list="type_ident_producer" 
                    listKey="acronymDocTyp" 
                    listValue="nameDocTyp" 
                    headerKey=" " 
                    headerValue="---"
                    onchange="selConf(this.value, 'formProducerSearch_num_ident_producer');"
                    />
            </div>
            <div class="span3" style="padding-left: 28px">
                <s:textfield
                    label="Numero de documento:"
                    name="num_ident_producer"         
                    />
            </div>  
        </div>  
        <div class="row-fluid">
            <div class="span5">
                <s:textfield
                    label="Primer nombre:"
                    name="names_producer_1"
                    />
            </div>  
            <div class="span3" style="padding-left: 28px">
               <s:textfield
                    label="Primer apellido:"
                    name="last_names_producer_1"
                    />
            </div>
        </div> 
        <div class="row-fluid">
            <div class="span5">
                <s:select
                    label="Departamento"
                    name="depPro" 
                    list="department_producer" 
                    listKey="idDep" 
                    listValue="nameDep"          
                    headerKey=" " 
                    headerValue="---"
                    onchange="chargeValues('/aeps-plataforma-mvn/comboMunicipalities.action', 'depId', this.value, 'formProducerSearch_cityPro', 'formFarm')"
                    />
            </div>
            <div class="span4" style="padding-left: 28px">
                <s:select
                    label="Municipio"
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
                    label="Direccion:"
                    name="direction_producer"
                    />
            </div>  
            <div class="span3" style="padding-left: 28px">
               <s:textfield
                    label="Correo:"
                    name="email_producer"
                    />
            </div>
        </div> 
        <div>   
            <sj:submit type="button" cssClass="btn btn-default" onclick="addMessageProcess()" targets="divConListProducers" onCompleteTopics="completeProducer">Buscar Productor <i class="icon-search"></i></sj:submit>
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