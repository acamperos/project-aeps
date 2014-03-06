<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<!--<html>
    <head>-->
<!--    </head>
    <body>-->
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="bootstrap"/>
<s:fielderror theme="bootstrap"/>
<div class="container">
    <!--<div class="w-box">-->
    <!--                <div class="w-box-content">
                    </div>-->
    <div class="row">
        <div class="span12 tabbable-bordered content">
            <div class="tabbable-bordered">
                <div class="tab-content">
                    <!--<div class="w-box-content cnt_b">-->
                    <s:form id="formProfileUser" action="sendInformation.action" method="post">
                        <s:hidden name="actExe" value="profileUser"/>
                        <fieldset>
                            <legend>Informacion del Usuario</legend>
                            <div class="control-group">
                                <s:label for="formProfileUser_emailUser" cssClass="control-label" value="Correo electrónico:"></s:label>
                                    <div class="controls">
                                    <s:textfield cssClass="form-control" id="formProfileUser_emailUser" name="emailUser"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <s:label for="formProfileUser_celphoneUser" cssClass="control-label" value="Celular:"></s:label>
                                    <div class="controls">
                                    <s:textfield cssClass="form-control" id="formProfileUser_celphoneUser" name="celphoneUser"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <s:label for="formProfileUser_noRecords" cssClass="control-label" value="Numero de registros a paginar:"></s:label>
                                    <div class="controls">
                                    <s:select            
                                        name="noRecords" 
                                        list="new int[]{5, 10, 15, 20, 25, 30}" 
                                        headerKey=" " 
                                        headerValue="---"
                                        />
                                </div>
                            </div>                                
                            <div class="row-fluid">
                                <div class="span6">
                                    <button class="btn btn-default" style="margin-bottom: 10px" onclick="showInfoPassword('divPassword', 'formProfileUser_changePass')">Cambiar contrasena</button>
                                </div>  
                            </div>  
                            <div id="divPassword" style="display: none">
                                <s:hidden id="formProfileUser_changePass" name="changePass" value="false"/>
                                <div class="control-group">
                                    <s:label for="formProfileUser_passActual" cssClass="control-label req" value="Contraseña actual:"></s:label>
                                        <div class="controls">
                                        <s:password cssClass="form-control" id="formProfileUser_passActual" name="passActual"/>
                                    </div>                            
                                </div>
                                <div class="control-group">
                                    <s:label for="formProfileUser_newPass" cssClass="control-label req" value="Nueva contraseña:"></s:label>
                                        <div class="controls">
                                        <s:password cssClass="form-control" id="formProfileUser_newPass" name="newPass"/>
                                    </div>                            
                                </div>
                                <div class="control-group">
                                    <s:label for="formProfileUser_confirmNewPass" cssClass="control-label req" value="Confirmar contraseña:"></s:label>
                                        <div class="controls">
                                        <s:password cssClass="form-control" id="formProfileUser_confirmNewPass" name="confirmNewPass"/>
                                    </div>                            
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span6">
                                    <sj:submit cssClass="btn btn-success" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeProfile" value="Guardar" validate="true" validateFunction="validationForm"/>
                                </div>  
                            </div>  
                            <script>
                                        $.subscribe('completeProfile', function(event, data) {
                                            completeFormChange('', 'formProfileUser', event.originalEvent.request.responseText);
                                        });
                            </script>
                        </fieldset>
                    </s:form>
                    <s:form id="formProfile" action="sendInformation.action" method="post">
                        <s:hidden name="actExe" value="profileBasic"/>
                        <fieldset>
                            <legend>Informacion Basica</legend>
                            <div class="control-group">
                                <s:label for="formProfile_typeDocument" cssClass="control-label" value="Tipo de documento:"></s:label>
                                    <div class="controls">
                                    <s:select            
                                        name="typeDocument" 
                                        list="type_ident_producer" 
                                        listKey="acronymDocTyp" 
                                        listValue="nameDocTyp" 
                                        headerKey=" " 
                                        headerValue="---"
                                        onchange="selValue(this, 'divDigVerPro');
                                        selConf(this.value, 'formProducer_num_ident_producer');"
                                        />
                                </div>
                            </div>
                            <div class="control-group">
                                <s:label for="formProfile_noDocument" cssClass="control-label" value="Numero de documento:"></s:label>
                                    <div class="controls">
                                    <s:textfield cssClass="form-control" id="formProfile_noDocument" name="noDocument"/>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span4 control-group">
                                    <s:label for="formProfile_firstName" cssClass="control-label" value="Primer Nombre:"></s:label>
                                        <div class="controls">
                                        <s:textfield cssClass="form-control" id="formProfile_firstName" name="firstName"/>
                                    </div>
                                </div>
                                <div class="span6 control-group">
                                    <s:label for="formProfile_secondName" cssClass="control-label" value="Segundo nombre:"></s:label>
                                        <div class="controls">
                                        <s:textfield cssClass="form-control" id="formProfile_secondName" name="secondName"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span4 control-group">
                                    <s:label for="formProfile_firstLastName" cssClass="control-label" value="Primer apellido:"></s:label>
                                        <div class="controls">
                                        <s:textfield cssClass="form-control" id="formProfile_firstLastName" name="firstLastName"/>
                                    </div>
                                </div>
                                <div class="span6 control-group">
                                    <s:label for="formProfile_secondLastName" cssClass="control-label" value="Segundo apellido:"></s:label>
                                        <div class="controls">
                                        <s:textfield cssClass="form-control" id="formProfile_secondLastName" name="secondLastName"/>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <s:label for="formProfile_department" cssClass="control-label" value="Departamento:"></s:label>
                                    <div class="controls">
                                    <s:select
                                        list="department_producer" 
                                        listKey="idDep" 
                                        listValue="nameDep" 
                                        headerKey=" " 
                                        headerValue="---"
                                        onchange="chargeValues('/aeps-plataforma-mvn/comboProducer.action', 'depId', this.value, 'formProfile_municipality', 'divMessage')"
                                        name="department"
                                        />
                                </div>
                            </div>	
                            <div class="control-group">
                                <s:label for="formProfile_municipality" cssClass="control-label" value="Municipio:"></s:label>
                                    <div class="controls">
                                    <s:select
                                        list="city_producer" 
                                        listKey="idMun" 
                                        listValue="nameMun" 
                                        headerKey=" " 
                                        headerValue="---"
                                        name="municipality" 
                                        />
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span6">
                                    <sj:submit cssClass="btn btn-success" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeProfile" value="Guardar" validate="true" validateFunction="validationForm"/>
                                </div>  
                            </div>  
                            <script>
                                $.subscribe('completeProfile', function(event, data) {
                                    completeFormChange('', 'formProfile', event.originalEvent.request.responseText);
                                });
                            </script>
                        </fieldset>
                    </s:form>
                </div>
            </div>
        </div>
    </div>
    <!--</div>-->
</div>
<!--    </body>
</html>-->