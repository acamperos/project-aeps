<s:date name="datePrep" format="MM/dd/yyyy" var="dateTransformRowPrep"/>
<td><s:property value="%{#dateTransformRowPrep}" /></td>
<td><s:property value="depthPrep" /></td>
<td><s:property value="namePrep" /></td>
<td><s:property value="otherNamePrep" /></td>
<td><s:property value="passNum" /></td>
<td>
    <% if (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/modify") || (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/delete"))) { %>
        <% if (entTypePrpId!=3) { %>
            <div class="btn-group">
                <% if (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.preparationedit.preparation')" />" onclick="viewForm('/crop/showPrep.action?action=modify&idCrop=${idCrop}', 'idPrep', ${idPrep}, '<s:property value="getText('title.preparationedit.preparation')" />', 1050, 550);"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.deletepreparation.preparation')" />" onclick="showDialogDelete(this, 'confirm_dialog_prep', '/crop/deletePrep.action?idPrep=<s:property value="idPrep" />', '/crop/searchPrep.action?idCrop=${idCrop}', 'divPrep', 'divListPrep');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        <% } %>
    <% } %>
</td>