<s:date name="dateResMan" format="MM/dd/yyyy" var="dateTransformRowRes"/>
<td><s:property value="%{#dateTransformRowRes}" /></td>
<td><s:property value="residualsResMan" /></td>
<td><s:property value="otherResidualsResMan" /></td>
<td>
    <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/modify") || (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/delete"))) { %>
        <% if (entTypeResId!=3) { %>
            <div class="btn-group">
                <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.residualedit.residual')" />" onclick="viewForm('/crop/showResidual.action?action=modify&idCrop=${idCrop}', 'idResMan', ${idResMan}, '<s:property value="getText('title.residualedit.residual')" />', 1050, 550);"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.residualdelete.residual')" />" onclick="showDialogDelete(this, 'confirm_dialog_prep', 'deleteResidual.action?idResMan=${idResMan}', 'searchResidual.action?idCrop=${idCrop}', 'divRes', 'divListRes');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        <% } %>
    <% } %>
</td>