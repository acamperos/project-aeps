<s:date name="dateResMan" format="dd/MM/yyyy" var="dateTransformRowRes"/>
<td><s:property value="%{#dateTransformRowRes}" /></td>
<td><s:property value="residualsResMan" /></td>
<td><s:property value="otherResidualsResMan" /></td>
<td>
    <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/modify") || (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/delete"))) { %>
        <div class="btn-group">
            <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Rastrojo" onclick="viewForm('/crop/showResidual.action?action=modify&idCrop=${idCrop}', 'idResMan', ${idResMan}, 'Editar Rastrojo', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Rastrojo" onclick="showDialogDelete(this, 'confirm_dialog_prep', 'deleteResidual.action?idResMan=${idResMan}', 'searchResidual.action?idCrop=${idCrop}', 'divRes', 'divListRes');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>