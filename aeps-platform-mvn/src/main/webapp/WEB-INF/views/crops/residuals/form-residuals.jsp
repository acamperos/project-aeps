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
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div id="divResForm">
            <s:form id="formCropRes" action="saveResidual" cssClass="form-horizontal">
                <fieldset>
                    <legend><s:property value="getText('title.formresidual.residual')" /></legend>
                    <div class="row">
                        <div class="span5">
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="resMan.idResMan"/>                          
                            <div class="control-group">
                                <label for="formCropRes_resMan_dateResMan" class="control-label req">
                                    <s:property value="getText('text.dateresidual.residual')" />:
                                </label>
                                <div class="date controls">
                                    <s:date name="resMan.dateResMan" format="MM/dd/yyyy" var="dateTransformRes"/>
                                    <s:textfield name="resMan.dateResMan" value="%{#dateTransformRes}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[mm/dd/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>    
                         <div class="span5" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formCropRes_resMan_residualsClasification_idResCla" class="control-label req">
                                    <s:property value="getText('select.residualclasification.residual')" />:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="resMan.residualsClasification.idResCla"
                                        list="type_res_clas" 
                                        listKey="idResCla" 
                                        listValue="nameResCla"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showOtherElement(this.value, 'divNewManageStub');"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                    </div>
                               
               <div class="row">
                   <div class="span5">
                        <div id="costresiduals" class="control-group">

                            <label for="formCropRes_resMan_costResMan" class="control-label"  >
                                        <s:property value="getText('text.cost.residual')"   />:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="resMan.costResMan"/>
                                    </div>

                                </div>        
               </div>      
                                    
                       <% String classNewRes="hide"; %>
                        <s:set name="idResidual" value="resMan.residualsClasification.idResCla"/>
                        <s:if test="%{#idResidual==1000000}">
                            <% classNewRes = "";%>
                        </s:if>  
                        <div class="span4 <%= classNewRes %>" id="divNewManageStub" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formCropRes_resMan_otherResidualsManagementResMan" class="control-label req">
                                     <s:property value="getText('text.otherresidualmanage.residual')" />:
                                </label>
                                <div class="controls">
                                    <s:textfield name="resMan.otherResidualsManagementResMan"/>
                                </div>
                            </div>
                        </div>
                       
               </div>  
                <fieldset>
                <legend><s:property value="getText('title.formresidualcomment.residual')" /></legend>                   
                <div class="row">
                   <div class="span5">
                        <div  class="control-group">

                                    <div class="controls">
                                      
                                        <s:textarea rows="5" cssClass="span6" name="resMan.commentResMan" ></s:textarea>
                                    </div>

                                </div>        
               </div>                     
               </div>    
                </fieldset>
                    <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                    <script>
                        $("#formCropRes_resMan_dateResMan").datepicker({dateFormat: 'mm/dd/yy'});
                        $("#formCropRes_resMan_dateResMan").mask("99/99/9999", {placeholder: " "});
                        $("#formCropRes_resMan_costResMan").maskMoney({suffix: ' $'});
                    </script>
                    <div id="divBtRes">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="removeMask(); addMessageProcess()" targets="divMessage" onCompleteTopics="completeRes" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saveresidual.residual')" /></sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropRes'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                    </div>
                </fieldset>
            </s:form>	
            <script>    
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeRes', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropRes', 'divRes', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchResidual.action?idCrop="+$("#formCropRes_idCrop").val(), "divListRes");
                    }, 2000);
                });
                
      function removeMask() {       
            $("#formCropRes_resMan_costResMan").maskMoney({thousands:"", decimal:'.'});
            $("#formCropRes_resMan_costResMan").maskMoney('mask');            
    };
            </script>
        </div>
        <div class="row-fluid" id="divListResForm"></div>
    </body>
</html>
