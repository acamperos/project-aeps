<%@page import="java.util.HashMap"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<% String coCode   = (String) session.getAttribute(APConstants.COUNTRY_CODE); %>
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
                    <legend><s:property value="getText('title.formirrigation.irrigation')" /></legend>
                    <div id="divInfoRiego">                        
                        <% String classLamina = "hide"; %>
                        <% String classWet    = "hide"; %>
                        <% if (coCode.equals("NI")) { %>
                            <div class="row">
                                <div class="span5">
                                    <label for="formCropIrr_irr_whatDoYouUseIrr_idUseIrr" class="control-label req">
                                        <s:property value="getText('radio.irrwhatdoyouuse.irrigation')" />:
                                    </label>
                                    <div class="controls radioSelect">
                                        <s:radio list="type_uses_irr" listKey="idUseIrr" listValue="nameUseIrr" onclick="showSelectionIrrigation(this.value, 'divLamina', 'divWet')" name="irr.whatDoYouUseIrr.idUseIrr" />
                                    </div>
                                </div> 
                            </div> 
                        <% } else if (coCode.equals("CO")) { %>
                            <% classLamina = ""; %>
                        <% } %>
                        <s:set name="whatUse" value="irr.whatDoYouUseIrr.idUseIrr"/>
                        <s:if test="%{#whatUse==1}">
                            <% classLamina = ""; %>
                            <% classWet    = "hide"; %>
                        </s:if>      
                        <s:elseif test="%{#whatUse==2}">
                            <% classLamina = "hide"; %>
                            <% classWet    = ""; %>
                        </s:elseif>
                        <div class="<%= classLamina %>" id="divLamina">
                            <div class="row">
                                <div class="span5">
                                    <s:hidden name="idCrop"/>
                                    <s:hidden name="typeCrop"/>
                                    <s:hidden name="actExe"/>
                                    <s:hidden name="irr.idIrr"/>
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_dateIrr" class="control-label req">
                                            <s:property value="getText('text.irrdate.irrigation')" />:
                                        </label>
                                        <div class="date controls">
                                            <s:date name="irr.dateIrr" format="MM/dd/yyyy" var="dateTransformIrr"/>
                                            <s:textfield name="irr.dateIrr" value="%{#dateTransformIrr}" readonly="true"/>
                                            <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>                          
                                    </div>                          
                                </div>   
                                            <% if (coCode.equals("CO")) { %>

                                            <div class="span5">
                                                <div class="control-group">
                                                    <label for="formCropIrr_irr_irrigationsTypes_idIrrTyp" class="control-label req">
                                                        <s:property value="getText('select.irrtypes.irrigation')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:select
                                                            name="irr.irrigationsTypes.idIrrTyp"
                                                            list="type_irr_typ" 
                                                            listKey="idIrrTyp" 
                                                            listValue="nameIrrTyp"            
                                                            headerKey="-1" 
                                                            headerValue="---"
                                                            onchange="showTypeIrrigations('formCropIrr_irr_irrigationsTypes_idIrrTyp', 'divcostsprinkling', 'divcostgravity', 'divMechanicCon', 'divMechanizedCon', 'divManualCon','divIrrRented');
                                                  chargeValuesIrrigations('/crop/comboControls.action?typeCrop=%{typeCrop}', 'idTarget', 'con.targetsTypes.idTarTyp', 'typeCon', 'formCropCon_con_controlsTypes_idConTyp', 'formCropCon_con_chemicalsControls_idCheCon', 'formCropCon_con_organicControls_idOrgCon', 'divMessage');
                                                  hideInformationIrrigation('divNewObjControlPes', 'divNewObjControlWee', 'divNewObjControlDis', 'divNewProCheCon', 'divNewProOrgCon');"
                                                            />
                                                    </div>                         
                                                </div>                          
                                            </div>   
                                            <% } %>
                            
                                
                                            
                            </div>  
                            <% if (coCode.equals("NI")) { %>
                                <div class="row">
                                    <div class="span5">
                                        <div class="control-group">
                                            <label for="formCropIrr_irr_thicknessSheetIrr" class="control-label">
                                                <s:property value="getText('text.thicknessheetirr.irrigation')" />:
                                            </label>
                                            <div class="controls">
                                                <s:textfield name="irr.thicknessSheetIrr"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <% } %>
                        </div>
                        <div class="<%= classWet %>" id="divWet">
                            <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_dateWetIrr" class="control-label req">
                                            <s:property value="getText('text.irrdatewet.irrigation')" />:
                                        </label>
                                        <div class="date controls">
                                            <s:date name="irr.dateWetIrr" format="MM/dd/yyyy" var="dateTransformWetIrr"/>
                                            <s:textfield name="irr.dateWetIrr" value="%{#dateTransformWetIrr}" readonly="true"/>
                                            <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>                          
                                    </div>                          
                                </div>       
                            </div>  
                            <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_durationIrr" class="control-label">
                                            <s:property value="getText('text.durationirr.irrigation')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="irr.durationIrr"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> 
                        <% if (coCode.equals("CO")) { %>
                           <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_amountIrr" class="control-label">
                                            <s:property value="getText('text.amountirr.irrigation')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="irr.amountIrr"/>
                                        </div>
                                    </div>
                                </div>                           
                                        
                            </div>
                        <% } %>
                        <div id="costirrigation" >
                        <fieldset>
                             <legend><s:property value="getText('title.formirrigationcost.irrigation')" /></legend>
                         <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_costwaterIrr" class="control-label">
                                            <s:property value="getText('text.costwaterirr.irrigation')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="irr.costWaterIrr"/>
                                        </div>
                                    </div>
                                </div>  
                                        
                                <div class="span5">
                                      <div class="control-group">
                                          <label for="formCropIrr_irr_costworkforceIrr" class="control-label">
                                              <s:property value="getText('text.costworkforceirr.irrigation')" />:
                                                    </label>
                                                    <div class="controls">
                                              <s:textfield name="irr.costWorkforceIrr"/>
                                                    </div>
                                      </div>
                                </div>  
                                        
                        </div>
                                                    
                       <div id="divcostgravity" class="row">
                                <div  class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_costditchesIrr" class="control-label">
                                            <s:property value="getText('text.costditchesirr.irrigation')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="irr.costDitchesIrr"/>
                                        </div>
                                    </div>
                                </div>  
                                        
                                 <div  class="span5">
                                      <div class="control-group">
                                          <label for="formCropIrr_irr_costdrainageIrr" class="control-label">
                                              <s:property value="getText('text.costdrainageirr.irrigation')" />:
                                                    </label>
                                                    <div class="controls">
                                              <s:textfield name="irr.costDrainageIrr"/>
                                                    </div>
                                      </div>
                                </div>  
                                        
                        </div>
                                                    
                        <div id="divcostsprinkling" class="row">
                                <div  class="span5">
                                    <div class="control-group">
                                        <label for="formCropIrr_irr_costfuelIrr" class="control-label">
                                            <s:property value="getText('text.costfuelirr.irrigation')" />:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="irr.costFuelIrr"/>
                                        </div>
                                    </div>
                                </div>  
                                        
                                 <div  class="span5">
                                      <div class="control-group">
                                          <label for="formCropIrr_irr_costelectricpowerIrr" class="control-label">
                                              <s:property value="getText('text.costelectricpowerirr.irrigation')" />:
                                                    </label>
                                                    <div class="controls">
                                                        <s:textfield name="irr.costElectricpowerIrr"/>
                                                    </div>
                                      </div>
                                </div>  
                                        
                        </div>

                                              <div id="divIrrRented" class="row" >
                                                  <div  class="span5">
                                                      <div class="control-group">
                                                          <label for="formCropIrr_rentedquestionIrr" class="control-label">
                                                              <s:property value="getText('radio.rentedquestionirr.irrigation')" />:
                                                          </label>
                                                          <div class="controls radioSelect">
                                                              <s:radio
                                                                       name="irr.costRentedquestionIrr"
                                                                       list="#{'true':'Alquilado', 'false':'Propio'}" 
                                                                       onclick="showRentedquestionIrrigations('irr.costRentedquestionIrr', 'divcostown', 'divcostrented');"
                                                                       />
                                                          </div>
                                                      </div>
                                                  </div>
                                                          
                                                              <div id="divcostown" class="span5">
                                                                  <div class="control-group">
                                                                      <label for="formCropIrr_irr_costdepreciationIrr" class="control-label">
                                                                          <s:property value="getText('text.costdepreciationirr.irrigation')" />:
                                                                      </label>
                                                                      <div class="controls">
                                                                          <s:textfield name="irr.costDepreciationIrr"/>
                                                                      </div>
                                                                  </div>
                                                              </div>  
                                                              
                                                              <div id="divcostrented" class="span5">
                                                                  <div class="control-group">
                                                                      <label for="formCropIrr_irr_costrentedIrr" class="control-label">
                                                                          <s:property value="getText('text.costrentedirr.irrigation')" />:
                                                                      </label>
                                                                      <div class="controls">
                                                                          <s:textfield name="irr.costRentedIrr"/>
                                                                      </div>
                                                                  </div>
                                                              </div>  

                                                          
                                              </div>
                        </fieldset>                  
                      </div>
                        
                    </div>	
                    <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                    <script>
                        $("#formCropIrr_irr_dateIrr").datepicker({dateFormat: 'mm/dd/yy', showOn: "focus"});
                        $("#formCropIrr_irr_dateIrr").mask("99/99/9999", {placeholder: " "});
