<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <s:form id="formProducer" action="saveProducer.action" theme="bootstrap" cssClass="form-horizontal formClassProducer" label="Formulario de un productor">
            <div>
                <%--<s:url id="remoteurl" action="comboTiposDocumentos"/>
                <sj:select 
                    href="%{remoteurl}" 
                    emptyOption="true" --%>
                <%--<s:hidden name="myfield" value="%{#parameters['mparam']}"></s:hidden>--%>
                <s:hidden name="idProducer"/>
                <s:select            
                    tooltip="Seleccione un tipo de documento"
                    label="Tipo de documento:"
                    name="typeIdent" 
                    list="type_ident_producer" 
                    listKey="acronymDocTyp" 
                    listValue="nameDocTyp" 
                    headerKey=" " 
                    headerValue="---"
                    requiredLabel="true"
                    onchange="selValue(this, 'divDigVerPro');
                            selConf(this.value, 'formProducer_num_ident_producer');"
                    />
            </div>
            <div>
                <s:textfield
                    label="Numero de cedula:"
                    requiredLabel="true"
                    name="num_ident_producer"         
                    tooltip="Ingrese su numero de cedula"
                    />
                <!--<img src="../img/search_icon.gif" onclick="listInfo('/aeps-plataforma-mvn/buscarProducer.action&selected=producer', 'identObj', 'params_num_ident_producer', 'params_num_ident_producer', 'params_num_ident_producer', 'Listado de Productores', 1050, 550)" />-->
            </div>
            <div id="divDigVerPro" class="hide">
                <s:textfield
                    label="Digito de verificación:"
                    name="dig_ver_producer"
                    tooltip="Ingrese su digito de verificaci?ón"
                    />
            </div>     
            <div class="row-fluid">
                <div class="span4">
                    <s:textfield
                        label="Primer nombre:"
                        requiredLabel="true"
                        name="names_producer_1"
                        tooltip="Ingrese su primer nombre"
                        />
                </div>  
                <div class="span3" style="padding-left: 28px">
                    <s:textfield
                        label="Segundo nombre:"
                        name="names_producer_2"
                        tooltip="Ingrese su segundo nombre"
                        />
                </div>
            </div> 
            <div class="row-fluid">
                <div class="span4">
                    <s:textfield
                        label="Primer apellido:"
                        requiredLabel="true"
                        name="last_names_producer_1"
                        tooltip="Ingrese su primer apellido"
                        />
                </div>  
                <div class="span3" style="padding-left: 28px">
                    <s:textfield
                        label="Segundo apellido:"
                        name="last_names_producer_2"
                        tooltip="Ingrese su segundo apellido"
                        />
                </div>
            </div>
            <div>
                <s:textfield
                    label="Dirección:"
                    name="direction_producer"
                    tooltip="Ingrese su dirección"
                    />
            </div>
            <div>
                <%--<s:url id="remoteurl" action="comboProducer.action"/>--%> 
                <s:select
                    tooltip="Seleccione un departamento:"
                    requiredLabel="true"
                    label="Departamento"
                    list="department_producer" 
                    listKey="idDep" 
                    listValue="nameDep" 
                    headerKey=" " 
                    headerValue="---"
                    onchange="chargeValues('/aeps-plataforma-mvn/comboProducer.action', 'depId', this.value, 'formProducer_cityPro', 'divMessage')"
                    name="depPro"/>
                <!--            <select onchange="chargeValues('../actions/Actions.php?action=ListarMunXDep', 'depId', this.value, 'params_city_producer', 'divMessage')" name="params[department_producer]" id="params_department_producer">
                            </select>-->
                <!-- <input type="text" name="params[department_producer]" id="params_department_producer"/> -->
            </div>
            <div>
                <s:select
                    tooltip="Seleccione un municipio:"
                    requiredLabel="true"
                    label="Municipio"
                    list="city_producer" 
                    listKey="idMun" 
                    listValue="nameMun" 
                    headerKey=" " 
                    headerValue="---"
                    name="cityPro" />
                <!--            <label for="params_city_producer">Municipio:</label>
                            <select name="params[city_producer]" id="params_city_producer">
                            </select>-->
                <!-- <input type="text" name="params[city_producer]" id="params_city_producer" /> -->
            </div>
            <div>
                <s:textfield
                    label="Teléfono fijo:"
                    name="telephone_producer"
                    tooltip="Ingrese su teléfono fijo"
                    />
            </div>
            <div>
                <s:textfield
                    label="Celular:"
                    name="celphone_producer"
                    tooltip="Ingrese su celular"
                    />
            </div>
            <div>
                <s:textfield
                    label="Correo electrónico:"
                    name="email_producer"
                    tooltip="Ingrese su correo electrónico"
                    />
            </div>  
            <div>   
                <% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
                <script>
