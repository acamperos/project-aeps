<s:date name="dateIrr" format="dd/MM/yyyy" var="dateTransformRowIrr"/>
<td><s:property value="useDesIrr" /></td>
<td><s:property value="%{#dateTransformRowIrr}" /></td>
<td><s:property value="amountIrr" /></td>
<td><s:property value="nameIrrType" /></td>
<td>
    <div class="btn-group">
        <a href="#" class="btn btn-small" title="Editar Riego" onclick="viewForm('/aeps-plataforma-mvn/crop/showIrr.action?action=modify&idCrop=${idCrop}', 'idIrr', ${idIrr}, 'Editar Riego', 1050, 550);"><i class="icon-pencil"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Riego" onclick="showDialogDelete(this, 'confirm_dialog_irr', 'deleteIrr.action?idIrr=<s:property value="idIrr" />', 'searchIrr.action?idCrop=${idCrop}', 'divIrr', 'divListIrr');"><i class="icon-trash"></i></a>
    </div>
</td>