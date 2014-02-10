<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<% HashMap addAss    = (HashMap) request.getAttribute("additionals");%>
<% String valueAss   = (String) (addAss.get("selected"));%>
<s:form id="formProducerSearch" action="searchProducer.action?selected=%{selected}" theme="bootstrap" cssClass="form-horizontal formClassProducer" label="Buscar productor">
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
                onchange="selValue(this, 'divDigVerPro');
                        selConf(this.value, 'formProducerSearch_num_ident_producer');"
                />
        </div>
        <div class="span3" style="padding-left: 28px">
            <s:textfield
                label="Numero de cedula:"
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
    <div>   
        <sj:submit cssClass="btn btn-primary" onclick="addMessageProcess()" targets="divConListProducers" onCompleteTopics="completeProducer" value="Buscar productor"/>
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