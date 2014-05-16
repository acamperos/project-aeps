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
                            <s:hidden name="idCrop"/>
                            <s:hidden name="typeCrop"/>
                            <s:hidden name="actExe"/>
                            <s:hidden name="fer.idFer"/>
                            <div class="control-group">
                                <label for="formCropFer_fer_dateFer" class="control-label req">
                                    Fecha del aplicación:
                                </label>
                                <div class="date controls">
                                    <s:date name="fer.dateFer" format="dd/MM/yyyy" var="dateTransformFer"/>
                                    <s:textfield name="fer.dateFer" value="%{#dateTransformFer}" readonly="true"/>
                                    <span class="prefix sec">&nbsp;[dd/mm/yyyy]</span>
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>                          
                            </div>                          
                        </div>       
                    </div>
                    <div class="row">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropFer_fer_fertilizationsTypes_idFerTyp" class="control-label req">
                                    Tipo:
                                </label>
                                <div class="controls">
                                    <s:select
                                        name="fer.fertilizationsTypes.idFerTyp"
                                        list="type_fer_typ" 
                                        listKey="idFerTyp" 
                                        listValue="nameFerTyp"            
                                        headerKey="-1" 
                                        headerValue="---"
                                        onchange="showTypeFertilizerSel('formCropFer_fer_fertilizationsTypes_idFerTyp', 'divQuimicoFer', 'divOrganicoFer', 'divEnmiendasFer')"
                                    />
                                </div>                         
                            </div>                          
                        </div>   
                    </div>
                    <div id="divQuimicoFer" class="hide">
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropFer_ferChe_applicationTypes_idAppTyp" class="control-label req">
                                        Tipo de aplicación:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="ferChe.applicationTypes.idAppTyp"
                                            list="list_app_typ" 
                                            listKey="idAppTyp" 
                                            listValue="nameAppTyp"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElementChemical('formCropFer_ferChe_chemicalFertilizers_idCheFer', 'formCropFer_ferChe_applicationTypes_idAppTyp', 'divNewProQui')"
                                        />
                                    </div>                         
                                </div>                          
                            </div>   
                        </div> 
                        <div class="row">
                            <div class="span5">
                                <s:hidden name="ferChe.idCheFer"/>
                                <div class="control-group">
                                    <label for="formCropFer_ferChe_chemicalFertilizers_idCheFer" class="control-label req">
                                        Producto:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="ferChe.chemicalFertilizers.idCheFer"
                                            list="type_prod_che" 
                                            listKey="idCheFer" 
                                            listValue="nameCheFer"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElementChemical('formCropFer_ferChe_chemicalFertilizers_idCheFer', 'formCropFer_ferChe_applicationTypes_idAppTyp', 'divNewProQui')"
                                        />
                                    </div>                         
                                </div>                          
                            </div> 
                        </div> 
                        <% String classNewProChe="hide"; %>
                        <s:set name="idCheFer" value="ferChe.chemicalFertilizers.idCheFer"/>
                        <s:if test="%{#idCheFer==1000000}">
                            <% classNewProChe = "";%>
                        </s:if> 
                        <div class="<%= classNewProChe %>" id="divNewProQui">
                            <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropFer_ferChe_otherProductCheFer" class="control-label req">
                                             Otro producto:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="ferChe.otherProductCheFer"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--<div class="row">-->
                                <!--<div class="span12">-->
                                    <div class="row" style="margin-left:153px">
                                        <table class="tableComposition" style="width:50%">
                                            <thead>
                                                <tr>
                                                    <td colspan="2"><label>Manejar valores en porcentajes</label></td>
                                                </tr>
                                            </thead>
                                            <s:iterator value="additionalsElem" var="element" status="estatus">     
                                                <s:if test="%{#estatus.index==0}">   
                                                    <tr>
                                                </s:if>    
                                                <s:if test="%{#estatus.index>=0 && #estatus.index<=4}">                                                        
                                                    <td>
                                                        <div class="span1">
                                                            <div class="control-group">
                                                                <div class="input-prepend controls" style="margin-left:0">
                                                                    <span class="add-on req"><s:property value="nameCheEle" /></span>
                                                                    <s:textfield name="additionalsElem[%{#estatus.index}].valueCheEle" value="%{#attr.valueCheEle}" cssClass="input-degrees"/>
                                                                    <s:hidden name="additionalsElem[%{#estatus.index}].idCheEle" value="%{#attr.idCheEle}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </s:if>                                                    
                                                <s:if test="%{#estatus.index==4}">   
                                                    </tr>
                                                </s:if> 
                                                <s:if test="%{#estatus.index==5}">   
                                                    <tr>
                                                </s:if>    
                                                <s:if test="%{#estatus.index>=5 && #estatus.index<=additionalsElem.size()}">                                                        
                                                    <td>
                                                        <div class="span1">
                                                            <div class="control-group">
                                                                <div class="input-prepend controls" style="margin-left:0">
                                                                    <span class="add-on req"><s:property value="nameCheEle" /></span>
                                                                    <s:textfield name="additionalsElem[%{#estatus.index}].valueCheEle" value="%{#attr.valueCheEle}" cssClass="input-degrees"/>
                                                                    <s:hidden name="additionalsElem[%{#estatus.index}].idCheEle" value="%{#attr.idCheEle}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </s:if>
                                                <s:if test="%{#estatus.index==additionalsElem.size()}">   
                                                    </tr>
                                                </s:if> 
                                                <script>			
                                                    $("#formCropFer_additionalsElem_0__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_1__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_2__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_3__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_4__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_5__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_6__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_7__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_8__valueCheEle").numeric({ negative: false });
                                                    $("#formCropFer_additionalsElem_9__valueCheEle").numeric({ negative: false });
                                                </script>
                                            </s:iterator>
                                        </table>
                                    </div>
