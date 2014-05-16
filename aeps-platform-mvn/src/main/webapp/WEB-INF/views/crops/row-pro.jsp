<s:date name="dateCon" format="dd/MM/yyyy" var="dateTransformRowCon"/>
<td><s:property value="%{#dateTransformRowCon}" /></td>
<td><s:property value="nameTarTyp" /></td>
<td><s:property value="nameConTyp" /></td>
<!--<td><s:property value="cleaning" /></td>
<td><s:property value="frequence" /></td>-->
<td><s:property value="chemCon" /></td>
<td><s:property value="doseChemCon" /><s:property value="unitChemCon" /></td>
<td><s:property value="orgCon" /></td>
<td><s:property value="doseOrgCon" /><s:property value="unitOrgCon" /></td>
<td>
    <% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "crop/modify") || (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "crop/delete"))) { %>
        <div class="btn-group">
            <% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Control" onclick="viewForm('/aeps-plataforma-mvn/crop/showCon.action?action=modify&idCrop=${idCrop}', 'idCon', ${idCon}, 'Editar Control', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Control" onclick="showDialogDelete(this, 'confirm_dialog_con', 'deleteCon.action?idCon=${idCon}', 'searchCon.action?idCrop=${idCrop}', 'divPro', 'divListPro');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>