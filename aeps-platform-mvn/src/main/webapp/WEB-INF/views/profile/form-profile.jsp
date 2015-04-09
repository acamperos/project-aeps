<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<fieldset>
    <legend><s:property value="getText('title.infoprofile.profile')" /></legend>    
    <s:form id="formProfile" action="saveProfile.action">
        <s:hidden name="actExe" value="configProfile"/>
        <div class="control-group">
            <label for="formProfile_typeDocument" class="control-label"><s:property value="getText('select.typedoc.profile')" />:</label>
            <div class="controls">
                <s:select            
                    name="typeDocument" 
                    list="type_ident_producer" 
                    listKey="acronymDocTyp" 
                    listValue="nameDocTyp" 
                    headerKey="-1" 
                    headerValue="---"
                    onchange="selConf(this.value, 'formProfile_noDocument');
                              showOtherTypeDocument(this.value, 'divInfoCompany', 'divInfoPerson');
                             "
                />
            </div>
        </div>
        <div class="control-group">
            <label for="formProfile_noDocument" class="control-label"><s:property value="getText('text.docnumber.profile')" />:</label>
            <div class="controls">
                <s:textfield cssClass="form-control" name="noDocument"/>
            </div>
        </div>
        <% String classInfoPerson  = "hide"; %>
        <% String classInfoCompany = "hide"; %>
        <s:set name="typeDoc" value="typeDocument"/>
        <s:if test="%{#typeDoc.equals('NIT')}">
            <% classInfoCompany = "";%>
        </s:if>      
        <s:elseif test="%{!(#typeDoc.equals('-1'))}">
            <% classInfoPerson = "";%>
        </s:elseif>
        <div class="<%= classInfoCompany %>" id="divInfoCompany">
            <div class="control-group">
                <label for="formProfile_digVer" class="control-label">
                    <s:property value="getText('text.digitverify.profile')" />:
                </label>
                <div class="controls">
                    <s:textfield name="digVer" />
                </div>  
            </div>
            <div class="control-group">
                <label for="formProfile_nameCompany" class="control-label">
                    <s:property value="getText('text.namecompany.profile')" />:
                </label>
                <div class="controls">
                    <s:textfield cssClass="form-control" name="nameCompany"/>
                </div>                         
            </div> 
            <h4><s:property value="getText('title.infoagent.profile')" />:</h4>   
            <hr />
            <div class="row">
                <div class="span3 control-group">
                    <label for="formProfile_firstNameRep" class="control-label"><s:property value="getText('text.firstnameagent.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="firstNameRep"/>
                    </div>
                </div>
                <div class="span6 control-group">
                    <label for="formProfile_secondNameRep" class="control-label"><s:property value="getText('text.secondnameagent.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="secondNameRep"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span3 control-group">
                    <label for="formProfile_firstLastNameRep" class="control-label"><s:property value="getText('text.firstlastnameagent.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="firstLastNameRep"/>
                    </div>
                </div>
                <div class="span6 control-group">
                    <label for="formProfile_secondLastNameRep" class="control-label"><s:property value="getText('text.secondlastnameagent.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="secondLastNameRep"/>
                    </div>
                </div>
            </div>
        </div>   
        <div class="<%= classInfoPerson %>" id="divInfoPerson">
            <div class="row">
                <div class="span3 control-group">
                    <label for="formProfile_firstName" class="control-label"><s:property value="getText('text.firstname.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="firstName"/>
                    </div>
                </div>
                <div class="span6 control-group">
                    <label for="formProfile_secondName" class="control-label"><s:property value="getText('text.secondname.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="secondName"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span3 control-group">
                    <label for="formProfile_firstLastName" class="control-label"><s:property value="getText('text.firstlastname.profile')" />:</label>
                        <div class="controls">
                        <s:textfield cssClass="form-control" name="firstLastName"/>
                    </div>
                </div>
                <div class="span6 control-group">
                    <label for="formProfile_secondLastName" class="control-label"><s:property value="getText('text.secondlastname.profile')" />:</label>
                    <div class="controls">
                        <s:textfield cssClass="form-control" name="secondLastName"/>
                    </div>
                </div>
            </div>                     
        </div>                     
        <div class="control-group">
            <label for="formProfile_department" class="control-label"><s:property value="getText('select.department.profile')" />:</label>
            <div class="controls">
                <s:select
                    list="department_producer" 
                    listKey="idDep" 
                    listValue="nameDep" 
                    headerKey="-1" 
                    headerValue="---"
                    onchange="chargeValues('/comboProducer.action', 'depId', this.value, 'formProfile_municipality', 'divMessage')"
                    name="department"
                    />
            </div>
        </div>	
        <div class="control-group">
            <label for="formProfile_municipality" class="control-label"><s:property value="getText('select.municipality.profile')" />:</label>
            <div class="controls">
                <s:select
                    list="city_producer" 
                    listKey="idMun" 
                    listValue="nameMun" 
                    headerKey="-1" 
                    headerValue="---"
                    name="municipality" 
                    />
            </div>
        </div>
        <div class="row">
            <% //if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify")) { %>
            <% if (true) { %>
                <div class="span6">
                    <sj:submit type="button" cssClass="btn btn-initial btn-large" onclick="addMessageProcess()" targets="divMessage" onCompleteTopics="completeProfile" validate="true" validateFunction="validationForm"><i class="icon-save"></i>  <s:property value="getText('button.saveprofile.profile')" /></sj:submit>
                </div>  
            <% } %>
        </div>  
        <script>
            $("#formProfile_digVer").mask("9", {placeholder: ""});
            $.subscribe('completeProfile', function(event, data) {
                completeFormCrop('', 'formProfile', 'divMessProfile', event.originalEvent.request.responseText);
            });
        </script>    
    </s:form>
</fieldset>
