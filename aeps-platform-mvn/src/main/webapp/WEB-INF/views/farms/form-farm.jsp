<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divFarmsForm">
            <div class="span7">	
                <s:form id="formFarm" action="saveFarm.action" cssClass="form-horizontal formClassProperty">
                    <fieldset>
                        <legend>Formulario de una finca</legend>
                        <s:hidden name="typeEnt"/>
                        <s:if test="%{typeEnt==2}">
                            <!--<div class="control-group">-->
                                <s:hidden name="idProducer"/>                                
                                <s:hidden name="name_producer"/>                                
<!--                                <label class="control-label">
                                    Seleccione el productor:                                    
                                </label>
                                <div class="controls">
                                    <label><s:property value="name_producer" /></label>                                    
                                </div>  
                            </div> -->
                        </s:if>
                        <s:else>
                            <div class="control-group">
                                <s:hidden name="idProducer"/>                                
                                <label for="formFarm_name_producer" class="control-label req">
                                    Seleccione el productor:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="Usar la lupa para seleccionar el productor al cual pertenece la finca que va a registrar." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="name_producer" readonly="true" onclick="listInfo('/viewProducer.action?selected=property', 'formFarm_name_producer', 'formFarm_idProducer', 'divListFarmsForm', 'divFarmsForm')" />
                                    <a class="btn" onclick="listInfo('/viewProducer.action?selected=property', 'formFarm_name_producer', 'formFarm_idProducer', 'divListFarmsForm', 'divFarmsForm')"><i class="icon-search"></i></a>
                                </div>  
                            </div>   
                        </s:else>                        
                        <div class="control-group">
                            <s:hidden name="idFarm"/>
                            <label for="formFarm_name_property" class="control-label req">
                                Nombre de Finca:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el nombre de la finca asociada. Sino cuenta con nombre colocar el nombre del productor o manejar numeros." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="name_property" />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_latitude_property" class="control-label req">
                                Latitud de la finca (decimales):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas de la sede de la finca. Si su dispositivo esta parametrizado en decimales, ingresar la latitud aquí. Si esta en Grados, Minutos, Segundos (GMS) ingresar los datos en el siguiente campo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="latitude_property" onkeyup="generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_latitude_degrees_property" class="control-label req">
                                Latitud de la finca (grados):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas de la sede de la finca. Si su dispositivo esta parametrizado en en Grados, Minutos, Segundos (GMS), ingresar la latitud aquí. Si esta en decimales ingresar el dato en el campo anterior." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="latitude_degrees_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');" id="formFarm_latitude_degrees_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="latitude_minutes_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property'); checkValue('formFarm_latitude_minutes_property', 59);" id="formFarm_latitude_minutes_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="latitude_seconds_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property'); checkValueSecond('formFarm_latitude_seconds_property', 60);" id="formFarm_latitude_seconds_property" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>                            
                        <div class="control-group">
                            <label for="formFarm_length_property" class="control-label req">
                                Longitud de la Finca (decimales):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas de la sede de la finca. Si su dispositivo esta parametrizado en decimales, ingresar la longitud aquí. Si esta en Grados, Minutos, Segundos (GMS) ingresar los datos en el siguiente campo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" id="formFarm_length_property" name="length_property" onkeyup="generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_length_degrees_property" class="control-label req">
                                Longitud de la Finca (grados):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas de la sede de la finca. Si su dispositivo esta parametrizado en en Grados, Minutos, Segundos (GMS), ingresar la longitud aquí. Si esta en decimales ingresar el dato en el campo anterior." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="length_degrees_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property');" id="formFarm_length_degrees_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="length_minutes_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); checkValue('formFarm_length_minutes_property', 59);" id="formFarm_length_minutes_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="length_seconds_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); checkValueSecond('formFarm_length_seconds_property', 60);" id="formFarm_length_seconds_property" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">   
                            <div class="controls">
                                <button type="button" class="btn btn-initial btn-space" onclick="viewPosition('/viewPositionFarm.action', 'formFarm', 'latitude_property', 'formFarm_latitude_property', 'length_property', 'formFarm_length_property', 'divFarmsForm', 'divListFarmsForm');">
                                    <i class="icon-map-marker" style="font-size: 18px"></i> Visualizar/editar las coordenadas en un mapa
                                </button>                                          
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_altitude_property" class="control-label req">
                                Altitud de la Finca (metros):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese la altitud de la finca en metros." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="altitude_property" />
                            </div>  
                        </div>                         
                        <div class="control-group">
                            <label for="formFarm_depFar" class="control-label req">
                                Departamento:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccione un departamento." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:select
                                    name="depFar" 
                                    list="department_property" 
                                    listKey="idDep" 
                                    listValue="nameDep"          
                                    headerKey=" " 
                                    headerValue="---"
                                    onchange="chargeValues('/comboMunicipalities.action', 'depId', this.value, 'formFarm_cityFar', 'divMessage')"
                                />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_cityFar" class="control-label req">
                                Municipio:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccione un municipio." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:select
                                    list="city_property" 
                                    listKey="idMun" 
                                    listValue="nameMun" 
                                    headerKey=" " 
                                    headerValue="---"
                                    name="cityFar" 
                                />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_lane_property" class="control-label req">
                                Vereda:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese la vereda." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="lane_property" />
                            </div>  
                        </div> 
                        <div class="control-group">
                            <label for="formFarm_direction_property" class="control-label">
                                Indicación (Como llegar):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese la direccion de la finca." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="direction_property" />
                            </div>  
                        </div>   
                    </fieldset>
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <div> 
                        <s:hidden name="page" id="formFarm_page" />
                        <s:hidden name="actExe"/>
                        <s:hidden name="viewInfo"/>
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "farm/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "farm/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formFarm'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeFarm" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Finca</sj:submit>
                        <% } %>
                        <!--<button class="btn btn_per bt_send_property" onclick="sendForm('../actions/Actions.php?action=AgregarFinca', 'formProperty', 'divMessage')"><i class="icon-save"></i> Guardar informaci&oacute;n</button>-->
                        <button class="btn btn-large bt_cancel_farm" onclick="resetForm('formFarm'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>    
                </s:form>        
                <script>
