<s:date name="dateDesPro" format="dd/MM/yyyy" var="dateTransformRowDes"/>
<td><s:property value="%{#dateTransformRowDes}" /></td>
<td><s:property value="observationDesPro" /></td>
<td>
    <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/modify") || (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/delete"))) { %>
        <div class="btn-group">
            <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Observación" onclick="viewForm('/crop/showDescrip.action?action=modify&idCrop=${idCrop}', 'idDesPro', ${idDesPro}, 'Editar Observación', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Observación" onclick="showDialogDelete(this, 'confirm_dialog_des', 'deleteDescrip.action?idDesPro=${idDesPro}', 'searchDescrip.action?idCrop=${idCrop}', 'divDes', 'divListDes');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>