<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Integer entTypeHarId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<div id="divMessHarvest"></div>
<s:form id="formCropHar" action="saveHarvest" cssClass="form-horizontal">
    <fieldset>
        <legend><s:property value="getText('title.formharvest.harvest')" /></legend>
        <div class="row">
            <div class="span5">
                <s:hidden name="idCrop"/>
                <s:hidden name="typeCrop"/>
                <s:hidden name="actExe"/>
                <s:hidden name="harv.idHar"/>
                <div class="control-group">
                    <label for="formCropHar_harv_dateHar" class="control-label req">
                        <s:property value="getText('text.harvestdate.harvest')" />:
                    </label>
                    <div class="date controls">
                        <s:date name="harv.dateHar" format="MM/dd/yyyy" var="dateTransform"/>
                        <s:textfield name="harv.dateHar" value="%{#dateTransform}" readonly="true"/>
                        <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                        <span class="add-on"><i class="icon-calendar"></i></span>
                    </div>                          
                </div>                          
            </div>       
        </div>
        <div class="row">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_harvestMethods_idHarMet" class="control-label req">
                        <s:property value="getText('select.harvestmethods.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:select
                            name="harv.harvestMethods.idHarMet"
                            list="type_harv_meth" 
                            listKey="idHarMet" 
                            listValue="nameHarMet"            
                            headerKey="-1" 
                            headerValue="---" 
                            onclick="showCostMethodsHarvest('formCropHar_harv_harvestMethods_idHarMet', 'divcostmechanized', 'divcostmanual', 'divcostmanual3');"
                            />
                    </div> 
                </div>                                        
            </div>          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_resultingProducts_idResPro" class="control-label req">
                         <s:property value="getText('select.resultingproducts.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:select
                            name="harv.resultingProducts.idResPro"
                            list="type_res_pro" 
                            listKey="idResPro" 
                            listValue="nameResPro"            
                            headerKey="-1" 
                            headerValue="---"
                            onchange="
                            changeOptionsHarvest('formCropHar_harv_resultingProducts_idResPro', 'divYield', 'divHumidity', 'divNumberSacks', 'harvNumberSacks', 'Numero de bulto (ha):', 'Numero de bolsas:', 'harvWeightAvg', 'Peso promedio de un bulto (kg/bulto):', 'Peso promedio de la bolsa:');
                            "
                        />
                    </div>
                </div>
            </div>            
        </div>
        <div class="row hide" id="divYield">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_yieldHar" class="control-label req">
                        <s:property value="getText('text.yieldhar.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.yieldHar"/>
                    </div>
                </div>                                      
            </div>                          
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_productionHar" class="control-label">
                        <s:property value="getText('text.productionhar.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.productionHar"/>
                    </div>                          
                </div>    
            </div>
        </div>
        <div class="row hide" id="divHumidity">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_humidityPercentageHar" class="control-label req">
                         <s:property value="getText('text.humiditypercentage.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.humidityPercentageHar" />
                    </div>                          
                </div>                                          
            </div>       
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_storageHar" class="control-label">
                        <s:property value="getText('radio.storagehar.harvest')" />:
                    </label>
                    <div class="controls radioSelect">
                        <s:radio 
                            list="#{'true':'Si', 'false':'No'}" 
                            name="harv.storageHar"
                            onclick="showStorageHar('harv.storageHar', 'divCostStorage', 'divcostrented');"
                            />
                    </div>                         
                </div>
            </div>
        </div>
        <div class="row hide" id="divNumberSacks">
            <div class="span5">
                <div class="control-group">
                    <label for="formCropHar_harv_numberSacksSow" id="harvNumberSacks" class="control-label req">
                         <s:property value="getText('text.numbersacks.harvest')" />:
                    </label>
                    <div class="controls">
                        <s:textfield name="harv.numberSacksSow" />
                    </div>                          
                </div>                                          
            </div>       
            <div class="span4" style="padding-left: 28px">
                <div class="control-group">
                    <label for="formCropHar_harv_weightAvgSacksSow" id="harvWeightAvg" class="control-label">
                        <s:property value="getText('text.weightavgsacks.harvest')" />:
                    </label>
                    <div class="controls radioSelect">
                        <s:textfield name="harv.weightAvgSacksSow" />
                    </div>                         
                </div>
            </div>
        </div>
        <s:if test="%{typeCrop==2}">
            <div class="control-group">
                <label for="formCropHar_harv_loadHectareSow" class="control-label">
                    <s:property value="getText('text.loadhectare.harvest')" />: 
                </label>
                <div class="controls">
                    <s:textfield name="harv.loadHectareSow" />
                </div>					 
            </div>
        </s:if>
        
            
            
        <div class="control-group">
            <label for="formCropHar_harv_commentHar" class="control-label" style="width: 175px">
                <s:property value="getText('text.commentofyield.harvest')" />: 
            </label>
            <div class="controls">
                <s:textarea rows="5" cssClass="span6" name="harv.commentHar"></s:textarea>
            </div>					 
        </div>	

        <div id ="divcosthar" class="row"> 
                                            <div class="span5" >
                                                <div class="control-group">
                                                                
                                                    <label for="formCropHar_harv_costSalepriceHar" class="control-label">
                                                        <s:property value="getText('text.hervestpvp.harvest')" />:
                                                    </label>
                                                    <div class="controls">
                                                      <s:textfield name="harv.costSalepriceHar" />
                                                  
                                                    </div>                         
                                                </div>                          
                                            </div> 
                                            <div class="span5" style="padding-left: 28px">
                                                <div class="control-group">
                                                    <label for="formCropHar_harv_costNamebuyerHar" class="control-label">
                                                        <s:property value="getText('text.hervestnamecliente.harvest')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="harv.costNamebuyerHar" />
                                                    </div>                         
                                                </div>        

                                            </div>

         </div>
            
        <script>
            changeOptionsHarvest('formCropHar_harv_resultingProducts_idResPro', 'divYield', 'divHumidity', 'divNumberSacks', 'harvNumberSacks', 'Numero de bulto (ha):', 'Numero de bolsas:', 'harvWeightAvg', 'Peso promedio de un bulto (kg/bulto):', 'Peso promedio de la bolsa:');
            $("#formCropHar_harv_dateHar").datepicker({dateFormat: 'mm/dd/yy'});
            $("#formCropHar_harv_dateHar").mask("99/99/9999", {placeholder: " "});
            $("#formCropHar_harv_productionHar").numeric({decimal: false, negative: false});            
            $("#formCropHar_harv_yieldHar").numeric({negative: false});
            $("#formCropHar_harv_humidityPercentageHar").numeric({negative: false});
            $("#formCropHar_harv_numberSacksSow").numeric({decimal: false, negative: false});
            $("#formCropHar_harv_weightAvgSacksSow").numeric({negative: false});
            $("#formCropHar_harv_loadHectareSow").numeric({negative: false});
            $("#formCropHar_harv_yieldHar").val(parsePointSeparated($("#formCropHar_harv_yieldHar").val())); 
            $("#formCropHar_harv_humidityPercentageHar").val(parsePointSeparated($("#formCropHar_harv_humidityPercentageHar").val())); 
