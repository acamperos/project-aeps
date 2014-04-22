<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<div id="divMessHarvest"></div>
<s:form id="formCropHar" action="saveHarvest" cssClass="form-horizontal">
    <fieldset>
        <legend>Formulario de Cosecha</legend>
        <div class="row">
            <div class="span5">
                <s:hidden name="idCrop"/>
                <s:hidden name="typeCrop"/>
                <s:hidden name="actExe"/>
                <s:hidden name="harv.idHar"/>
                <div class="control-group">
                    <label for="formCropHar_harv_dateHar" class="control-label req">
                        Fecha de cosecha:
                    </label>
                    <div class="date controls">
                        <s:date name="harv.dateHar" format="dd/MM/yyyy" var="dateTransform"/>
                        <s:textfield name="harv.dateHar" value="%{#dateTransform}" readonly="true"/>
                        <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_harvestMethods_idHarMet" class="control-label req">
                        Metodo de cosecha:
                    </label>
                    <div class="controls">
                        <s:select
                            name="harv.harvestMethods.idHarMet"
                            list="type_harv_meth" 
                            listKey="idHarMet" 
                            listValue="nameHarMet"            
                            headerKey="-1" 
                            headerValue="---" />
                    </div> 
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_productionHar" class="control-label req">
                        Cantidad (Kg):
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.productionHar"/>
                    </div>                          
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_yieldHar" class="control-label req">
                        Rendimiento (kg/ha):
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.yieldHar"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_storageHar" class="control-label">
                        Almacenamiento:
                    </label>
                    <div class="controls radioSelect">
                        <s:radio list="#{'true':'Si', 'false':'No'}" name="harv.storageHar" />
                    </div>                         
                </div>                          
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_resultingProducts_idResPro" class="control-label req">
                         Producto esperado:
                    </label>
                    <div class="controls">
                        <s:select
                            name="harv.resultingProducts.idResPro"
                            list="type_res_pro" 
                            listKey="idResPro" 
                            listValue="nameResPro"            
                            headerKey="-1" 
                            headerValue="---"
                            onchange="showElementRate(this.value, 'divRateWet');"
                        />
                    </div>
                </div>
            </div>
        </div>
        <% String classRateWet="hide"; %>
        <s:set name="idHarvest" value="harv.resultingProducts.idResPro"/>
        <s:if test="%{#idHarvest==1 || #idHarvest==4}">
            <% classRateWet = "";%>
        </s:if>  
        <div class="<%= classRateWet %>" id="divRateWet">
            <div class="row">
                <div class="span5">
                    <div class="control-group">
                        <label for="formCropHar_harv_humidityPercentageHar" class="control-label req">
                             % de Humedad de la cosecha:
                        </label>
                        <div class="controls">
                            <s:textfield name="harv.humidityPercentageHar" />
                        </div>                          
                    </div>                          
                </div>     
            </div>
        </div>
        <div class="control-group">
            <label for="formCropHar_harv_commentHar" class="control-label req" style="width: 175px">
                Observaciones generales: 
            </label>
            <div class="controls">
                <s:textarea rows="5" cssClass="span6" name="harv.commentHar"></s:textarea>
            </div>					 
        </div>	
        <script>
            $("#formCropHar_harv_dateHar").datepicker({dateFormat: 'dd/mm/yy'});
            $("#formCropHar_harv_dateHar").mask("99/99/9999", {placeholder: " "});
            $("#formCropHar_harv_productionHar").numeric({decimal: false, negative: false});
            $("#formCropHar_harv_yieldHar").numeric({negative: false});
            $("#formCropHar_harv_humidityPercentageHar").numeric({negative: false});
            $("#formCropHar_harv_yieldHar").val(parsePointSeparated($("#formCropHar_harv_yieldHar").val())); 
            $("#formCropHar_harv_humidityPercentageHar").val(parsePointSeparated($("#formCropHar_harv_humidityPercentageHar").val())); 
        </script>
    </fieldset>
    <div style="margin-bottom: 15px" id="divBtHarvest">
        <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeHarvest" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Cosecha</sj:submit>
        <!--<button class="btn btn_default" onclick="resetForm('formCropHar')">Cancelar</button>-->
    </div>
</s:form>	
<script>
    $.subscribe('completeHarvest', function(event, data) {
        completeFormCrop('', 'formCropHar', 'divMessHarvest', event.originalEvent.request.responseText);
    });
</script>