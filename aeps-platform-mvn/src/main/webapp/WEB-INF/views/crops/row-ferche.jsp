<s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
<td><s:property value="%{#dateTransformRowFer}" /></td>
<td><s:property value="amountUsed" /></td>
<s:set name="useFerTyp" value="%{infoChe.get('idFerTyp')}"/>
<s:if test="%{#useFerTyp!=null}">
    <td><s:property value="%{infoChe.get('nameFerTyp')}" /></td>
</s:if> 
<s:elseif test="%{#useFerTyp==null}">
    <td><s:property value="%{infoChe.get('otherProduct')}" /></td>
</s:elseif>                        
<td><s:property value="%{infoChe.get('composition')}" /></td>
<td><s:property value="%{infoChe.get('value')}" /></td>
<td>
    <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
    <div class="btn-group">
            <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Fertilizacion Quimica" onclick="viewForm('/aeps-plataforma-mvn/crop/showFer.action?action=modify&idCrop=${idCrop}', 'idFer', ${idFer}, 'Editar Fertilizacion Quimica', 1050, 550);"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Fertilizacion Quimica" onclick="showDialogDelete(this, 'confirm_dialog_ferche', 'deleteFer.action?idFer=${idFer}', 'searchFer.action?idCrop=${idCrop}', 'divFerChe', 'divListFer');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    <% } %>
</td>