<!--                                </div>
                            </div>-->
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropFer_amountProductUsedChe" class="control-label req">
                                        Cantidad de producto:
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="amountProductUsedChe"/>
                                    </div>                         
                                </div>                          
                            </div>   
                            <div class="span4" style="padding-left: 28px">
                                <div class="control-group">
                                    <label for="ferChe_unitCheFer" class="control-label req">
                                        Unidad:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="ferChe.unitCheFer"
                                            list="#{'1':'kg/ha', '2':'lt/ha'}"           
                                            headerKey="-1" 
                                            headerValue="---"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>                            
                    </div>                           
                    <div id="divOrganicoFer" class="hide">
                        <div class="row">
                            <div class="span5">
                                <s:hidden name="ferOrg.idOrgFer"/>
                                <div class="control-group">
                                    <label for="formCropFer_ferOrg_organicFertilizers_idOrgFer" class="control-label req">
                                        Producto:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="ferOrg.organicFertilizers.idOrgFer"
                                            list="type_prod_org" 
                                            listKey="idOrgFer" 
                                            listValue="nameOrgFer"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElement(this.value, 'divNewProOrg')"
                                        />
                                    </div>                         
                                </div>                          
                            </div> 
                        </div> 
                        <% String classNewProOrg="hide"; %>
                        <s:set name="idCheOrg" value="ferOrg.organicFertilizers.idOrgFer"/>
                        <s:if test="%{#idCheOrg==1000000}">
                            <% classNewProOrg = "";%>
                        </s:if> 
                        <div class="<%= classNewProOrg %>" id="divNewProOrg">
                            <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropFer_ferOrg_otherProductOrgFer" class="control-label req">
                                             Otro producto:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="ferOrg.otherProductOrgFer"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropFer_amountProductUsedOrg" class="control-label req">
                                        Cantidad de producto (kg/ha):
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="amountProductUsedOrg"/>
                                    </div>                         
                                </div>                          
                            </div>                               
                        </div>                            
                    </div>
                    <div id="divEnmiendasFer" class="hide">
                        <div class="row">
                            <div class="span5">
                                <s:hidden name="ferAme.idAmeFer"/>
                                <div class="control-group">
                                    <label for="formCropFer_ferAme_amendmentsFertilizers_idAmeFer" class="control-label req">
                                        Producto:
                                    </label>
                                    <div class="controls">
                                        <s:select
                                            name="ferAme.amendmentsFertilizers.idAmeFer"
                                            list="type_prod_ame" 
                                            listKey="idAmeFer" 
                                            listValue="nameAmeFer"            
                                            headerKey="-1" 
                                            headerValue="---"
                                            onchange="showOtherElement(this.value, 'divNewProAme')"
                                        />
                                    </div>                         
                                </div>                          
                            </div> 
                        </div> 
                        <% String classNewProAme="hide"; %>
                        <s:set name="idCheAme" value="ferAme.amendmentsFertilizers.idAmeFer"/>
                        <s:if test="%{#idCheAme==1000000}">
                            <% classNewProAme = "";%>
                        </s:if> 
                        <div class="<%= classNewProAme %>" id="divNewProAme">
                            <div class="row">
                                <div class="span5">
                                    <div class="control-group">
                                        <label for="formCropFer_ferAme_otherProductAmeFer" class="control-label req">
                                             Otro producto:
                                        </label>
                                        <div class="controls">
                                            <s:textfield name="ferAme.otherProductAmeFer"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>                                        
                        <div class="row">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formCropFer_amountProductUsedAme" class="control-label req">
                                        Cantidad de producto (kg/ha):
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="amountProductUsedAme"/>
                                    </div>                         
                                </div>                          
                            </div>   
                        </div>                            
                    </div> 
                    <p class="warnField reqBef">Campos Requeridos</p>
                    <script>
                        showTypeFertilizerSel('formCropFer_fer_fertilizationsTypes_idFerTyp', 'divQuimicoFer', 'divOrganicoFer', 'divEnmiendasFer');
                        $("#formCropFer_fer_dateFer").datepicker({dateFormat: 'dd/mm/yy'});
                        $("#formCropFer_fer_dateFer").mask("99/99/9999", {placeholder: " "});
                        $("#formCropFer_amountProductUsedChe").numeric({ negative: false });
                        $("#formCropFer_amountProductUsedChe").val(parsePointSeparated($("#formCropFer_amountProductUsedChe").val())); 
                        
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
                        showInfo("/aeps-plataforma-mvn/crop/searchFer.action?idCrop="+$("#formCropFer_idCrop").val(), "divListFer");
                    }, 2000);
                });
            </script>
        </div>
        <div class="row-fluid" id="divListFerForm"></div>
    </body>
</html>