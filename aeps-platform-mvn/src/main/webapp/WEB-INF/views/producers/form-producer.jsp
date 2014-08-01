<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <s:form id="formProducer" action="saveProducer.action" cssClass="form-horizontal formClassProducer">
            <fieldset>
                <legend>Formulario de un productor</legend>
                <div class="control-group">
                    <s:hidden name="idProducer"/>
                    <label for="formProducer_typeIdent" class="control-label req">
                        Tipo de documento:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccione un tipo de documento." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:select            
                            name="typeIdent" 
                            list="type_ident_producer" 
                            listKey="acronymDocTyp" 
                            listValue="nameDocTyp" 
                            headerKey="-1" 
                            headerValue="---"
                            onchange="selConf(this.value, 'formProducer_num_ident_producer');
                                      showOtherTypeDocument(this.value, 'divInfoCompany', 'divInfoPerson');"
                        />
                    </div>  
                </div>  
                <div class="control-group">
                    <label for="formProducer_num_ident_producer" class="control-label req">
                        Numero de documento:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su numero de cedula." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="num_ident_producer" />
                    </div>  
                </div> 
                <% String classInfoPerson  = "hide"; %>
                <% String classInfoCompany = "hide"; %>
                <s:set name="typeDoc" value="typeIdent"/>
                <s:if test="%{#typeDoc.equals('NIT')}">
                    <% classInfoCompany = "";%>
                </s:if>      
                <s:elseif test="%{!(#typeDoc.equals(''))}">
                    <% classInfoPerson = "";%>
                </s:elseif>
                <div class="<%= classInfoCompany %>" id="divInfoCompany">
                    <div class="control-group">
                        <label for="formProducer_dig_ver_producer" class="control-label req">
                            Digito de verificación:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su digito de verificación." data-title="Información" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield name="dig_ver_producer" />
                        </div>  
                    </div>
                    <div class="control-group">
                        <label for="formProducer_nameCompany" class="control-label req">
                            Nombre de la empresa:
                        </label>
                        <div class="controls">
                            <s:textfield name="nameCompany"/>
                        </div>                         
                    </div> 
                    <h4>Información del responsable:</h4>   
                    <hr />
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_firstNameRep" class="control-label req">Primer nombre:</label>
                                <div class="controls">
                                    <s:textfield name="firstNameRep"/>
                                </div>
                            </div>
                        </div>
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_secondNameRep" class="control-label">Segundo nombre:</label>
                                <div class="controls">
                                    <s:textfield name="secondNameRep"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_firstLastNameRep" class="control-label req">Primer apellido:</label>
                                <div class="controls">
                                    <s:textfield name="firstLastNameRep"/>
                                </div>
                            </div>
                        </div>
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_secondLastNameRep" class="control-label">Segundo apellido:</label>
                                <div class="controls">
                                    <s:textfield name="secondLastNameRep"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="formProducer_emailRes" class="control-label req">
                            Correo electrónico del responsable:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese el correo electrónico del responsable." data-title="Información" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield type="email" name="emailRes" />
                        </div>  
                    </div>
                    <h4>Información adicional de la empresa:</h4>   
                    <hr />        
                </div>   
                <div class="<%= classInfoPerson %>" id="divInfoPerson">      
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_names_producer_1" class="control-label req">
                                    Primer nombre:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su primer nombre." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="names_producer_1" />
                                </div>  
                            </div>
                        </div>  
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_names_producer_2" class="control-label">
                                    Segundo nombre:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su segundo nombre." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="names_producer_2" />
                                </div>  
                            </div>
                        </div>
                    </div> 
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_last_names_producer_1" class="control-label req">
                                    Primer apellido:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su primer apellido." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="last_names_producer_1" />
                                </div>  
                            </div>
                        </div>  
                        <div class="span3" style="padding-left: 28px">
                            <div class="span5">
                                <div class="control-group">
                                    <label for="formProducer_last_names_producer_2" class="control-label">
                                        Segundo apellido:
                                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su segundo apellido." data-title="Información" data-placement="right" data-trigger="hover"></i>
                                    </label>
                                    <div class="controls">
                                        <s:textfield name="last_names_producer_2" />
                                    </div>  
                                </div>
                            </div>  
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label for="formProducer_direction_producer" class="control-label">
                        Dirección:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su dirección." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="direction_producer" />
                    </div>  
                </div>      
                <div class="control-group">
                    <label for="formProducer_depPro" class="control-label req">
                        Departamento:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccione un departamento." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:select
                            list="department_producer" 
                            listKey="idDep" 
                            listValue="nameDep" 
                            headerKey=" " 
                            headerValue="---"
                            onchange="chargeValues('/comboProducer.action', 'depId', this.value, 'formProducer_cityPro', 'divMessage')"
                            name="depPro"/>
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_cityPro" class="control-label req">
                        Municipio:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Seleccione un municipio." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:select
                            label="Municipio"
                            list="city_producer" 
                            listKey="idMun" 
                            listValue="nameMun" 
                            headerKey=" " 
                            headerValue="---"
                            name="cityPro" />
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_telephone_producer" class="control-label">
                        Teléfono fijo:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su teléfono fijo." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="telephone_producer" />
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_celphone_producer" class="control-label">
                        Celular:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su celular." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="celphone_producer" />
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_email_producer" class="control-label">
                        Correo electrónico:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="Ingrese su correo electrónico." data-title="Información" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield type="email" name="email_producer" />
                    </div>  
                </div>
            </fieldset>
            <p class="warnField reqBef">Campos Requeridos</p>
            <div>   
                <% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
                <script>