//                        $("#formCropIrr_irr_depthIrr").numeric({decimal: false, negative: false});
                        $("#formCropIrr_irr_amountIrr").numeric({negative: false});
                        $("#formCropIrr_irr_thicknessSheetIrr").numeric({negative: false});
                        $("#formCropIrr_irr_durationIrr").numeric({negative: false});
                        $("#formCropIrr_irr_amountIrr").val(parsePointSeparated($("#formCropIrr_irr_amountIrr").val())); 
                        $("#formCropIrr_irr_thicknessSheetIrr").val(parsePointSeparated($("#formCropIrr_irr_thicknessSheetIrr").val())); 
                        $("#formCropIrr_irr_durationIrr").val(parsePointSeparated($("#formCropIrr_irr_durationIrr").val())); 
                        $("#formCropIrr_irr_dateWetIrr").datepicker({dateFormat: 'mm/dd/yy', showOn: "focus"});
                        $("#formCropIrr_irr_dateWetIrr").mask("99/99/9999", {placeholder: " "});
                        showTypeIrrigations('formCropIrr_irr_irrigationsTypes_idIrrTyp', 'divcostsprinkling', 'divcostgravity', 'divMechanicCon', 'divMechanizedCon', 'divManualCon','divIrrRented');
                        showRentedquestionIrrigations('irr.costRentedquestionIrr', 'divcostown', 'divcostrented');
                    </script>
                    <div id="divBtIrr">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropIrr'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeIrr" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saveirr.irrigation')" /></sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropIrr'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                    </div>
                </fieldset>
            </s:form>	
            <script>        
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeIrr', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropIrr', 'divIrr', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchIrr.action?idCrop="+$("#formCropIrr_idCrop").val(), "divListIrr");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListIrrForm"></div>
    </body>
</html>