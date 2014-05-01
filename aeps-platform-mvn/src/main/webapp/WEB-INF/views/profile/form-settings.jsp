<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<fieldset>
    <legend>Informacion del Usuario</legend>
    <s:form id="formSetting" action="saveSetting.action">
        <s:hidden name="actExe" value="configSetting"/>
<!--        <div class="control-group">
            <label for="formSetting_emailUser" class="control-label">Correo electrónico:</label>
            <div class="controls">
                <%--<s:textfield cssClass="form-control" id="formSetting_emailUser" name="emailUser"/>--%>
            </div>
        </div>-->
<!--        <div class="control-group">
            <label for="formSetting_celphoneUser" class="control-label">Celular:</label>
            <div class="controls">
                <%--<s:textfield cssClass="form-control" id="formSetting_celphoneUser" name="celphoneUser"/>--%>
            </div>
        </div>-->
<!--        <div class="control-group">
            <label for="formSetting_noRecords" class="control-label">Numero de registros a paginar:</label>
            <div class="controls">
                <%--<s:select            
                    name="noRecords" 
                    list="new int[]{5, 10, 15, 20, 25, 30}" 
                    headerKey=" " 
                    headerValue="---"
                    />--%>
            </div>
        </div>                                -->
        <div class="row">
            <div class="span6">
                <button class="btn btn-default" style="margin-bottom: 10px" onclick="showInfoPassword('divPassword', 'formSetting_changePass')"><i class="icon-key"></i> Cambiar Contraseña</button>
            </div>  
        </div>  
        <div id="divPassword" style="display: none" class="panel arrow_box">
            <div class="row-fluid">
                <div class="span5 panel-body" style="width: 28.3%;">
                    <s:hidden id="formSetting_changePass" name="changePass" value="false"/>
                    <div class="control-group">
                        <label for="formSetting_passActual" class="control-label req">Contraseña actual:</label>
                        <div class="controls">
                            <s:password cssClass="form-control" id="formSetting_passActual" name="passActual"/>
                        </div>                            
                    </div>
                    <div class="control-group">
                        <label for="formSetting_newPass" class="control-label req">Nueva contraseña:</label>
                        <div class="controls">
                            <s:password cssClass="form-control" id="formSetting_newPass" name="newPass"/>
                            <div id="pwdMeter" class="progress progress-danger">
                                <div class="bar" style="width: 0%"></div>
                                <span class="pwdText"></span>
                            </div>
                        </div>
                        <script>
                            if($('#formSetting_newPass').length) {
                                $('#formSetting_newPass').pwdMeter({
                                    minLength: 6,
                                    displayGeneratePassword: false,
                                    neutralText:"",
                                    veryWeakText:"Muy Debil",
                                    weakText:"Debil",
                                    mediumText:"Normal",
                                    strongText:"Fuerte",
                                    veryStrongText:"Muy Fuerte"
                                });  
                            }
                        </script>                       
                    </div>
                    <div class="control-group">
                        <label for="formSetting_confirmNewPass" class="control-label req">Confirmar contraseña:</label>
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
                <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeSetting" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  Guardar Configuración</sj:submit>
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