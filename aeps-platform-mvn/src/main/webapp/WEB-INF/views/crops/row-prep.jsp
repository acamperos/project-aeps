<s:date name="datePrep" format="dd/MM/yyyy" var="dateTransformRowPrep"/>
<td><s:property value="%{#dateTransformRowPrep}" /></td>
<td><s:property value="depthPrep" /></td>
<td><s:property value="namePrep" /></td>
<td><s:property value="otherNamePrep" /></td>
<td><s:property value="residualsPrep" /></td>
<td><s:property value="otherResidualsPrep" /></td>
<td>
    <div class="btn-group">
        <a href="#" class="btn btn-small" title="Editar Preparación" onclick="viewForm('/aeps-plataforma-mvn/crop/showPrep.action?action=modify&idCrop=${idCrop}', 'idPrep', ${idPrep}, 'Editar Preparación', 1050, 550);"><i class="icon-pencil"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Preparación" onclick="showDialogDelete(this, 'confirm_dialog_prep', 'deletePrep.action?idPrep=<s:property value="idPrep" />', 'searchPrep.action?idCrop=${idCrop}', 'divPrep', 'divListPrep');"><i class="icon-trash"></i></a>
    </div>
</td>