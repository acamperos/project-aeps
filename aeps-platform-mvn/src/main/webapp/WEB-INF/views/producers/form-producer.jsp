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
                <legend><s:property value="getText('title.formprod.producer')" /></legend>
                <div class="control-group">
                    <s:hidden name="idProducer"/>
                    <label for="formProducer_typeIdent" class="control-label req">
                        <s:property value="getText('text.typedoc.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.typedoc.producer')" />." data-title="<s:property value="getText('help.typedoc.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                        <s:property value="getText('text.docnumber.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.docnumber.producer')" />." data-title="<s:property value="getText('help.docnumber.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                            <s:property value="getText('text.digverify.producer')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.digverify.producer')" />." data-title="<s:property value="getText('help.digverify.producer')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield name="dig_ver_producer" />
                        </div>  
                    </div>
                    <div class="control-group">
                        <label for="formProducer_nameCompany" class="control-label req">
                            <s:property value="getText('text.namecompany.producer')" />:
                        </label>
                        <div class="controls">
                            <s:textfield name="nameCompany"/>
                        </div>                         
                    </div> 
                    <h4><s:property value="getText('title.infoagent.producer')" />:</h4>   
                    <hr />
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_firstNameRep" class="control-label req"><s:property value="getText('text.firstnameagent.producer')" />:</label>
                                <div class="controls">
                                    <s:textfield name="firstNameRep"/>
                                </div>
                            </div>
                        </div>
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_secondNameRep" class="control-label"><s:property value="getText('text.secondnameagent.producer')" />:</label>
                                <div class="controls">
                                    <s:textfield name="secondNameRep"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_firstLastNameRep" class="control-label req"><s:property value="getText('text.firstlastnameagent.producer')" />:</label>
                                <div class="controls">
                                    <s:textfield name="firstLastNameRep"/>
                                </div>
                            </div>
                        </div>
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_secondLastNameRep" class="control-label"><s:property value="getText('text.secondlastnameagent.producer')" />:</label>
                                <div class="controls">
                                    <s:textfield name="secondLastNameRep"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="formProducer_emailRes" class="control-label req">
                            <s:property value="getText('text.emailagent.producer')" />:
                            <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.emailagent.producer')" />." data-title="<s:property value="getText('help.emailagent.producer')" />" data-placement="right" data-trigger="hover"></i>
                        </label>
                        <div class="controls">
                            <s:textfield type="email" name="emailRes" />
                        </div>  
                    </div>
                    <h4><s:property value="getText('title.infocompany.producer')" />:</h4>   
                    <hr />        
                </div>   
                <div class="<%= classInfoPerson %>" id="divInfoPerson">      
                    <div class="row-fluid">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formProducer_names_producer_1" class="control-label req">
                                    <s:property value="getText('text.firstnamepro.producer')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.firstnamepro.producer')" />." data-title="<s:property value="getText('help.firstnamepro.producer')" />" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="names_producer_1" />
                                </div>  
                            </div>
                        </div>  
                        <div class="span3" style="padding-left: 28px">
                            <div class="control-group">
                                <label for="formProducer_names_producer_2" class="control-label">
                                    <s:property value="getText('text.secondnamepro.producer')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.secondnamepro.producer')" />." data-title="<s:property value="getText('help.secondnamepro.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                                    <s:property value="getText('text.firstlastnamepro.producer')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.firstlastnamepro.producer')" />." data-title="<s:property value="getText('help.firstlastnamepro.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                                        <s:property value="getText('text.secondlastnamepro.producer')" />:
                                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.secondlastnamepro.producer')" />." data-title="<s:property value="getText('help.secondlastnamepro.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                        <s:property value="getText('text.direction.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.direction.producer')" />." data-title="<s:property value="getText('help.direction.producer')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="direction_producer" />
                    </div>  
                </div>      
                <div class="control-group">
                    <label for="formProducer_depPro" class="control-label req">
                        <s:property value="getText('select.department.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.department.producer')" />." data-title="<s:property value="getText('help.department.producer')" />" data-placement="right" data-trigger="hover"></i>
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
                        <s:property value="getText('select.municipality.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.municipality.producer')" />." data-title="<s:property value="getText('help.municipality.producer')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:select
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
                        <s:property value="getText('text.telephone.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.telephone.producer')" />." data-title="<s:property value="getText('help.telephone.producer')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="telephone_producer" />
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_celphone_producer" class="control-label">
                        <s:property value="getText('text.celphone.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.celphone.producer')" />." data-title="<s:property value="getText('help.celphone.producer')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield name="celphone_producer" />
                    </div>  
                </div>
                <div class="control-group">
                    <label for="formProducer_email_producer" class="control-label">
                        <s:property value="getText('text.emailpro.producer')" />:
                        <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.emailpro.producer')" />." data-title="<s:property value="getText('help.emailpro.producer')" />" data-placement="right" data-trigger="hover"></i>
                    </label>
                    <div class="controls">
                        <s:textfield type="email" name="email_producer" />
                    </div>  
                </div>
            </fieldset>
            <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
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
                <s:hidden name="actExe"/>
                <s:hidden name="page"/>
                <s:hidden name="viewInfo"/>                
                <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "producer/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify"))) { %>
                    <sj:submit id="btnProducer" type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formProducer'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeProducer" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saveproducer.producer')" /></sj:submit>
                <% } %>
                <button class="btn btn-large bt_cancel_producer" onclick="resetForm('formProducer'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
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
                var actExePro = $("#formProducer_actExe").val();
                if (actExePro=='create') {
                    $('#btnProducer').on('click', function() {
                        ga('send', 'event', 'Producers', 'click', 'Create');
                    });
                } else if (actExePro=='modify') {
                    $('#btnProducer').on('click', function() {
                        ga('send', 'event', 'Producers', 'click', 'Update');
                    });                
                }
                //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                //     '\n\nThe output div should have already been updated with the responseText.');
                //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                //        alert('responseText: \n' + json.info);
//                if(!requestSent) {
//                    requestSent = true;
                    completeFormGetting('dialog-form', 'formProducer', 'divProducers', event.originalEvent.request.responseText);
                    setTimeout( function() {
                        showInfo("searchProducer.action?page="+$("#formProducer_page").val(), "divConListProducers");
    //                        if(requestSent) $.ajax().abort();
                    }, 2000);
//                }
            });
            if($('.pop-over').length) {
                $('.pop-over').popover();
            }
        </script>
    </body>
</html>