//                    var idPro  = $("#formProducer_idProducer").val();
//                    var action = $("#formProducer_actExe").val();
//                    var target = "divBodyLayout";
//                    if (action=="modify") {
//                        target = "trProducer"+idPro;
//                    }
                </script>
                <%--<s:hidden name="actExe" value=""/>--%>
                <s:hidden name="actExe"/>
                <s:hidden name="page"/>
                <s:hidden name="viewInfo"/>                
                <!--<input type="submit" class="btn btn-primary" value="Guardar productor" id="submit_492662557">-->
                <%--<sj:submit cssClass="btn btn-inverse" targets="divBodyLayout" onCompleteTopics="completeProducer" value="Guardar productor" validate="true" validateFunction="validationForm"/>--%>
                <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "producer/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify"))) { %>
                    <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formProducer'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeProducer" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar productor</sj:submit>
                <% } %>
                <!--<button class="btn btn-inverse" onclick="saveData('saveProducer.action','searchProducer.action?page=<%=pageNow%>','formProducer');">Guardar productor</button>-->
                <button class="btn btn-large bt_cancel_producer" onclick="resetForm('formProducer'); closeWindow();"><i class="icon-ban-circle"></i>  Cancelar</button>
            </div>
        </s:form>
        <script>
            var idPro  = $("#formProducer_idProducer").val();
            var action = $("#formProducer_actExe").val();
            var page   = $("#formProducer_page").val();
            var target = "divBodyLayout";
            if (action=="modify") {
                //target = "trProducer"+idPro;
            }
//            alert(target);
//            jQuery(document).ready(function () {
//                var options_submit_492662557 = {};
//                options_submit_492662557.jqueryaction = "button";
//                options_submit_492662557.id    = "submit_492662557";
//                options_submit_492662557.oncom = "completeProducer";
////                options_submit_492662557.targets = ""+target;
//                options_submit_492662557.href  = "#";
//                options_submit_492662557.formids = "formProducer";
//                options_submit_492662557.validateFunction = validationForm;
//                options_submit_492662557.validate = true;
//                jQuery.struts2_jquery.bind(jQuery('#submit_492662557'),options_submit_492662557);
//            }); 
//            var requestSent = false;
            $.mask.definitions['h'] = "[3]";
            $("#formProducer_dig_ver_producer").mask("9",{placeholder:""});
            $("#formProducer_telephone_producer").mask("9999999",{placeholder:""});
            $("#formProducer_celphone_producer").mask("h999999999",{placeholder:""});
            $.subscribe('completeProducer', function(event, data) {
                //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                //     '\n\nThe output div should have already been updated with the responseText.');
                //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                //        alert('responseText: \n' + json.info);
//                if(!requestSent) {
//                    requestSent = true;
                    completeFormGetting('dialog-form', 'formProducer', 'divProducers', event.originalEvent.request.responseText);
                    setTimeout( function() {
                        showInfo("searchProducer.action?page="+$("#formProducer_page").val(), "divConListProducers");
    //                        if(requestSent) $.ajax().abort();  // abort request
                    }, 2000);
//                }
            });
            if($('.pop-over').length) {
                $('.pop-over').popover();
            }
        </script>
    </body>
</html>