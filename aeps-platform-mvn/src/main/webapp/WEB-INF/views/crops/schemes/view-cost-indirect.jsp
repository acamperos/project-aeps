<script type="text/javascript" src="/scripts/js/jquery/jquery.maskMoney.js"></script>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Integer entTypeCostId = new EntitiesDao().getEntityTypeId(user.getIdUsr()); %>
<div id="divMessCost"></div>
<s:form id="formCropCos" action="saveCost" cssClass="form-horizontal">
   
        
   <fieldset>
        <legend>Formulario de costos indirectos</legend>
        <div class="row"> 
             <div class="span5" >
                <s:hidden name="idCrop"/>
                <s:hidden name="typeCrop"/>
                <s:hidden name="actExe"/>
                <s:hidden name="costo.idCostIndPro"/>
                                <div class="control-group">
                                    <label id="formCropCost_costo_costVigilantPro" for="formCropCost_cost_costVigilantPro"  class="control-label">
                                        Costos de vigilante:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="costo.costVigilantPro" />
                                    </div>                         
                                </div>                          
           </div> 
                                    
                <div class="span5" style="padding-left: 28px">
                                    <label for="formCropCost_costo_costRentPro" class="control-label">
                                        Costos de arrendamiento:
                                    </label>
                                    <div class="controls">
                                       <s:textfield name="costo.costRentPro" />
                                    </div>  
                                     
                    </div> 
             
        </div>              
                                        <div id ="divcostgranel" class="row"> 
                                            <div class="span5" >
                                                <div class="control-group">
                                                    <label for="formCropCost_costo_costTechnicalAssistancePro" class="control-label">
                                                        Costos de asistencia técnica:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="costo.costTechnicalAssistancePro"/>
                                                    </div>                         
                                                </div>                          
                                            </div> 
                                            <div class="span5" style="padding-left: 28px">
                                                <div class="control-group">
                                                    <label for="formCropCost_costo_costImpuestoPro" class="control-label">
                                                       Costos de impuestos:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="costo.costImpuestoPro" />
                                                    </div>                         
                                                </div>        

                                            </div>

                                        </div>                
                                   
                                                    
                                        
            <div id ="divcostmechanized" class="row" >
                                      <div class="span5" >
                                            <div class="control-group">
                                                <label for="formCropCost_costo_costAdministrationPro"class="control-label">
                                                   Costos de administración:
                                                </label>
                                                <div class="controls">
                                                   <s:textfield name="costo.costAdministrationPro" />
                                                </div>                         
                                        </div>  
                                      </div>
                                                    <div id="divCostStorage" >      
                                                        <div class="span5" style="padding-left: 28px">
                                                            <div class="control-group">
                                                                <label for="formCropCost_costo_costOthersPro" class="control-label">
                                                                  Costos Otros Servicios:
                                                                </label>
                                                                <div class="controls">
                                                                    <s:textfield name="costo.costOthersPro" />
                                                                </div>                         
                                                            </div>        

                                                        </div>
                                                    </div>

            </div> 
          <div id ="divcostmanual" >                                        
          <div id ="divcostmanual0" class="row"> 
             <div class="span5" >
                                <div class="control-group">
                                    <label for="formCropCost_costo_costInterestsPro"  class="control-label">
                                        Intereses financieros:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="costo.costInterestsPro"/>
                                    </div>                         
                                </div>                          
            </div> 
            
            </div>
                                   
        </div>        
                                                               
         
                                  
   
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
            <script>
                                   
               $("#formCropCos_costo_costVigilantPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costRentPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costTechnicalAssistancePro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costImpuestoPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costAdministrationPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costOthersPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costInterestsPro").maskMoney({suffix: '$'});
               
            </script>                 
   </fieldset>      
        
     <div style="margin-bottom: 15px" id="divBtHarvest">
        <% String actExeHar   = String.valueOf(request.getAttribute("actExe")); %>
        <% if ((actExeHar=="create" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExeHar=="modify" && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
            <% if (entTypeCostId!=3) { %>
                <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="removeMask();searchDecimalNumber('formCropCos'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeCos" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.savecost.costindirect')" /></sj:submit>
            <% } %>
        <% } %>
    </div>
   
</s:form>	
<script>
    $.subscribe('completeCos', function(event, data) {
        
               $("#formCropCos_costo_costVigilantPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costRentPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costTechnicalAssistancePro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costImpuestoPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costAdministrationPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costOthersPro").maskMoney({suffix: '$'});
               $("#formCropCos_costo_costInterestsPro").maskMoney({suffix: '$'});
        completeFormCrop('', 'formCropCos', 'divMessCost', event.originalEvent.request.responseText);
    });
    //showCostTypeHarvest('formCropHar_harv_harvestType_idHarTyp', 'divcostgranel', 'divcostbulto','divcostmanual3');
    //showCostMethodsHarvest('formCropHar_harv_harvestMethods_idHarMet', 'divcostmechanized', 'divcostmanual','divcostmanual3');
    //showStorageHar('harv.storageHar', 'divCostStorage', 'divcostrented');
$(function(){ $("#formCropCost_costo_costVigilantPro").maskMoney({suffix: '$'});
});

    function removeMask() {       
                    $("#formCropCos_costo_costVigilantPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costVigilantPro").maskMoney('mask');

                    $("#formCropCos_costo_costRentPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costRentPro").maskMoney('mask');
                    
                    $("#formCropCos_costo_costTechnicalAssistancePro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costTechnicalAssistancePro").maskMoney('mask');

                    $("#formCropCos_costo_costImpuestoPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costImpuestoPro").maskMoney('mask');
                    
                    $("#formCropCos_costo_costOthersPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costOthersPro").maskMoney('mask');

                    $("#formCropCos_costo_costInterestsPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costInterestsPro").maskMoney('mask');
                    
                    $("#formCropCos_costo_costAdministrationPro").maskMoney({thousands:"", decimal:'.'});
                    $("#formCropCos_costo_costAdministrationPro").maskMoney('mask');
                    
    }
   
</script>