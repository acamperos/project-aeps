<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <%--<sj:head jqueryui="false"/>
        <sb:head includeScripts="true" includeScriptsValidation="true"/>--%>

        <!--    <style type="text/css">
                body {
                    padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
                }
            </style>-->

        <%--<s:form id="formProductor" action="crearProductor" theme="bootstrap" onsubmit="return false;" cssClass="form-horizontal formClassProductor" label="Formulario de un productor">--%>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divFieldsForm">
            <div class="span6">	
                <%--<form method="post" class="formClassProperty" id="formProperty" submit="return false;">--%>	
                <s:form id="formField" theme="bootstrap" action="saveField.action" cssClass="form-horizontal formClassLot" label="Formulario de un lote">
                    <fieldset>
                        <div>
                            <s:hidden name="idProducer"/>
                            <s:hidden name="idFarm"/>
                            <s:hidden name="idField"/>
                            <s:textfield
                                label="Productor:"
                                name="name_producer_lot"
                                class="input-xlarge uneditable-input"                        
                                tooltip="Seleccione un productor con la lupa a la derecha"                        
                                />
                            <img src="/aeps-plataforma-mvn/img/search_icon.gif" alt="Seleccione el productor" onclick="listInfo('/aeps-plataforma-mvn/searchProducer.action?selected=property', 'formField_name_producer_lot', 'formField_idProducer', 'divListFieldsForm', 'divFieldsForm')" />
                            <%-- <a href="#" data-toggle="tooltip" data-placement="right" title="Seleccione el productor">
                                                        
                                                </a>--%>
                        </div>                          
                        <div id="divPropertyLot">
                            <s:textfield
                                label="Seleccione la Finca:"
                                name="name_property_lot"                    
                                tooltip="Seleccione la finca en la lupa a la derecha"                        
                                />
                            <img src="/aeps-plataforma-mvn/img/search_icon.gif" onclick="setPropertyGeneral('/aeps-plataforma-mvn/searchFarm.action&selected=property', 'idProducer', 'formField_idProducer', 'formField_name_property_lot', 'formField_idFarm', 'divListFieldsForm', 'divFieldsForm')" />
                            <%--<img src="../img/search_icon.gif" onclick="setPropertyVal('../actions/Actions.php?action=BuscarFincasXPro&selected=property', 'producer', 'params_id_producer', 'params_name_property', 'params_name_property', 'Listado de Fincas', 1050, 550)" />--%>
                        </div>
                        <div>
                            <s:select
                                tooltip="Seleccione que tipo de lote es"
                                label="El lote es:"
                                name="typeLot"
                                list="type_property_lot" 
                                listKey="idFieTyp" 
                                listValue="nameFieTyp"              
                                headerKey=" " 
                                headerValue="---" />
                        </div>
                        <div>
                            <s:textfield
                                label="Nombre de Lote:"
                                name="name_lot"                    
                                tooltip="Ingrese el nombre del lote asociado a la finca seleccionada"                        
                                />
                        </div>    
                        <label class="radio inline" style="margin-left: 50px; margin-bottom: 15px;">
                            <input type="radio" name="option_geo_lot" id="formField_option_degrees_lot" value="2" onclick="changeValues(this.value, 'decimals_lot', 'degrees_lot');">
                            Grados
                        </label>
                        <label class="radio inline" style="margin-left: 50px; margin-bottom: 15px;">
                            <input type="radio" name="option_geo_lot" id="formField_option_decimal_lot" value="1" checked onclick="changeValues(this.value, 'decimals_lot', 'degrees_lot');">
                            Decimales
                        </label>
                        <div id="decimals_lot"> 
                            <div>
                                <s:textfield
                                    label="Latitud del Lote:"
                                    name="latitude_lot"                    
                                    tooltip="Ingrese la latitud del lote"                        
                                    />
                            </div>
                            <div>
                                <s:textfield
                                    label="Longitud del Lote:"
                                    name="length_lot"                    
                                    tooltip="Ingrese la longitud del lote"                        
                                    />
                            </div>                
                        </div>
                        <div id="degrees_lot" class="hide">   
                            <div>
                                <label for="latitude_degrees_lot" style="margin-left: 40px; margin-bottom: 30px;">Latitud del Lote:</label>
                                <div class="row-fluid">
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="latitude_degrees_lot" id="formField_latitude_degrees_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="latitude_minutes_lot" id="formField_latitude_minutes_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="latitude_seconds_lot" id="formField_latitude_seconds_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                </div>
                            </div>  
                            <div>
                                <label for="length_degrees_lot" style="margin-left: 40px; margin-bottom: 30px;">Longitud del Lote:</label>
                                <div class="row-fluid">
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="length_degrees_lot" id="formField_length_degrees_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="length_minutes_lot" id="formField_length_minutes_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend" style="width: 100px">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="length_seconds_lot" id="formField_length_seconds_lot" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <s:textfield
                                label="Altitud del Lote (metros):"
                                name="altitude_lot"                    
                                tooltip="Ingrese la altitud del lote en metros"                        
                                />
                        </div>
                        <div>
                            <s:textfield
                                label="Area del Lote (hectarea):"
                                name="area_lot"                    
                                tooltip="Ingrese el area del lote"                        
                                />
                        </div>         
                    </fieldset>				
                    <div> 
                        <s:hidden name="actExe"/>
                        <sj:submit cssClass="btn btn-inverse" value="Guardar" onCompleteTopics="completeField" validate="true" validateFunction="validationForm" />
                        <!--<button class="btn btn_per bt_send_lot" onclick="sendForm('../actions/Actions.php?action=saveField', 'formLot', 'divMessage')">Guardar informaci&oacute;n</button>-->
                        <button class="btn btn_per bt_cancel_producer" onclick="resetForm('formField');
                                    closeWindow();">Cancelar</button>
                    </div>    
                </s:form>        
                <script>
                                //For Lot
                                $.mask.definitions['i'] = "[-0-9]";
                                $.mask.definitions['f'] = "[-.0-9]";
                                /*$("#params_latitude_lot").mask("f?9fffffff",{placeholder:" "});
                                 $("#params_length_lot").mask("f9?fffffff",{placeholder:" "});
                                 $("#params_altitude_lot").mask("9?999",{placeholder:" "});
                                 $("#params_area_lot").mask("9?999",{placeholder:" "});*/

                                $("#formField_latitude_lot").numeric();
                                $("#formField_length_lot").numeric();
                                $("#formField_altitude_lot").numeric({decimal: false, negative: false});
                                $("#formField_area_lot").numeric();

                                /*$("#params_length_degrees_lot").mask("9?fffffff",{placeholder:" "});
                                 $("#params_length_minutes_lot").mask("9?fffffff",{placeholder:" "});
                                 $("#params_length_seconds_lot").mask("9?fffffff",{placeholder:" "});*/
                                $("#formField_length_degrees_lot").numeric({decimal: false});
                                $("#formField_length_minutes_lot").numeric({decimal: false});
                                $("#formField_length_seconds_lot").numeric();
                                $("#formField_latitude_degrees_lot").numeric({decimal: false});
                                $("#formField_latitude_minutes_lot").numeric({decimal: false});
                                $("#formField_latitude_seconds_lot").numeric();
                                $.subscribe('completeField', function(event, data) {
                                    completeForm('dialog-form', 'formField', event.originalEvent.request.responseText);
                                });
                                //            chargeValues('/aeps-plataforma-mvn/templates/listarTiposFields.action', 'proId', '', 'formField_type_property_lot', 'divMessage');
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
                    <p>El area se encuentran entre 0 y 3000 (hectarea)</p>
                </div>
            </div>
        </div>
        <div class="row-fluid" id="divListFieldsForm"></div>
    </body>
</html>