//            $("#formCropHar_harv_weightAvgSacksSow").val(parsePointSeparated($("#formCropHar_harv_weightAvgSacksSow").val())); 
//            $("#formCropHar_harv_loadHectareSow").val(parsePointSeparated($("#formCropHar_harv_loadHectareSow").val())); 
            showCostMethodsHarvest('formCropHar_harv_harvestMethods_idHarMet', 'divcostmechanized', 'divcostmanual');
        </script>
    </fieldset>
        
   <fieldset>
        <legend><s:property value="getText('title.formharvestcost.harvest')" /></legend>
        <div class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="formCropHar_harv_costPackingHar"  class="control-label">
                                        <s:property value="getText('text.hervestcostpacking.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costPackingHar"/>
                                    </div>                         
                                </div>                          
           </div> 
                                    
                <div class="span5" style="padding-left: 28px">
                                    <s:label for="formCropHar_harv_costTypeHar_idHarTyp" cssClass="control-label " value="%{getText('select.harvesttype.harvest')}:"></s:label>
                                        <div class="controls">

                                        <s:select
                                            id="formCropHar_harv_costTypeHar_idHarTyp"
                                            name="harv.costTypeHar" 
                                                 
                                            value="harv.costTypeHar" 
                                            list="#{'0':'---','1':'Granel', '2':'Bulto'}"     
                                           
                                            onclick="showCostTypeHarvest('formCropHar_harv_costTypeHar_idHarTyp', 'divcostgranel', 'divcostbulto','divcostmanual3');"
                                         />

                                    </div>
                                     
                    </div> 
             
        </div>           
              
                                        <div id ="divcostgranel" class="row"> 
                                            <div class="span5" >
                                                <div class="control-group">
                                                    <label for="formCropHar_harv_costTractorHar" class="control-label">
                                                        <s:property value="getText('text.hervestcosttractor.harvest')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="harv.costTractorHar" />
                                                    </div>                         
                                                </div>                          
                                            </div> 
                                            <div class="span5" style="padding-left: 28px">
                                                <div class="control-group">
                                                    <label for="formCropHar_harv_costGranaleroTrailerHar" class="control-label">
                                                        <s:property value="getText('text.hervestcosttrailer.harvest')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="harv.costGranaleroTrailerHar" />
                                                    </div>                         
                                                </div>        

                                            </div>

                                        </div>                
                                                    
                                      <div id ="divcostbulto" class="row"> 
                                            <div class="span5" >
                                                <div class="control-group">
                                                    <label for="formCropHar_harv_costZorreoHar" class="control-label">
                                                        <s:property value="getText('text.hervestcostzorreo.harvest')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="harv.costZorreoHar" />
                                                    </div>                         
                                                </div>                          
                                            </div> 
                                         

                                        </div> 
                                        
            <div id ="divcostmechanized" class="row" >
                                      <div class="span5" >
                                            <div class="control-group">
                                                <label  for="formCropHar_harv_costCombineHar"  class="control-label">
                                                    <s:property value="getText('text.hervestcost.harvest')" />:
                                                </label>
                                                <div class="controls">
                                                    <s:textfield name="harv.costCombineHar" />
                                                </div>                         
                                        </div>  
                                      </div>
                                                    

            </div> 
             
              <div id="divCostStorage" class="row" >      
                                                        <div class="span5" >
                                                            <div class="control-group">
                                                                <label for="formCropHar_harv_costStorageHar" class="control-label">
                                                                    <s:property value="getText('text.hervestcoststorage.harvest')" />:
                                                                </label>
                                                                <div class="controls">
                                                                    <s:textfield name="harv.costStorageHar" />
                                                                </div>                         
                                                            </div>        

                                                        </div>
              </div>                                  
                                                
                                                
          <div id ="divcostmanual" >                                        
          <div id ="divcostmanual0" class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="formCropHar_harv_costWorkforceHar" class="control-label">
                                        <s:property value="getText('text.hervestcostworkforce.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costWorkforceHar"/>
                                    </div>                         
                                </div>                          
           </div> 
             <div class="span5" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropHar_harv_costTransportWorkforceHar" class="control-label">
                                        <s:property value="getText('text.hervestcosttrasportsd.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costTransportWorkforceHar" />
                                    </div>                         
                                </div>        

            </div> 
        </div>
        <div id ="divcostmanual1" class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="formCropHar_harv_costShellerHar" class="control-label">
                                        <s:property value="getText('text.hervestcostdesgranadora.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costShellerHar" />
                                    </div>                         
                                </div>                          
           </div> 
             <div class="span5" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropHar_harv_costWorkforceShellerHar" class="control-label">
                                        <s:property value="getText('text.hervestcostdesgranadoraworkforce.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costWorkforceShellerHar" />
                                    </div>                         
                                </div>        

            </div> 
        </div>
       
                             
       </div>        
                                                               
           <div id ="divcostmanual3" class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="formCropHar_harv_costCabuyaHar" class="control-label">
                                        <s:property value="getText('text.hervestcostcabuya.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costCabuyaHar" />
                                    </div>                         
                                </div>                          
           </div> 
             <div class="span5" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="formCropHar_harv_costTransportCollectioncenterHar" class="control-label">
                                        <s:property value="getText('text.hervestcosttransport.harvest')" />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="harv.costTransportCollectioncenterHar" />
                                    </div>                         
                                </div>        

            </div> 
        </div>   
        <% if (entTypeHarId!=3) { %>    
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
        <% } %>                              
   </fieldset>      
   <script>       
       
       $("#formCropHar_harv_costSalepriceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costPackingHar").maskMoney({suffix: ' $'});       
       $("#formCropHar_harv_costTractorHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costGranaleroTrailerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costZorreoHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costWorkforceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costTransportWorkforceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costShellerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costWorkforceShellerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costCabuyaHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costTransportCollectioncenterHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costStorageHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costCombineHar").maskMoney({suffix: ' $'});           
       
   </script>
        
    <div style="margin-bottom: 15px" id="divBtHarvest">
        <% String actExeHar   = String.valueOf(request.getAttribute("actExe")); %>
        <% if ((actExeHar=="create" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExeHar=="modify" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
            <% if (entTypeHarId!=3) { %>
                <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="removeMask();searchDecimalNumber('formCropHar'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeHarvest" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saveharvest.harvest')" /></sj:submit>
            <% } %>
        <% } %>
    </div>
</s:form>	
<script>
    $.subscribe('completeHarvest', function(event, data) {        
           
       $("#formCropHar_harv_costSalepriceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costPackingHar").maskMoney({suffix: ' $'});       
       $("#formCropHar_harv_costTractorHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costGranaleroTrailerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costZorreoHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costWorkforceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costTransportWorkforceHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costShellerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costWorkforceShellerHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costCabuyaHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costTransportCollectioncenterHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costStorageHar").maskMoney({suffix: ' $'});
       $("#formCropHar_harv_costCombineHar").maskMoney({suffix: ' $'});  
        completeFormCrop('', 'formCropHar', 'divMessHarvest', event.originalEvent.request.responseText);
       
    });
    showCostTypeHarvest('formCropHar_harv_costTypeHar_idHarTyp', 'divcostgranel', 'divcostbulto','divcostmanual3');
    showCostMethodsHarvest('formCropHar_harv_harvestMethods_idHarMet', 'divcostmechanized', 'divcostmanual','divcostmanual3');
    showStorageHar('harv.storageHar', 'divCostStorage', 'divcostrented');

    function removeMask() {       
                    $("#formCropHar_harv_costSalepriceHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costSalepriceHar").maskMoney('mask');

                    $("#formCropHar_harv_costPackingHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costPackingHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costTractorHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costTractorHar").maskMoney('mask');

                    $("#formCropHar_harv_costGranaleroTrailerHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costGranaleroTrailerHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costZorreoHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costZorreoHar").maskMoney('mask');

                    $("#formCropHar_harv_costWorkforceHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costWorkforceHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costTransportWorkforceHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costTransportWorkforceHar").maskMoney('mask');

                    $("#formCropHar_harv_costShellerHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costShellerHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costWorkforceShellerHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costWorkforceShellerHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costCabuyaHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costCabuyaHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costTransportCollectioncenterHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costTransportCollectioncenterHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costStorageHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costStorageHar").maskMoney('mask');
                    
                    $("#formCropHar_harv_costCombineHar").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropHar_harv_costCombineHar").maskMoney('mask');

                }
</script>