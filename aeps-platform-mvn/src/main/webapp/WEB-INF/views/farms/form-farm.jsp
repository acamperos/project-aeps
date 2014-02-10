<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divFarmsForm">
            <div class="span6">	
                <s:form id="formFarm" theme="bootstrap" action="saveFarm.action" cssClass="form-horizontal formClassProperty" label="Formulario de una finca">
                    <fieldset>
                        <div>
                            <s:hidden name="idProducer"/>
                            <s:hidden name="idFarm"/>
                            <s:textfield
                                label="Productor:"
                                name="name_producer"
                                class="input-xlarge uneditable-input"          
                                requiredLabel="true"
                                tooltip="Seleccione un productor en la lupa a la derecha"                        
                                />
                            <img src="/aeps-plataforma-mvn/img/search_icon.gif" alt="Seleccione el productor" onclick="listInfo('/aeps-plataforma-mvn/listProducer.action?selected=property', 'formFarm_name_producer', 'formFarm_idProducer', 'divListFarmsForm', 'divFarmsForm')" />

                            <%-- <s:textfield
                                name="id_producer"
                                style="display: none"
                                tooltip="Seleccione un productor con la lupa a la derecha"                        
                                /> --%>
                            <%-- <a href="#" data-toggle="tooltip" data-placement="right" title="Seleccione el productor">
                                 <img src="../img/search_icon.gif" alt="Seleccione el productor" onclick="listInfo('/aeps-plataforma-mvn/buscarProductor.action&selected=property', 'formFarm_name_producer', 'formFarm_idProductor', 'Listado de Productores', 1050, 550)" /></a>--%>
                        </div>                          
                        <div>
                            <s:textfield
                                label="Nombre de Finca:"
                                name="name_property"        
                                requiredLabel="true"
                                tooltip="Ingrese el nombre de la finca asociada al productor seleccionado"                        
                                />
                            <%--<img src="../img/search_icon.gif" onclick="setPropertyVal('/aeps-plataforma-mvn/buscarFinca.action&selected=property', 'idProductor', 'formFarm_idProductor', 'formFarm_name_property', 'formFarm_idFinca', 'Listado de Fincas', 1050, 550)" />--%>
                        </div>
                        <label class="radio inline" style="margin-left: 50px; margin-bottom: 15px;">
                            <input type="radio" name="option_geo" id="formFarm_option_degrees" value="2" onclick="changeValues(this.value, 'decimals', 'degrees');">
                            Grados
                        </label>
                        <label class="radio inline" style="margin-left: 50px; margin-bottom: 15px;">
                            <input type="radio" name="option_geo" id="formFarm_option_decimal" value="1" checked onclick="changeValues(this.value, 'decimals', 'degrees');">
                            Decimales
                        </label>
                        <%--<s:radio 
                            cssClass="radio inline"
                            cssStyle="margin-left: 50px; margin-bottom: 15px;"
                            name="option_geo"
                            list="{'Grados', 'Decimales'}"
                            id="option_degrees"
                            onclick="changeValues(this.value, 'decimals', 'degrees');"
                            value="2"
                            />
                        <s:radio 
                            cssClass="radio inline"
                            label="Decimales"
                            cssStyle="margin-left: 50px; margin-bottom: 15px;"
                            name="option_geo"
                            id="option_decimal"
                            onclick="changeValues(this.value, 'decimals', 'degrees');"
                            value="1"
                            />--%>
                        <div id="decimals"> 
                            <div>
                                <s:textfield
                                    label="Latitud de la Finca:"
                                    name="latitude_property"       
                                    requiredLabel="true"
                                    tooltip="Ingrese la latitud de la finca"                        
                                    />
                            </div>
                            <div>
                                <s:textfield
                                    label="Longitud de la Finca:"
                                    name="length_property"           
                                    requiredLabel="true"
                                    tooltip="Ingrese la longitud de la finca"                        
                                    />
                            </div>                
                        </div>
                        <div id="degrees" class="hide">   
                            <div>
                                <label for="latitude_degrees_property" style="margin-left: 40px; margin-bottom: 30px;">Latitud de la Finca:</label>
                                <div class="row-fluid">
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="latitude_degrees_property" id="formFarm_latitude_degrees_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="latitude_minutes_property" id="formFarm_latitude_minutes_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="latitude_seconds_property" id="formFarm_latitude_seconds_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                </div>
                            </div>  
                            <div>
                                <label for="length_degrees_property" style="margin-left: 40px; margin-bottom: 30px;">Longitud de la Finca:</label>
                                <div class="row-fluid">
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="length_degrees_property" id="formFarm_length_degrees_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="length_minutes_property" id="formFarm_length_minutes_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="length_seconds_property" id="formFarm_length_seconds_property" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <s:textfield
                                label="Altitud de la Finca (metros):"
                                name="altitude_property"                 
                                requiredLabel="true"
                                tooltip="Ingrese la altitud de la finca en metros"                        
                                />
                        </div>
                        <div>
                            <s:textfield
                                label="Direccion de la Finca:"
                                name="direction_property"                    
                                tooltip="Ingrese la direccion de la finca"                        
                                />
                        </div>              
                        <div>
                            <s:select
                                tooltip="Seleccione un departamento"
                                label="Departamento"
                                name="depFar" 
                                list="department_property" 
                                listKey="idDep" 
                                listValue="nameDep"          
                                requiredLabel="true"
                                headerKey=" " 
                                headerValue="---"
                                onchange="chargeValues('/aeps-plataforma-mvn/comboMunicipalities.action', 'depId', this.value, 'formFarm_cityFar', 'formFarm')"
                                />
                        </div>
                        <div>
                            <s:select
                                tooltip="Seleccione un municipio:"
                                label="Municipio"
                                list="city_property" 
                                listKey="idMun" 
                                listValue="nameMun" 
                                requiredLabel="true"
                                headerKey=" " 
                                headerValue="---"
                                name="cityFar" />
                        </div>
                        <div>
                            <s:textfield
                                label="Vereda:"
                                name="lane_property"                
                                requiredLabel="true"
                                tooltip="Ingrese la vereda"                        
                                />
                        </div>          
                    </fieldset>
                    <div> 
                        <s:hidden name="page"/>
                        <s:hidden name="actExe"/>
                        <sj:submit cssClass="btn btn-inverse" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeFarm" value="Guardar Finca" validate="true" validateFunction="validationForm"/>
                        <!--<button class="btn btn_per bt_send_property" onclick="sendForm('../actions/Actions.php?action=AgregarFinca', 'formProperty', 'divMessage')">Guardar informaci&oacute;n</button>-->
                        <button class="btn btn_per bt_cancel_producer" onclick="resetForm('formFarm'); closeWindow();">Cancelar</button>
                    </div>    
                </s:form>        
                <script>
                    var page   = $("#formFarm_page").val();
                    //For property
                    // $.mask.definitions['i'] = "[-0-9]";
                    $.mask.definitions['f'] = "[-.0-9]";
                    $("#formFarm_latitude_property").numeric();
                    $("#formFarm_length_property").numeric();
                    $("#formFarm_altitude_property").numeric({decimal: false, negative: false});
                    $("#formFarm_length_degrees_property").numeric({decimal: false});
                    $("#formFarm_length_minutes_property").numeric({decimal: false});
                    $("#formFarm_length_seconds_property").numeric();
                    $("#formFarm_latitude_degrees_property").numeric({decimal: false});
                    $("#formFarm_latitude_minutes_property").numeric({decimal: false});
                    $("#formFarm_latitude_seconds_property").numeric();
                    $.subscribe('completeFarm', function(event, data) {
                        //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                        //     '\n\nThe output div should have already been updated with the responseText.');
                        //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                        //        alert('responseText: \n' + json.info);
//                        completeForm('dialog-form', 'formFarm', event.originalEvent.request.responseText);
                        completeFormGetting('dialog-form', 'formFarm', 'divFarms', event.originalEvent.request.responseText);
                        setTimeout( function() {
                            showInfo("searchFarm.action?page="+page, "divConListFarms");
                        }, 2000);
                    });
                    //chargeValues('../actions/Actions.php?action=ListarDeps', 'depId', '', 'params_department_property', 'divMessage');
                </script>
            </div>
            <div class="span6">
                <div class="alert fade in">
                    <strong>Recuerde que en Colombia:</strong>
                    <br /><p></p> 
                    <p>Las latitudes en decimales se encuentran entre -4.3 y 13.5</p>
                    <p>Las longitudes en decimales se encuentran entre -81.8 y -66</p>
                    <p>Las latitudes en grados se encuentran entre -5 y 14</p>
                    <p>Las longitudes en grados se encuentran entre -82 y -66</p>
                    <p>Las altitudes se encuentran entre 0 y 9000 (metros)</p>			
                </div>
            </div>
        </div>
        <div id="divListFarmsForm"></div>
    </body>
</html>