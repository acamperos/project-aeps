<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<fieldset>
    <legend><s:property value="getText('title.infouser.setting')" /></legend>
    <s:form id="formSetting" action="saveSetting.action">
        <s:hidden name="actExe" value="configSetting"/>
        <div class="row">
            <div class="span6">
                <button class="btn btn-default" style="margin-bottom: 10px" onclick="showInfoPassword('divPassword', 'formSetting_changePass')"><i class="icon-key"></i> <s:property value="getText('button.changepass.setting')" /></button>
            </div>  
        </div>  
        <div id="divPassword" style="display: none" class="panel arrow_box">
            <div class="row-fluid">
                <div class="span5 panel-body" style="width: 28.3%;">
                    <s:hidden id="formSetting_changePass" name="changePass" value="false"/>
                    <div class="control-group">
                        <label for="formSetting_passActual" class="control-label req"><s:property value="getText('text.currentpass.setting')" />:</label>
                        <div class="controls">
                            <s:password cssClass="form-control" id="formSetting_passActual" name="passActual"/>
                        </div>                            
                    </div>
                    <div class="control-group">
                        <label for="formSetting_newPass" class="control-label req"><s:property value="getText('text.newpass.setting')" />:</label>
                        <div class="controls">
                            <s:password cssClass="form-control" id="formSetting_newPass" name="newPass"/>
                            <div id="pwdMeter" class="progress progress-danger">
                                <div class="bar" style="width: 0%"></div>
                                <span class="pwdText"></span>
                            </div>
                        </div>
                        <script>
														var veryWeakLogin = "";
														var weakLogin = "";
														var mediumLogin = "";
														var strongLogin = "";
														var veryStrongLogin = "";
														if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
															veryWeakLogin = "Muy Debil";
															weakLogin = "Debil";
															mediumLogin = "Normal";
															strongLogin = "Fuerte";
															veryStrongLogin = "Muy Fuerte";
														}
														
														if(navigator.language=='en-EN' || navigator.language=='en') {
															veryWeakLogin = "Very Weak";
															weakLogin = "Weak";
															mediumLogin = "Normal";
															strongLogin = "Strong";
															veryStrongLogin = "Very Strong";
														}
												
												
                            if($('#formSetting_newPass').length) {
                                $('#formSetting_newPass').pwdMeter({
                                    minLength: 6,
                                    displayGeneratePassword: false,
                                    neutralText:"",
                                    veryWeakText:veryWeakLogin,
                                    weakText:weakLogin,
                                    mediumText:mediumLogin,
                                    strongText:strongLogin,
                                    veryStrongText:veryStrongLogin
                                });  
                            }
                        </script>                       
                    </div>
                    <div class="control-group">
                        <label for="formSetting_confirmNewPass" class="control-label req"><s:property value="getText('text.confirmpass.setting')" />:</label>
                        <div class="controls">
                            <s:password cssClass="form-control" id="formSetting_confirmNewPass" name="confirmNewPass"/>
                        </div>                            
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <% //if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify")) { %>
            <% if (true) {%>
							<div class="span6">
									<sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeSetting" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.savesetting.setting')" /></sj:submit>
							</div>  
            <% }%>
        </div>  
        <script>
            $.subscribe('completeSetting', function(event, data) {
                completeFormCrop('', 'formSetting', 'divMessProfile', event.originalEvent.request.responseText);
            });
        </script>
    </s:form>
</fieldset>