//                    var idPro  = $("#formProducer_idProducer").val();
//                    var action = $("#formProducer_actExe").val();
//                    var target = "divBodyLayout";
//                    if (action=="modify") {
//                        target = "trProducer"+idPro;
//                    }
                </script>
                <%--<s:hidden name="actExe" value=""/>--%>
                <s:hidden name="actExe"/>
                <s:hidden name="page"/>
                <!--<input type="submit" class="btn btn-primary" value="Guardar productor" id="submit_492662557">-->
                <%--<sj:submit cssClass="btn btn-inverse" targets="divBodyLayout" onCompleteTopics="completeProducer" value="Guardar productor" validate="true" validateFunction="validationForm"/>--%>
                <sj:submit cssClass="btn btn-inverse" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeProducer" value="Guardar productor" validate="true" validateFunction="validationForm"/>
                <!--<button class="btn btn-inverse" onclick="saveData('saveProducer.action','searchProducer.action?page=<%=pageNow%>','formProducer');">Guardar productor</button>-->
                <button class="btn btn_per bt_cancel_producer" onclick="resetForm('formProducer'); closeWindow();">Cancelar</button>
            </div>
        </s:form>
        <script>
            var idPro  = $("#formProducer_idProducer").val();
            var action = $("#formProducer_actExe").val();
            var page   = $("#formProducer_page").val();
            var target = "divBodyLayout";
            if (action=="modify") {
                //target = "trProducer"+idPro;
            }
//            alert(target);
//            jQuery(document).ready(function () {
//                var options_submit_492662557 = {};
//                options_submit_492662557.jqueryaction = "button";
//                options_submit_492662557.id    = "submit_492662557";
//                options_submit_492662557.oncom = "completeProducer";
////                options_submit_492662557.targets = ""+target;
//                options_submit_492662557.href  = "#";
//                options_submit_492662557.formids = "formProducer";
//                options_submit_492662557.validateFunction = validationForm;
//                options_submit_492662557.validate = true;
//                jQuery.struts2_jquery.bind(jQuery('#submit_492662557'),options_submit_492662557);
//            }); 
//            var requestSent = false;
            $.mask.definitions['h'] = "[3]";
            $("#formProducer_dig_ver_producer").mask("9",{placeholder:""});
            $("#formProducer_telephone_producer").mask("9999999",{placeholder:""});
            $("#formProducer_celphone_producer").mask("h999999999",{placeholder:""});
            $.subscribe('completeProducer', function(event, data) {
                //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                //     '\n\nThe output div should have already been updated with the responseText.');
                //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                //        alert('responseText: \n' + json.info);
//                if(!requestSent) {
//                    requestSent = true;
                    completeFormGetting('dialog-form', 'formProducer', 'divProducers', event.originalEvent.request.responseText);
                    setTimeout( function() {
                        showInfo("searchProducer.action?page="+page, "divConListProducers");
    //                        if(requestSent) $.ajax().abort();  // abort request
                    }, 2000);
//                }
            });
        </script>
    </body>
</html>