//                    var page   = $("#formFarm_page").val();                    
                    //For property
                    // $.mask.definitions['i'] = "[-0-9]";
                    $.mask.definitions['f'] = "[-.0-9]";
                    $("#formFarm_latitude_property").numeric();
                    $("#formFarm_length_property").numeric();
                    $("#formFarm_latitude_property").val(parsePointSeparated($("#formFarm_latitude_property").val()));
                    $("#formFarm_length_property").val(parsePointSeparated($("#formFarm_length_property").val()));           
                    
                    $("#formFarm_altitude_property").numeric({decimal: false, negative: false});
                    $("#formFarm_length_degrees_property").numeric({decimal: false});
                    $("#formFarm_length_minutes_property").numeric({decimal: false});
                    $("#formFarm_length_seconds_property").numeric();
                    if ($("#formFarm_length_minutes_property").val()>60) {                        
                        $("#formFarm_length_minutes_property").val('');
                    }
                    if ($("#formFarm_length_seconds_property").val()>60) {                        
                        $("#formFarm_length_seconds_property").val('');
                    }
                    
                    $("#formFarm_altitude_property").val(parseValueInt( $("#formFarm_altitude_property").val()));
                    
                    $("#formFarm_latitude_degrees_property").numeric({decimal: false});
                    $("#formFarm_latitude_minutes_property").numeric({decimal: false});
                    $("#formFarm_latitude_seconds_property").numeric();
                    if ($("#formFarm_latitude_minutes_property").val()>60) {                        
                        $("#formFarm_latitude_minutes_property").val('');
                    }
                    if ($("#formFarm_latitude_seconds_property").val()>60) {                        
                        $("#formFarm_latitude_seconds_property").val('');
                    }
                    generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');
                    generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property');
                    $.subscribe('completeFarm', function(event, data) {
                        //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                        //     '\n\nThe output div should have already been updated with the responseText.');
                        //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                        //        alert('responseText: \n' + json.info);
//                        completeForm('dialog-form', 'formFarm', event.originalEvent.request.responseText);
                        completeFormGetting('dialog-form', 'formFarm', 'divFarms', event.originalEvent.request.responseText);
                        setTimeout( function() {
                            showInfo("searchFarm.action?page="+$("#formFarm_page").val(), "divConListFarms");
                        }, 2000);
                    });
                    if($('.pop-over').length) {
                        $('.pop-over').popover();
                    }
                    //chargeValues('../actions/Actions.php?action=ListarDeps', 'depId', '', 'params_department_property', 'divMessage');
                </script>
            </div>
            <div class="span5" style="margin-left: 0">
                <div class="alert fade in">
                    <h3>Recuerde que en Colombia:</h3>
                    <br />
                    <strong>Las latitudes</strong>
                    <hr>
                    <p>Decimales varia entre (-4.3 y 13.5)</p>
                    <p>Grados varia entre (-5 y 14)</p>
                    <p>Minutos varia entre (0 y 60)</p>
                    <p>Segundos varia entre (0 y 60)</p>
                    <strong>Las longitudes</strong>
                    <hr>
                    <p>Decimales varia entre (-81.8 y -66)</p>
                    <p>Grados varia entre (-82 y -66)</p>
                    <p>Minutos varia entre (0 y 60)</p>
                    <p>Segundos varia entre (0 y 60)</p>
                    <strong>Las altitudes</strong>
                    <hr>
                    <p>Se encuentran entre 0 y 9000 (metros)</p>			
                </div>
            </div>
        </div>
        <div id="divListFarmsForm"></div>
    </body>
</html>