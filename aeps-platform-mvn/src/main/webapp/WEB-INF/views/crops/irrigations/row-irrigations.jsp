<s:date name="dateIrr" format="MM/dd/yyyy" var="dateTransformRowIrr"/>
<% if (coCodeIrr.equals("CO")) { %>
    <td><s:property value="%{#dateTransformRowIrr}" /></td>
    <td><s:property value="amountIrr" /></td>
    <td><s:property value="nameIrrType" /></td>
<% } else if (coCodeIrr.equals("NI")) { %>
    <s:date name="dateWetIrr" format="MM/dd/yyyy" var="dateTransformWetIrr"/>
    <s:if test="%{useIrr==1}">
        <td><s:property value="%{#dateTransformRowIrr}" /></td>
        <td><s:property value="thickness" /></td>
        <td></td>
        <td></td>
    </s:if>
    <s:elseif test="%{useIrr==2}">
        <td></td>
        <td></td>
        <td><s:property value="%{#dateTransformWetIrr}" /></td>
        <td><s:property value="duration" /></td>
    </s:elseif>    
<% } %>
<td>
    <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify") || (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete"))) { %>
        <% if (entTypeIrrId!=3) { %>    
            <div class="btn-group">
                <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.irrigationedit.irrigation')" />" onclick="viewForm('/crop/showIrr.action?action=modify&idCrop=${idCrop}', 'idIrr', ${idIrr}, '<s:property value="getText('title.irrigationedit.irrigation')" />', 1050, 550);"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.deleteirrigation.irrigation')" />" onclick="showDialogDelete(this, 'confirm_dialog_irr', 'deleteIrr.action?idIrr=${idIrr}', 'searchIrr.action?idCrop=${idCrop}', 'divIrr', 'divListIrr');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        <% } %>
    <% } %>
</td>