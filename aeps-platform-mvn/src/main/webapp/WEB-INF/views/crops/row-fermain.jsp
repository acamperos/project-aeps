<s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
<td rowspan="${contFert}"><s:property value="%{#dateTransformRowFer}" /></td>
<td><s:property value="%{infoFert.get('ferTyp')}" /></td>
<td><s:property value="%{infoFert.get('amountUsed')}" /></td>
<s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
<s:if test="%{#useFerTyp!=null}">
    <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
</s:if> 
<s:elseif test="%{#useFerTyp==null}">
    <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
</s:elseif>                        
<td rowspan="${contFert}">
    <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
    <div class="btn-group">
            <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Fertilizacion" onclick="viewForm('/crop/showFer.action?action=modify&idCrop=${idCrop}', 'idFer', ${idFer}, 'Editar Fertilizacion', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Fertilizacion" onclick="showDialogDelete(this, 'confirm_dialog_fergen', 'deleteFer.action?idFer=${idFer}', 'searchFer.action?idCrop=${idCrop}', 'divFerGen', 'divListFer');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>