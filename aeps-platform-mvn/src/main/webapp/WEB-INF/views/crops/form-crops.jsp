<%@page import="java.util.HashMap"%>
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
        <div class="row-fluid" id="divRastaForm">
            <s:form id="formRasta" action="saveCrop" cssClass="form-horizontal">
                <fieldset>         
                    <legend>Caracteristicas y Observaciones</legend>
                    <s:hidden name="rasta.idRas"/>    
                    <div class="control-group">
                        <s:label for="formRasta_rasta_fechaRas" cssClass="control-label req" value="Seleccione el lote al cual pertenece:"></s:label>
                        <div class="controls">
                            <s:hidden name="idField"/>
                            <s:textfield
                                name="nameField"         
                                tooltip="Seleccione un lote con la lupa a la derecha"                        
                                />
                            <img src="/aeps-plataforma-mvn/img/search_icon.gif" style="cursor: pointer" alt="Seleccione el lote" onclick="listInfo('/aeps-plataforma-mvn/viewField.action?selected=rasta', 'formRasta_nameField', 'formRasta_idField', 'divListRastaForm', 'divRastaForm')" />
                        </div>  
                    </div>  
                    <div class="control-group">
                        <s:label for="formRasta_rasta_fechaRas" cssClass="control-label req" value="Fecha:"></s:label>
                        <div class="controls">
                            <s:date name="rasta.fechaRas" format="dd/MM/yyyy" var="dateTransform"/>
                            <s:textfield cssClass="form-control" name="rasta.fechaRas" value="%{#dateTransform}"/>
                            <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                        </div>
                    </div>    
                    <div class="control-group">
                        <s:label for="formRasta_rasta_latitudRas" cssClass="control-label req" value="Latitud del rasta (decimales):"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" name="rasta.latitudRas" onkeyup="generateDegrees('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_latitude_degrees" cssClass="control-label req" value="Latitud del rasta (grados):"></s:label>
                        <div class="controls">
                            <div class="row-fluid">
                                <div class="span2 input-prepend controls" style="width: 100px;">
                                    <span class="add-on">Grados</span>
                                    <input type="text" name="latitude_degrees" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')" id="formRasta_rasta_latitude_degrees" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                    <span class="add-on">Minutos</span>
                                    <input type="text" name="latitude_minutes" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')" id="formRasta_rasta_latitude_minutes" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                </div>
                                <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2.2%">
                                    <span class="add-on">Segundos</span>
                                    <input type="text" name="latitude_seconds" onkeyup="generateDecimals('formRasta_rasta_latitudRas', 'formRasta_rasta_latitude_degrees', 'formRasta_rasta_latitude_minutes', 'formRasta_rasta_latitude_seconds')" id="formRasta_rasta_latitude_seconds" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                </div>
                            </div>
                        </div>
                    </div>                            
                        <div class="control-group">
                        <s:label for="formRasta_rasta_longitudRas" cssClass="control-label req" value="Longitud del rasta (decimales):"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_longitudRas" name="rasta.longitudRas" onkeyup="generateDegrees('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_length_degrees" cssClass="control-label req" value="Longitud del rasta (grados):"></s:label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on">Grados</span>
                                        <input type="text" name="length_degrees" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')" id="formRasta_rasta_length_degrees" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on">Minutos</span>
                                        <input type="text" name="length_minutes" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')" id="formRasta_rasta_length_minutes" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2.2%">
                                        <span class="add-on">Segundos</span>
                                        <input type="text" name="length_seconds" onkeyup="generateDecimals('formRasta_rasta_longitudRas', 'formRasta_rasta_length_degrees', 'formRasta_rasta_length_minutes', 'formRasta_rasta_length_seconds')" id="formRasta_rasta_length_seconds" style="padding: 8px; max-width:30px; max-height: 12px"/>
                                    </div>
                                </div>
                            </div>
                        </div>                             
                        <div class="control-group">
                        <s:label for="formRasta_rasta_altitudRas" cssClass="control-label req" value="Altitud del rasta (metros):"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_altitudRas" name="rasta.altitudRas" tooltip="Ingrese la altitud del rasta en metros"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_pendienteTerrenoRas" cssClass="control-label req" value="Pendiente:"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" id="formRasta_rasta_pendienteTerrenoRas" name="rasta.pendienteTerrenoRas" tooltip="Ingrese la pendiente del rasta"/>
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_terrenoCircundanteRas" cssClass="control-label req" value="Terreno circundante:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.terrenoCircundanteRas"
                                list="{'plano o llano', 'ondulado', 'montañoso', 'ondulado y montañoso'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_posicionPerfilRas" cssClass="control-label req" value="Posición del perfil:"></s:label>
                        <div class="controls">
                            <s:select
                                name="rasta.posicionPerfilRas"
                                list="{'meseta', 'cima', 'ladera convexa', 'ladera cóncava', 'ladera plana', 'plano', 'plano con ondulaciones', 'pie de una elevación'}"           
                                headerKey="-1" 
                                headerValue="---" />
                        </div>   
                    </div>
                    <div class="control-group">
                        <s:label for="formRasta_rasta_phRas" cssClass="control-label req" value="pH:"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" name="rasta.phRas" tooltip="Ingrese el pH del rasta"/>
                        </div>
                    </div>
                    <div class="form-group control-group">
                        <s:label for="formRasta_rasta_carbonatosRas" cssClass="control-label req" value="Carbonatos:"></s:label>
                            <div class="controls">
                                <s:select
                                    name="rasta.carbonatosRas"
                                    list="{'no tiene', 'bajos a muy bajos', 'medios', 'altos'}"           
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>   
                        </div>
                        <div class="control-group">
                            <s:label for="formRasta_rasta_profundidadCarbonatosRas" cssClass="control-label" value="Profundidad Carbonatos:"></s:label>
                            <div class="controls">
                            <s:textfield cssClass="form-control" name="rasta.profundidadCarbonatosRas" tooltip="Ingrese la profundidad de los carbonatos"/>&nbsp;cm
                        </div>
                    </div>
                </fieldset>                
                <div> 
                    <s:hidden name="page"/>
                    <s:hidden name="actExe"/>    
                    <s:hidden name="newRow" value="1"/>    
                    <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeRasta" validate="true" validateFunction="validationForm">Guardar Rasta</sj:submit>
                    <button class="btn btn-large bt_cancel_crop" onclick="resetForm('formRasta'); closeWindow();">Cancelar</button>
                </div>    
            </s:form>        
            <script>
                var page = $("#formRasta_page").val();

                $.mask.definitions['i'] = "[-0-9]";
                $.mask.definitions['f'] = "[-.0-9]";
                $("#formRasta_rasta_fechaRas").datepicker({dateFormat: 'dd/mm/yy'});
                $("#formRasta_rasta_fechaRas").mask("99/99/9999", {placeholder: ""});
                
                $("#formRasta_rasta_latitudRas").numeric();
                $("#formRasta_rasta_latitudRas").val(parsePointSeparated($("#formRasta_rasta_latitudRas").val()));
                
                $("#formRasta_rasta_longitudRas").numeric();
                $("#formRasta_rasta_longitudRas").val(parsePointSeparated($("#formRasta_rasta_longitudRas").val()));
                
                $("#formRasta_rasta_altitudRas").numeric({decimal: false, negative: false});                
                $("#formRasta_rasta_length_degrees").numeric({decimal: false});
                $("#formRasta_rasta_length_minutes").numeric({decimal: false});
                $("#formRasta_rasta_length_seconds").numeric({decimal: false});
                $("#formRasta_rasta_latitude_degrees").numeric({decimal: false});
                $("#formRasta_rasta_latitude_minutes").numeric({decimal: false});
                $("#formRasta_rasta_latitude_seconds").numeric({decimal: false});
                
                $("#formRasta_rasta_pendienteTerrenoRas").numeric({negative: false});
                $("#formRasta_rasta_pendienteTerrenoRas").val(parsePointSeparated($("#formRasta_rasta_pendienteTerrenoRas").val()));
                
                $("#formRasta_rasta_phRas").numeric({negative: false});
                $("#formRasta_rasta_phRas").val(parsePointSeparated($("#formRasta_rasta_phRas").val()));
                
                $("#formRasta_rasta_profundidadCarbonatosRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadCarbonatosRas").val(parsePointSeparated($("#formRasta_rasta_profundidadCarbonatosRas").val()));
                
                $("#formRasta_rasta_profundidadHorizontePedregosoRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadHorizontePedregosoRas").val(parsePointSeparated($("#formRasta_rasta_profundidadHorizontePedregosoRas").val()));
                
                $("#formRasta_rasta_profundidadPrimerasPiedrasRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadPrimerasPiedrasRas").val(parsePointSeparated($("#formRasta_rasta_profundidadPrimerasPiedrasRas").val()));
                
                $("#formRasta_rasta_espesorHorizontePedregosoRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_espesorHorizontePedregosoRas").val(parsePointSeparated($("#formRasta_rasta_espesorHorizontePedregosoRas").val()));
                
                $("#formRasta_rasta_espesorCapaEndurecidaRas").numeric({decimal: false, negative: false});
                $("#formRasta_rasta_prufundidadCapasRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_prufundidadCapasRas").val(parsePointSeparated($("#formRasta_rasta_prufundidadCapasRas").val()));
                
                $("#formRasta_rasta_profundidadMoteadosRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadMoteadosRas").val(parsePointSeparated($("#formRasta_rasta_profundidadMoteadosRas").val()));
                $("#formRasta_rasta_profundidadRaicesRas").numeric({decimal: true, negative: false});
                $("#formRasta_rasta_profundidadRaicesRas").val(parsePointSeparated($("#formRasta_rasta_profundidadRaicesRas").val()));
                
                $.subscribe('completeRasta', function(event, data) {
                    completeFormGetting('dialog-form', 'formRasta', 'divRasta', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("searchSoil.action?page=" + page, "divConListRasta");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListRastaForm"></div>
    </body>
</html>