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
        <div class="row-fluid" id="divIrrForm">
            <s:form id="formCropIrr" action="saveIrr" cssClass="form-horizontal">
                <fieldset>
                    <legend><s:property value="getText('title.formirrigation.irrigation')" /></legend>
                    <div id="divInfoRiego">
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
                                        />
                                    </div>                         
                                </div>                          
                            </div>   
                        </div>
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
                    </div>	
                    <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                    <script>
                        $("#formCropIrr_irr_dateIrr").datepicker({dateFormat: 'dd/mm/yy', showOn: "focus"});
                        $("#formCropIrr_irr_dateIrr").mask("99/99/9999", {placeholder: " "});
//                        $("#formCropIrr_irr_depthIrr").numeric({decimal: false, negative: false});
                        $("#formCropIrr_irr_amountIrr").numeric({negative: false});
                        $("#formCropIrr_irr_amountIrr").val(parsePointSeparated($("#formCropIrr_irr_amountIrr").val())); 
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