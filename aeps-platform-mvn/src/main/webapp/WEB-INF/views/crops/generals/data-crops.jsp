<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<div class="panel">
    <div class="panel-body w-box">             
        <fieldset>
            <legend>
                <h3>
                    <s:property value="getText('title.infoCrop.crop')" /> 
                    <s:if test="%{typeCrop==1}">
                        <s:property value="nameTypeCrop" /> <s:property value="getText('text.machining.crop')" /> # <s:property value="idCrop" /></h3>
                    </s:if>
                    <s:else>
                        <s:property value="nameTypeCrop" /> # <s:property value="idCrop" />
                    </s:else>
                </h3>    
            </legend>
            <table class="table table-bordered">
                <tbody>
                    <s:if test="%{typeEnt!=2}">
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.producername.crop')" /></th>
                            <td><s:property value="name_producer" /></td>
                        </tr>
                        <tr>
                            <th><s:property value="getText('tr.docproducer.crop')" /></th>
                            <td><s:property value="type_doc" />: <s:property value="num_doc" /></td>
                        </tr>
                        <tr>
                            <th><s:property value="getText('tr.namefarm.crop')" /></th>
                            <td><s:property value="name_farm" /></td>
                        </tr>
                    </s:if>
                    <s:else>
                        <tr>
                            <th style="width: 30%"><s:property value="getText('tr.namefarm.crop')" /></th>
                            <td><s:property value="name_farm" /></td>
                        </tr>
                    </s:else>    
                    <tr>   
                        <th><s:property value="getText('tr.namefield.crop')" /></th>
                        <td><s:property value="nameField" /></td>
                    </tr>
                    <tr>
                        <th><s:property value="getText('tr.lastcrop.crop')" /></th>
                        <td><s:property value="lastTypeCrop" /></td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </div>       
</div>
<% if (entTypeId!=3) { %>                        
    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify")) { %>
        <button type="button" class="btn btn-initial" onclick="viewForm('/crop/showCrop.action?action=modify', 'idCrop', '${idCrop}', '<s:property value="getText('button.modifyprodevent.crop')" />', 1050, 700)">
            <i class="icon-pencil"></i> <s:property value="getText('button.modifyprodevent.crop')" />
        </button>
    <% } %>
    <button type="button" class="btn btn-default" onclick="returnListCrop()">
        <i class="icon-arrow-left"></i> <s:property value="getText('button.backtocroplist.crop')" />
    </button>
<% } %>