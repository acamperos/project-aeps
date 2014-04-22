<s:date name="dateMon" format="dd/MM/yyyy" var="dateTransformRowMon"/>
<td><s:property value="%{#dateTransformRowMon}" /></td>
<td><s:property value="monDesPet" /></td>
<td><s:property value="monDesDis" /></td>
<td><s:property value="monDesWee" /></td>
<td>
    <div class="btn-group">
        <a href="#" class="btn btn-small" title="Editar Monitoreo" onclick="viewForm('/aeps-plataforma-mvn/crop/showMon.action?action=modify&idCrop=${idCrop}', 'idMon', ${idMon}, 'Editar Monitoreo', 1050, 550);"><i class="icon-pencil"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Monitoreo" onclick="showDialogDelete(this, 'confirm_dialog_mon', 'deleteMon.action?idMon=<s:property value="idMon" />', 'searchMon.action?idCrop=${idCrop}', 'divMon', 'divListMonGen');"><i class="icon-trash"></i></a>
    </div>
</td>