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
        <div class="row-fluid" id="divIrrForm">
            <s:form id="formCropIrr" action="saveIrr" cssClass="form-horizontal">
                <fieldset>
                    <legend>Formulario de Riego</legend>
                    <div class="row">
                        <div class="span5">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="irr.idIrr"/>
                            <div class="control-group">
                                <label for="formCropIrr_irr_useIrrigationIrr" class="control-label req">
                                    Aplica riego:
                                </label>
                                <div class="controls radioSelect">
                                    <s:radio list="#{'true':'Si', 'false':'No'}" name="irr.useIrrigationIrr" onclick="showInfoRiego(this.value, 'divInfoRiego')"/>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <% String classUseIrr="hide"; %>
                    <s:set name="useIrr" value="irr.useIrrigationIrr"/>
                    <s:if test="%{#useIrr==true}">
                        <% classUseIrr = "";%>
                    </s:if>  
                    <!--<div id="divInfoRiego" class="<%//= classUseIrr %> hide" style="display: none">-->
                    <div id="divInfoRiego" class="<%= classUseIrr %>">
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropIrr_irr_dateIrr" class="control-label req">
                                        Fecha del riego:
                                    </label>
                                    <div class="date controls">
                                        <s:date name="irr.dateIrr" format="dd/MM/yyyy" var="dateTransformIrr"/>
                                        <s:textfield name="irr.dateIrr" value="%{#dateTransformIrr}" readonly="true"/>
                                        <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                        <span class="add-on"><i class="icon-calendar"></i></span>
                                    </div>                          
                                </div>                          
                            </div>       
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropIrr_irr_amountIrr" class="control-label req">
                                        Cantidad aportada por hectarea (lamina):
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="irr.amountIrr"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropIrr_irr_irrigationsTypes_idIrrTyp" class="control-label req">
                                        Tipo de riego:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="irr.irrigationsTypes.idIrrTyp"
                                            list="type_irr_typ" 
                                            listKey="idIrrTyp" 
                                            listValue="nameIrrTyp"            
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>                         
                                </div>                          
                            </div>   
                        </div>	
                    </div>	
                    <script>
                        $("#formCropIrr_irr_dateIrr").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropIrr_irr_dateIrr").mask("99/99/9999", {placeholder: " "});
//                        $("#formCropIrr_irr_depthIrr").numeric({decimal: false, negative: false});
                        $("#formCropIrr_irr_amountIrr").numeric({negative: false});
                        $("#formCropIrr_irr_amountIrr").val(parsePointSeparated($("#formCropIrr_irr_amountIrr").val())); 
                    </script>
                    <div id="divBtIrr">
                        <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeIrr" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Riego</sj:submit>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropIrr'); closeWindow();">Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>                
                $.subscribe('completeIrr', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropIrr', 'divIrr', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/aeps-plataforma-mvn/crop/searchIrr.action?idCrop="+$("#formCropIrr_idCrop").val(), "divListIrr");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListIrrForm"></div>
    </body>
</html>