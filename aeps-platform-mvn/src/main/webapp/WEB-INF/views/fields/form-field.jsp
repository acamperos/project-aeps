<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divFieldsForm">
            <div class="span7">	
                <s:form id="formField" action="saveField.action" cssClass="form-horizontal formClassLot">
                    <fieldset>
                        <legend>Formulario de un lote</legend>
                        <s:hidden name="idProducer"/>                           
                        <s:hidden name="idField"/>
<!--                        <div class="control-group">
                            <label for="formField_name_producer_lot" class="control-label req">
                                Seleccione el lote al cual pertenece:
                                <i class="icon-info-sign s2b_tooltip" title="Seleccione un productor con la lupa a la derecha"></i>
                            </label>
                            <div class="controls">
                                <%--<s:textfield name="name_producer_lot" />--%>
                                <a class="btn" onclick="listInfo('/viewProducer.action?selected=lot', 'formField_name_producer_lot', 'formField_idProducer', 'divListFieldsForm', 'divFieldsForm')"><i class="icon-search"></i></a>
                            </div>  
                        </div>                         -->
                        <div id="divPropertyLot">
                            <div class="control-group">
                                <s:hidden name="idFarm"/>
                                <label for="formField_name_property_lot" class="control-label req">
                                    Seleccione la Finca:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="Usar la lupa para seleccionar la finca a la cual pertenece el lote que va a registrar." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="name_property_lot" readonly="true" onclick="setPropertyGeneral('/viewFarm.action?selected=lot', 'idProducer', 'formField_idProducer', 'formField_name_property_lot', 'formField_idFarm', 'divListFieldsForm', 'divFieldsForm')" />
                                    <a class="btn" onclick="setPropertyGeneral('/viewFarm.action?selected=lot', 'idProducer', 'formField_idProducer', 'formField_name_property_lot', 'formField_idFarm', 'divListFieldsForm', 'divFieldsForm')"><i class="icon-search"></i></a>
                                </div>  
                            </div>                     
                        </div>
                        <div class="control-group">
                            <label for="formField_typeLot" class="control-label req">
                                El lote es:
                                <!--<i class="icon-info-sign s2b_tooltip" title="Seleccione que tipo de lote es"></i>-->
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccionar el régimen de tenencia del lote." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <!--requiredLabel="true"-->
                                <s:select
                                    name="typeLot"                                    
                                    list="type_property_lot" 
                                    listKey="idFieTyp" 
                                    listValue="nameFieTyp"              
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_name_lot" class="control-label req">
                                Nombre de Lote:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el nombre del lote asociado." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="name_lot" />
                            </div>    
                        </div>    
                        <div class="control-group">
                            <label for="formField_latitude_lot" class="control-label req">
                                Latitud del lote (decimales):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas del lote. Si su dispositivo esta parametrizado en decimales, ingresar la latitud aquí. Si esta en Grados, Minutos, Segundos (GMS) ingresar los datos en el siguiente campo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="latitude_lot" onkeyup="generateDegrees('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_latitude_degrees_lot" class="control-label req">
                                Latitud del lote (grados):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas del lote. Si su dispositivo esta parametrizado en en Grados, Minutos, Segundos (GMS), ingresar la latitud aquí. Si esta en decimales ingresar el dato en el campo anterior." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="latitude_degrees_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot')" id="formField_latitude_degrees_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="latitude_minutes_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot'); checkValue('formField_latitude_minutes_lot', 59);" id="formField_latitude_minutes_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="latitude_seconds_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot'); checkValueSecond('formField_latitude_seconds_lot', 60);" id="formField_latitude_seconds_lot" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>                            
                        <div class="control-group">
                            <label for="formField_length_lot" class="control-label req">
                                Longitud del Lote (decimales):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas del lote. Si su dispositivo esta parametrizado en decimales, ingresar la longitud aquí. Si esta en Grados, Minutos, Segundos (GMS) ingresar los datos en el siguiente campo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" id="formField_length_lot" name="length_lot" onkeyup="generateDegrees('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_length_degrees_lot" class="control-label req">
                                Longitud del Lote (grados):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Hacer uso de un GPS, o de un smartphone para capturar las coordenadas del lote. Si su dispositivo esta parametrizado en en Grados, Minutos, Segundos (GMS), ingresar la longitud aquí. Si esta en decimales ingresar el dato en el campo anterior." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="length_degrees_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot')" id="formField_length_degrees_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="length_minutes_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot'); checkValue('formField_length_minutes_lot', 59);" id="formField_length_minutes_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="length_seconds_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot'); checkValueSecond('formField_length_seconds_lot', 60);" id="formField_length_seconds_lot" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>    
                        <div class="control-group">
                            <button type="button" class="btn btn-initial btn-space" onclick="viewPosition('/viewPositionField.action', 'formField', 'latitude_lot', 'formField_latitude_lot', 'length_lot', 'formField_length_lot', 'divFieldsForm', 'divListFieldsForm');">
                                <i class="icon-map-marker"></i> Cambiar ubicacion
                            </button>
                        </div>
                        <div class="control-group">
                            <label for="formField_altitude_lot" class="control-label req">
                                Altitud del Lote (metros):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese la altitud del lote en metros." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="altitude_lot" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_area_lot" class="control-label">
                                Area del Lote (hectarea):
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el area del lote." data-title="Información" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="area_lot" requiredLabel="true" />
                            </div>         
                        </div>         
                    </fieldset>		
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <div> 
                        <s:hidden name="page" id="formField_page"/>
                        <s:hidden name="actExe"/>
                        <s:hidden name="viewInfo"/>
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "field/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "field/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formField'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeField" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Lote</sj:submit>
                        <% } %>
                        <!--<button class="btn btn_per bt_send_lot" onclick="sendForm('../actions/Actions.php?action=saveField', 'formLot', 'divMessage')"><i class="icon-save"></i>  Guardar informaci&oacute;n</button>-->
                        <button class="btn btn-large bt_cancel_field" onclick="resetForm('formField'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>    
                </s:form>        
                <script>
                        var viewInfo = $("#formField_viewInfo").val();
                        //For Lot
                        $.mask.definitions['i'] = "[-0-9]";
                        $.mask.definitions['f'] = "[-.0-9]";
                        /*$("#params_latitude_lot").mask("f?9fffffff",{placeholder:" "});
                         $("#params_length_lot").mask("f9?fffffff",{placeholder:" "});
                         $("#params_altitude_lot").mask("9?999",{placeholder:" "});
                         $("#params_area_lot").mask("9?999",{placeholder:" "});*/

                        $("#formField_latitude_lot").numeric();
                        $("#formField_length_lot").numeric();
                        $("#formField_latitude_lot").val(parsePointSeparated($("#formField_latitude_lot").val()));
                        $("#formField_length_lot").val(parsePointSeparated($("#formField_length_lot").val()));
                        
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
                        generateDegrees('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot');
                        generateDegrees('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot');
                        $.subscribe('completeField', function(event, data) {
                            completeFormGetting('dialog-form', 'formField', 'divFields', event.originalEvent.request.responseText);
                            setTimeout( function() {
                                showInfo("searchField.action?page="+$("#formField_page").val(), "divConListFields");
                            }, 2000);
                        });
                        if($('.pop-over').length) {
                            $('.pop-over').popover();
                        }
                                //            chargeValues('/templates/listarTiposFields.action', 'proId', '', 'formField_type_property_lot', 'divMessage');
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
                    <br />
                    <strong>Las longitudes</strong>
                    <hr>
                    <p>Decimales varia entre (-81.8 y -66)</p>
                    <p>Grados varia entre (-82 y -66)</p>
                    <p>Minutos varia entre (0 y 60)</p>
                    <p>Segundos varia entre (0 y 60)</p>
                    <br />
                    <strong>Las altitudes</strong>
                    <hr>
                    <p>Se encuentran entre 0 y 9000 (metros)</p>
                    <br />
                    <strong>Area</strong>
                    <hr>
                    <p>Se encuentra entre 0 y 3000 (hectarea)</p>			
                </div>
            </div>
        </div>
        <div class="row-fluid" id="divListFieldsForm"></div>
    </body>
</html>