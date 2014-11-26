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
        <div class="row-fluid" id="divFerForm">
            <s:form id="formCropFer" action="saveFer" cssClass="form-horizontal">
                <fieldset>
                    <legend>Formulario de Fertilizacion</legend>  
                    <div class="row">
                        <div class="span5">
                            <% String actionOpt = String.valueOf(request.getAttribute("actExe"));%>
                            <% String rowNew    = String.valueOf(request.getAttribute("rowNew"));%>
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="fer.idFer"/>
                            <div class="control-group">
                                <label for="formCropFer_fer_dateFer" class="control-label req">
                                    Fecha de aplicación:
                                </label>
                                <div class="date controls">
                                    <s:date name="fer.dateFer" format="dd/MM/yyyy" var="dateTransformFer"/>
                                    <s:textfield name="fer.dateFer" value="%{#dateTransformFer}" readonly="true" onchange="seeDate(this.value, 'lblDateApp')"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <div style="margin-bottom: 30px">
                        <label class="textFloat">
                            A continuacion, listar los fertilizantes que fueron usados en                            
                        </label>
                        <label id="lblDateApp" class="textFloat">&nbsp; esta aplicacion</label>
                    </div>            
                    <fieldset>
                        <div class="span3">
                            <button type="button" class="btn btn-chemical btn-large" onclick="showRowAdditionalFert('../crop/showRowAddFer?action=<%=actionOpt%>', '1', 'tableAditFertChem', 'tableAditFertOrg', 'tableAditFertAmen')"><i class="icon-plus"></i> Agregar fertilizante Quimico</button>
                        </div>
                        <table class="table" style="width: 100%;">
                            <tbody id="tableAditFertChem">
                                <s:if test="chemFert.size()>0">
                                    <s:iterator value="chemFert" var="chemical" status="estatus">
                                        <s:include value="row_chemical_fer.jsp">
                                            <s:param name="numRows" value="#estatus.index+1" />
                                            <s:param name="actionOpt" value="{request.actExe}" />
                                        </s:include>
                                    </s:iterator>
                                </s:if>   
                                <s:else>
                                    <tr value="0">
                                    </tr>   
                                </s:else>
                            </tbody>
                        </table>
                    </fieldset>
                    <fieldset>
                        <div class="span3">
                            <button type="button" class="btn btn-organic btn-large" onclick="showRowAdditionalFert('../crop/showRowAddFer?action=<%=actionOpt%>', '2', 'tableAditFertChem', 'tableAditFertOrg', 'tableAditFertAmen')"><i class="icon-plus"></i> Agregar fertilizante Organico</button>
                        </div>
                        <table class="table" style="width: 100%;">
                            <tbody id="tableAditFertOrg">
                                <s:if test="orgFert.size()>0">
                                    <s:iterator value="orgFert" var="organic" status="estatus">
                                        <s:include value="row_organic_fer.jsp">
                                            <s:param name="numRows" value="#estatus.index+1" />
                                            <s:param name="actionOpt" value="{request.actExe}" />
                                        </s:include>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <tr value="0">
                                    </tr>   
                                </s:else>
                            </tbody>
                        </table>
                    </fieldset>
                    <fieldset>
                        <div class="span3">
                            <button type="button" class="btn btn-amendment btn-large" onclick="showRowAdditionalFert('../crop/showRowAddFer?action=<%=actionOpt%>', '3', 'tableAditFertChem', 'tableAditFertOrg', 'tableAditFertAmen')"><i class="icon-plus"></i> Agregar fertilizante de Enmienda</button>
                        </div>
                        <table class="table" style="width: 100%;">
                            <tbody id="tableAditFertAmen">
                                <s:if test="amenFert.size()>0">
                                    <s:iterator value="amenFert" var="amendment" status="estatus">
                                        <s:include value="row_amendment_fer.jsp">
                                            <s:param name="numRows" value="#estatus.index+1" />
                                            <s:param name="actionOpt" value="{request.actExe}" />
                                        </s:include>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <tr value="0">
                                    </tr>   
                                </s:else>
                            </tbody>
                        </table>
                    </fieldset>
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        showTypeFertilizerSel('formCropFer_fer_fertilizationsTypes_idFerTyp', 'divQuimicoFer', 'divOrganicoFer', 'divEnmiendasFer');
                        $("#formCropFer_fer_dateFer").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropFer_fer_dateFer").mask("99/99/9999", {placeholder: " "});
                        
                        $("#formCropFer_amountProductUsedOrg").numeric({ negative: false });
                        $("#formCropFer_amountProductUsedOrg").val(parsePointSeparated($("#formCropFer_amountProductUsedOrg").val())); 
                        
                        $("#formCropFer_amountProductUsedAme").numeric({ negative: false });
                        $("#formCropFer_amountProductUsedAme").val(parsePointSeparated($("#formCropFer_amountProductUsedAme").val())); 
                    </script>
                    <div id="divBtFer">
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify"))) { %>
                            <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formCropFer'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeFer" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Fertilizacion</sj:submit>
                        <% } %>
                        <button class="btn btn_default btn-large" onclick="resetForm('formCropFer'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
                    </div>
                </fieldset>
            </s:form>	
            <script>      
                $.ui.dialog.prototype._focusTabbable = function(){};
                $.subscribe('completeFer', function(event, data) {             
                    completeFormGetting('dialog-form', 'formCropFer', 'divFer', event.originalEvent.request.responseText);
                    setTimeout(function() {
                        showInfo("/crop/searchFer.action?idCrop="+$("#formCropFer_idCrop").val(), "divListFer");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListFerForm"></div>
    </body>
</html>