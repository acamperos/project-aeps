<s:date name="dateMon" format="dd/MM/yyyy" var="dateTransformRowMon"/>
<td><s:property value="%{#dateTransformRowMon}" /></td>
<td>
    <s:property value="monDesPet" /><br />
    <s:if test="%{!namePets.equals('')}">
        Nom. Plaga: <s:property value="namePets" /><br />
        Porcentaje: <s:property value="perPets" />
    </s:if>
</td>
<td>
    <s:property value="monDesDis" /><br />
    <s:if test="%{!nameDis.equals('')}">
        Nom. Enfermedad: <s:property value="nameDis" /><br />
        Porcentaje: <s:property value="perDis" />
    </s:if>
</td>
<td>
    <s:property value="monDesWee" /><br />
    <s:if test="%{!nameWee.equals('')}">
        Nom. Maleza: <s:property value="nameWee" /><br />
        Porcentaje: <s:property value="perWee" />
    </s:if>
</td>
<td>
    <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify") || (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete"))) { %>
        <div class="btn-group">
            <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Monitoreo" onclick="viewForm('/crop/showMon.action?action=modify&idCrop=${idCrop}', 'idMon', ${idMon}, 'Editar Monitoreo', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Monitoreo" onclick="showDialogDelete(this, 'confirm_dialog_mon', 'deleteMon.action?idMon=<s:property value="idMon" />', 'searchMon.action?idCrop=${idCrop}', 'divMon', 'divListMonGen');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>