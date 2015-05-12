<s:date name="dateMon" format="MM/dd/yyyy" var="dateTransformRowMon"/>
<td><s:property value="%{#dateTransformRowMon}" /></td>
<td>
    <s:property value="monDesPet" /><br />
    <s:if test="%{!namePets.equals('')}">
        <s:property value="getText('td.namepest.monitoring')" />: <s:property value="namePets" /><br />
        <s:property value="getText('td.percentpest.monitoring')" />: <s:property value="perPets" />
    </s:if>
</td>
<td>
    <s:property value="monDesDis" /><br />
    <s:if test="%{!nameDis.equals('')}">
        <s:property value="getText('td.namedisease.monitoring')" />: <s:property value="nameDis" /><br />
        <s:property value="getText('td.percentdisease.monitoring')" />: <s:property value="perDis" />
    </s:if>
</td>
<td>
    <s:property value="monDesWee" /><br />
    <s:if test="%{!nameWee.equals('')}">
        <s:property value="getText('td.nameweed.monitoring')" />: <s:property value="nameWee" /><br />
        <s:property value="getText('td.percentweed.monitoring')" />: <s:property value="perWee" />
    </s:if>
</td>
<td>
    <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify") || (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete"))) { %>
        <% if (entTypeMonId!=3) { %>
            <div class="btn-group">
                <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.monitoringedit.monitoring')" />" onclick="viewForm('/crop/showMon.action?action=modify&idCrop=${idCrop}', 'idMon', ${idMon}, '<s:property value="getText('title.monitoringedit.monitoring')" />', 1050, 550);"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.monitoringdelete.monitoring')" />" onclick="showDialogDelete(this, 'confirm_dialog_mon', 'deleteMon.action?idMon=<s:property value="idMon" />', 'searchMon.action?idCrop=${idCrop}', 'divMon', 'divListMonGen');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        <% } %>
    <% } %>
</td>