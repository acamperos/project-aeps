<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users user = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>
<html>
    <head></head>
    <body>
        <s:actionerror theme="bootstrap"/>
        <s:actionmessage theme="bootstrap"/>
        <s:fielderror theme="bootstrap"/>
        <div class="row-fluid" id="divFarmsForm">
            <div class="span7">	
                <s:form id="formFarm" action="saveFarm.action" cssClass="form-horizontal formClassProperty">
                    <fieldset>
                        <legend><s:property value="getText('title.formfarm.farm')" /></legend>
                        <s:hidden name="typeEnt"/>
                        <s:if test="%{typeEnt==2}">
                            <s:hidden name="idProducer"/>                                
                            <s:hidden name="name_producer"/>
                        </s:if>
                        <s:else>
                            <div class="control-group">
                                <s:hidden name="idProducer"/>                                
                                <label for="formFarm_name_producer" class="control-label req">
                                    <s:property value="getText('text.selproducer.farm')" />:
                                    <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.useoptiontoselaproducer.farm')" />" data-title="<s:property value="getText('help.informationproducer.farm')" />" data-placement="right" data-trigger="hover"></i>
                                </label>
                                <div class="controls">
                                    <s:textfield name="name_producer" readonly="true" onclick="listInfo('/viewProducer.action?selected=property', 'formFarm_name_producer', 'formFarm_idProducer', 'divListFarmsForm', 'divFarmsForm')" />
                                    <a class="btn" onclick="listInfo('/viewProducer.action?selected=property', 'formFarm_name_producer', 'formFarm_idProducer', 'divListFarmsForm', 'divFarmsForm')"><i class="icon-search"></i></a>
                                </div>  
                            </div>   
                        </s:else>                        
                        <div class="control-group">
                            <s:hidden name="idFarm"/>
                            <label for="formFarm_name_property" class="control-label req">
                                <s:property value="getText('text.namefarm.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.writenamefarm.farm')" />." data-title="<s:property value="getText('help.informationfarm.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="name_property" />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_latitude_property" class="control-label req">
                                <s:property value="getText('text.latitudedecimal.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedecimal.farm')" />." data-title="<s:property value="getText('help.latitudeinfodecimals.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" name="latitude_property" onkeyup="generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_latitude_degrees_property" class="control-label req">
                                <s:property value="getText('text.latitudedegree.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.latitudedegree.farm')" />." data-title="<s:property value="getText('help.latitudeinfodegrees.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on"><s:property value="getText('text.degrees.farm')" /></span>
                                        <input type="text" name="latitude_degrees_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');" id="formFarm_latitude_degrees_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on"><s:property value="getText('text.minutes.farm')" /></span>
                                        <input type="text" name="latitude_minutes_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property'); checkValue('formFarm_latitude_minutes_property', 59);" id="formFarm_latitude_minutes_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on"><s:property value="getText('text.seconds.farm')" /></span>
                                        <input type="text" name="latitude_seconds_property" onkeyup="generateDecimals('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property'); checkValueSecond('formFarm_latitude_seconds_property', 60);" id="formFarm_latitude_seconds_property" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>                            
                        <div class="control-group">
                            <label for="formFarm_length_property" class="control-label req">
                                <s:property value="getText('text.longitudedecimal.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedecimal.farm')" />." data-title="<s:property value="getText('help.longitudedecimal.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield cssClass="form-control" id="formFarm_length_property" name="length_property" onkeyup="generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property')"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_length_degrees_property" class="control-label req">
                                <s:property value="getText('text.longitudedegree.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.longitudedegree.farm')" />." data-title="<s:property value="getText('help.longitudeinfodegrees.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <div class="row-fluid">
                                    <div class="span2 input-prepend controls" style="width: 100px;">
                                        <span class="add-on"><s:property value="getText('text.degrees.farm')" /></span>
                                        <input type="text" name="length_degrees_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property');" id="formFarm_length_degrees_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 2%">
                                        <span class="add-on"><s:property value="getText('text.minutes.farm')" /></span>
                                        <input type="text" name="length_minutes_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); checkValue('formFarm_length_minutes_property', 59);" id="formFarm_length_minutes_property" class="input-degrees"/>
                                    </div>
                                    <div class="span2 input-prepend controls" style="width: 100px; margin-left: 3%">
                                        <span class="add-on"><s:property value="getText('text.seconds.farm')" /></span>
                                        <input type="text" name="length_seconds_property" onkeyup="generateDecimals('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property'); checkValueSecond('formFarm_length_seconds_property', 60);" id="formFarm_length_seconds_property" class="input-degrees"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">   
                            <div class="controls">
                                <button type="button" class="btn btn-initial btn-space" onclick="viewPosition('/viewPositionFarm.action', 'formFarm', 'latitude_property', 'formFarm_latitude_property', 'length_property', 'formFarm_length_property', 'divFarmsForm', 'divListFarmsForm');">
                                    <i class="icon-map-marker" style="font-size: 18px"></i> <s:property value="getText('button.showgeolocation.farm')" />
                                </button>                                          
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="formFarm_altitude_property" class="control-label req">
                                <s:property value="getText('text.altitudemeters.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.altitudemeters.farm')" />." data-title="<s:property value="getText('help.altitudemeters.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="altitude_property" />
                            </div>  
                        </div>                         
                        <div class="control-group">
                            <label for="formFarm_depFar" class="control-label req">
                                <s:property value="getText('select.department.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.department.farm')" />." data-title="<s:property value="getText('help.departmentinfo.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:select
                                    name="depFar" 
                                    list="department_property" 
                                    listKey="idDep" 
                                    listValue="nameDep"          
                                    headerKey=" " 
                                    headerValue="---"
                                    onchange="chargeValues('/comboMunicipalities.action', 'depId', this.value, 'formFarm_cityFar', 'divMessage')"
                                />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_cityFar" class="control-label req">
                                <s:property value="getText('select.municipality.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.municipality.farm')" />." data-title="<s:property value="getText('help.municipalityinfo.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:select
                                    list="city_property" 
                                    listKey="idMun" 
                                    listValue="nameMun" 
                                    headerKey=" " 
                                    headerValue="---"
                                    name="cityFar" 
                                />
                            </div>  
                        </div>
                        <div class="control-group">
                            <label for="formFarm_lane_property" class="control-label req">
                                <s:property value="getText('text.lane.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.lane.farm')" />." data-title="<s:property value="getText('help.laneinfo.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="lane_property" />
                            </div>  
                        </div> 
                        <div class="control-group">
                            <label for="formFarm_direction_property" class="control-label">
                                <s:property value="getText('text.direction.farm')" />:
                                <i class="icon-info-sign s2b_tooltip pop-over" data-content="<s:property value="getText('desc.direction.farm')" />." data-title="<s:property value="getText('help.directioninfo.farm')" />" data-placement="right" data-trigger="hover"></i>
                            </label>
                            <div class="controls">
                                <s:textfield name="direction_property" />
                            </div>  
                        </div>   
                    </fieldset>
                    <p class="warnField reqBef"><s:property value="getText('label.requirefields')" /></p>
                    <div> 
                        <s:hidden name="page" id="formFarm_page" />
                        <s:hidden name="actExe"/>
                        <s:hidden name="viewInfo"/>
                        <% String actExe   = String.valueOf(request.getAttribute("actExe")); %>
                        <% if ((actExe.equals("create") && usrDao.getPrivilegeUser(user.getIdUsr(), "farm/create")) || (actExe.equals("modify") && usrDao.getPrivilegeUser(user.getIdUsr(), "farm/modify"))) { %>
                            <sj:submit id="btnFarm" type="button" cssClass="btn btn-initial btn-large" onclick="searchDecimalNumber('formFarm'); addMessageProcess()" targets="divMessage" onCompleteTopics="completeFarm" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.savefarm.farm')" /></sj:submit>
                        <% } %>
                        <button class="btn btn-large bt_cancel_farm" onclick="resetForm('formFarm'); closeWindow();"><i class="icon-ban-circle"></i>  <s:property value="getText('button.cancel')" /></button>
                    </div>    
                </s:form>        
                <script>
