<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<div id="divMessHarvest"></div>
<s:form id="formCropHar" action="saveHarvest" cssClass="form-horizontal">
   
        
   <fieldset>
        <legend>Formulario de costos indirectos</legend>
        <div class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="costControlCon" class="control-label">
                                        Costos de vigilante:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                    </div>                         
                                </div>                          
           </div> 
                                    
                <div class="span5" style="padding-left: 28px">
                                    <label for="costControlCon" class="control-label">
                                        Costos de arrendamiento:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                    </div>  
                                     
                    </div> 
             
        </div>
            
              
                                        <div id ="divcostgranel" class="row"> 
                                            <div class="span5" >
                                                <div class="control-group">
                                                    <label for="costControlCon" class="control-label">
                                                        Costos de asistencia técnica:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                                    </div>                         
                                                </div>                          
                                            </div> 
                                            <div class="span5" style="padding-left: 28px">
                                                <div class="control-group">
                                                    <label for="costControlCon" class="control-label">
                                                       Costos de impuestos:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                                    </div>                         
                                                </div>        

                                            </div>

                                        </div>                
                                                    
                                   
                                        
            <div id ="divcostmechanized" class="row" >
                                      <div class="span5" >
                                            <div class="control-group">
                                                <label for="costControlCon" class="control-label">
                                                   Costos de administración:
                                                </label>
                                                <div class="controls">
                                                    <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                                </div>                         
                                        </div>  
                                      </div>
                                                    <div id="divCostStorage" >      
                                                        <div class="span5" style="padding-left: 28px">
                                                            <div class="control-group">
                                                                <label for="costControlCon" class="control-label">
                                                                  Costos Otros Servicios:
                                                                </label>
                                                                <div class="controls">
                                                                    <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                                                </div>                         
                                                            </div>        

                                                        </div>
                                                    </div>

            </div> 
          <div id ="divcostmanual" >                                        
          <div id ="divcostmanual0" class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="costControlCon" class="control-label">
                                        Intereses financieros:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="%{#attr.formChe}.costProductCheFer" id="%{#attr.formCheId}__costProductCheFer" value="%{#attr.costProductCheFer}"/>
                                    </div>                         
                                </div>                          
           </div> 
            
        </div>
       
        
                             
       </div>        
                                                               
         
                                  
   
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                              
   </fieldset>      
        
    <div style="margin-bottom: 15px" id="divBtHarvest">
      
                <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropHar'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeHarvest" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Costos Indirectos</sj:submit>
           
    </div>
   
</s:form>	
<script>
    $.subscribe('completeHarvest', function(event, data) {
        completeFormCrop('', 'formCropHar', 'divMessHarvest', event.originalEvent.request.responseText);
    });
    showCostTypeHarvest('formCropHar_harv_harvestType_idHarTyp', 'divcostgranel', 'divcostbulto','divcostmanual3');
    showCostMethodsHarvest('formCropHar_harv_harvestMethods_idHarMet', 'divcostmechanized', 'divcostmanual','divcostmanual3');
    showStorageHar('harv.storageHar', 'divCostStorage', 'divcostrented');
</script>