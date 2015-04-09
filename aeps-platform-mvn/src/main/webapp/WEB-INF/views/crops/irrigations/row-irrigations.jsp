<s:date name="dateIrr" format="dd/MM/yyyy" var="dateTransformRowIrr"/>
<td><s:property value="%{#dateTransformRowIrr}" /></td>
<td><s:property value="amountIrr" /></td>
<td><s:property value="nameIrrType" /></td>
<td>
    <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify") || (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete"))) { %>
        <div class="btn-group">
            <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="<s:property value="getText('link.irrigationedit.irrigation')" />" onclick="viewForm('/crop/showIrr.action?action=modify&idCrop=${idCrop}', 'idIrr', ${idIrr}, '<s:property value="getText('title.irrigationedit.irrigation')" />', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.deleteirrigation.irrigation')" />" onclick="showDialogDelete(this, 'confirm_dialog_irr', 'deleteIrr.action?idIrr=${idIrr}', 'searchIrr.action?idCrop=${idCrop}', 'divIrr', 'divListIrr');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>