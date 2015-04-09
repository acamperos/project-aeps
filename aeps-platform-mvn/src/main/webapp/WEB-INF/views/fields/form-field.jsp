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
        <div class="row-fluid" id="divFieldsForm">
            <div class="span7">	
                <s:form id="formField" action="saveField.action" cssClass="form-horizontal formClassLot">
                    <fieldset>
                        <legend><s:property value="getText('title.formfield.field')" /></legend>
                        <s:hidden name="idProducer"/>                           
                        <s:hidden name="idField"/>
                        <div id="divPropertyLot">
                            <div class="control-group">
                                <s:hidden name="idFarm"/>
                                <label for="formField_name_property_lot" class="control-label req">
                                    <s:property value="getText('text.selfarm.field')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.useoptiontoselafarm.field')" />." data-title="<s:property value="getText('help.informationfarm.field')" />" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="name_property_lot" readonly="true" onclick="setPropertyGeneral('/viewFarm.action?selected=lot', 'idProducer', 'formField_idProducer', 'formField_name_property_lot', 'formField_idFarm', 'divListFieldsForm', 'divFieldsForm')" />
                                    <a class="btn" onclick="setPropertyGeneral('/viewFarm.action?selected=lot', 'idProducer', 'formField_idProducer', 'formField_name_property_lot', 'formField_idFarm', 'divListFieldsForm', 'divFieldsForm')"><i class="icon-search"></i></a>
                                </div>  
                            </div>                     
                        </div>
                        <div class="control-group">
                            <label for="formField_typeLot" class="control-label req">
                                <s:property value="getText('select.typefield.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.seltypefield.field')" />." data-title="<s:property value="getText('help.infotypefield.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <!--requiredLabel="true"-->
                                <s:select
                                    name="typeLot"                                    
                                    list="type_property_lot" 
                                    listKey="idFieTyp" 
                                    listValue="nameFieTyp"              
                                    headerKey="-1" 
                                    headerValue="---" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_name_lot" class="control-label req">
                                <s:property value="getText('text.namefield.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.putnamefield.field')" />." data-title="<s:property value="getText('help.putnamefield.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="name_lot" />
                            </div>    
                        </div>    
                        <div class="control-group">
                            <label for="formField_latitude_lot" class="control-label req">
                                <s:property value="getText('text.latitudedecimal.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedecimal.field')" />." data-title="<s:property value="getText('help.latitudedecimal.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="latitude_lot" onkeyup="generateDegrees('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_latitude_degrees_lot" class="control-label req">
                                <s:property value="getText('text.latitudedegree.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedegree.field')" />." data-title="<s:property value="getText('help.latitudedegree.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on"><s:property value="getText('text.degrees.field')" /></span>
                                        <input type="text" name="latitude_degrees_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot')" id="formField_latitude_degrees_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on"><s:property value="getText('text.minutes.field')" /></span>
                                        <input type="text" name="latitude_minutes_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot'); checkValue('formField_latitude_minutes_lot', 59);" id="formField_latitude_minutes_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on"><s:property value="getText('text.seconds.field')" /></span>
                                        <input type="text" name="latitude_seconds_lot" onkeyup="generateDecimals('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot'); checkValueSecond('formField_latitude_seconds_lot', 60);" id="formField_latitude_seconds_lot" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>                            
                        <div class="control-group">
                            <label for="formField_length_lot" class="control-label req">
                                <s:property value="getText('text.longitudedecimal.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedecimal.field')" />." data-title="<s:property value="getText('help.longitudedecimal.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" id="formField_length_lot" name="length_lot" onkeyup="generateDegrees('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_length_degrees_lot" class="control-label req">
                                <s:property value="getText('text.longitudedegree.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedegree.field')" />." data-title="<s:property value="getText('help.longitudedegree.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on"><s:property value="getText('text.degrees.field')" /></span>
                                        <input type="text" name="length_degrees_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot')" id="formField_length_degrees_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on"><s:property value="getText('text.minutes.field')" /></span>
                                        <input type="text" name="length_minutes_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot'); checkValue('formField_length_minutes_lot', 59);" id="formField_length_minutes_lot" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on"><s:property value="getText('text.seconds.field')" /></span>
                                        <input type="text" name="length_seconds_lot" onkeyup="generateDecimals('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot'); checkValueSecond('formField_length_seconds_lot', 60);" id="formField_length_seconds_lot" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>    
                        <div class="control-group">
                            <div class="controls">
                                <button type="button" class="btn btn-initial btn-space" onclick="viewPosition('/viewPositionField.action', 'formField', 'latitude_lot', 'formField_latitude_lot', 'length_lot', 'formField_length_lot', 'divFieldsForm', 'divListFieldsForm');">
                                    <i class="icon-map-marker" style="font-size: 18px"></i> <s:property value="getText('button.showgeolocation.field')" />
                                </button>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_altitude_lot" class="control-label req">
                                <s:property value="getText('text.altitudemeters.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.altitudemeters.field')" />." data-title="<s:property value="getText('help.altitudemeters.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="altitude_lot" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formField_area_lot" class="control-label">
                                <s:property value="getText('text.areafield.field')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.areafield.field')" />." data-title="<s:property value="getText('help.areafield.field')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="area_lot" requiredLabel="true" />
                            </div>         
                        </div>         
                    </fieldset>		
                    <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                    <div> 
                        <s:hidden name="page" id="formField_page"/>
                        <s:hidden name="actExe"/>
                        <s:hidden name="viewInfo"/>
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "field/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "field/modify"))) { %>
                            <sj:submit id="btnField" type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formField'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeField" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.savefield.field')" /></sj:submit>
                        <% } %>
                        <button class="btn btn-large bt_cancel_field" onclick="resetForm('formField'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                    </div>    
                </s:form>        
                <script>
                        var viewInfo = $("#formField_viewInfo").val();
                        $.mask.definitions['i'] = "[-0-9]";
                        $.mask.definitions['f'] = "[-.0-9]";
                        /*$("#params_latitude_lot").mask("f?9fffffff",{placeholder:" "});
                         $("#params_length_lot").mask("f9?fffffff",{placeholder:" "});
                         $("#params_altitude_lot").mask("9?999",{placeholder:" "});
                         $("#params_area_lot").mask("9?999",{placeholder:" "});*/

                        $("#formField_latitude_lot").numeric();
                        $("#formField_length_lot").numeric();
                        $("#formField_latitude_lot").val(parsePointSeparated($("#formField_latitude_lot").val()));
                        $("#formField_length_lot").val(parsePointSeparated($("#formField_length_lot").val()));
                        
                        $("#formField_altitude_lot").numeric({decimal: false, negative: false});
                        $("#formField_area_lot").numeric();

                        $("#formField_length_degrees_lot").numeric({decimal: false});
                        $("#formField_length_minutes_lot").numeric({decimal: false});
                        $("#formField_length_seconds_lot").numeric();
                        $("#formField_latitude_degrees_lot").numeric({decimal: false});
                        $("#formField_latitude_minutes_lot").numeric({decimal: false});
                        $("#formField_latitude_seconds_lot").numeric();
                        generateDegrees('formField_latitude_lot', 'formField_latitude_degrees_lot', 'formField_latitude_minutes_lot', 'formField_latitude_seconds_lot');
                        generateDegrees('formField_length_lot', 'formField_length_degrees_lot', 'formField_length_minutes_lot', 'formField_length_seconds_lot');
                        $.subscribe('completeField', function(event, data) {
                            var actExeFie = $("#formField_actExe").val();
                            if (actExeFie=='create') {
                                $('#btnField').on('click', function() {
                                    ga('send', 'event', 'Fields', 'click', 'Create');
                                });
                            } else if (actExeFie=='modify') {
                                $('#btnField').on('click', function() {
                                    ga('send', 'event', 'Fields', 'click', 'Update');
                                });                
                            }
                            completeFormGetting('dialog-form', 'formField', 'divFields', event.originalEvent.request.responseText);
                            setTimeout( function() {
                                showInfo("searchField.action?page="+$("#formField_page").val(), "divConListFields");
                            }, 2000);
                        });
                        if($('.pop-over').length) {
                            $('.pop-over').popover();
                        }
                                //            chargeValues('/templates/listarTiposFields.action', 'proId', '', 'formField_type_property_lot', 'divMessage');
                </script>
            </div>
            <div class="span5" style="margin-left: 0">
                <div class="alert fade in">
                    <s:text name="%{getText('area.aboutgeolocation')}" />
                </div>
            </div>
        </div>
        <div class="row-fluid" id="divListFieldsForm"></div>
    </body>
</html>