//                    var page   = $("#formFarm_page").val();                    
                    // $.mask.definitions['i'] = "[-0-9]";
                    $.mask.definitions['f'] = "[-.0-9]";
                    $("#formFarm_latitude_property").numeric();
                    $("#formFarm_length_property").numeric();
                    $("#formFarm_latitude_property").val(parsePointSeparated($("#formFarm_latitude_property").val()));
                    $("#formFarm_length_property").val(parsePointSeparated($("#formFarm_length_property").val()));           
                    
                    $("#formFarm_altitude_property").numeric({decimal: false, negative: false});
                    $("#formFarm_length_degrees_property").numeric({decimal: false});
                    $("#formFarm_length_minutes_property").numeric({decimal: false});
                    $("#formFarm_length_seconds_property").numeric();
                    if ($("#formFarm_length_minutes_property").val()>60) {                        
                        $("#formFarm_length_minutes_property").val('');
                    }
                    if ($("#formFarm_length_seconds_property").val()>60) {                        
                        $("#formFarm_length_seconds_property").val('');
                    }
                    
                    $("#formFarm_altitude_property").val(parseValueInt( $("#formFarm_altitude_property").val()));
                    
                    $("#formFarm_latitude_degrees_property").numeric({decimal: false});
                    $("#formFarm_latitude_minutes_property").numeric({decimal: false});
                    $("#formFarm_latitude_seconds_property").numeric();
                    if ($("#formFarm_latitude_minutes_property").val()>60) {                        
                        $("#formFarm_latitude_minutes_property").val('');
                    }
                    if ($("#formFarm_latitude_seconds_property").val()>60) {                        
                        $("#formFarm_latitude_seconds_property").val('');
                    }
                    generateDegrees('formFarm_latitude_property', 'formFarm_latitude_degrees_property', 'formFarm_latitude_minutes_property', 'formFarm_latitude_seconds_property');
                    generateDegrees('formFarm_length_property', 'formFarm_length_degrees_property', 'formFarm_length_minutes_property', 'formFarm_length_seconds_property');
                    $.subscribe('completeFarm', function(event, data) {
                        var actExeFarm = $("#formFarm_actExe").val();
                        if (actExeFarm=='create') {
                            $('#btnFarm').on('click', function() {
                                ga('send', 'event', 'Farms', 'click', 'Create');
                            });
                        } else if (actExeFarm=='modify') {
                            $('#btnFarm').on('click', function() {
                                ga('send', 'event', 'Farms', 'click', 'Update');
                            });                
                        }
                        //   	 alert('status: ' + event.originalEvent.status + '\n\nresponseText: \n' + event.originalEvent.request.responseText + 
                        //     '\n\nThe output div should have already been updated with the responseText.');
                        //        var json = jQuery.parseJSON(event.originalEvent.request.responseText);
                        //        alert('responseText: \n' + json.info);
//                        completeForm('dialog-form', 'formFarm', event.originalEvent.request.responseText);
                        completeFormGetting('dialog-form', 'formFarm', 'divFarms', event.originalEvent.request.responseText);
                        setTimeout( function() {
                            showInfo("/viewFarm.action?page="+$("#formFarm_page").val(), "divViewFarm");
                        }, 2000);
                    });
                    if($('.pop-over').length) {
                        $('.pop-over').popover();
                    }
                </script>
            </div>
            <div class="span5" style="margin-left: 0">
                <div class="alert fade in">
                    <s:text name="%{getText('area.aboutgeolocation')}" />		
                </div>
            </div>
        </div>
        <div id="divListFarmsForm"></div>
    </body>
</html>