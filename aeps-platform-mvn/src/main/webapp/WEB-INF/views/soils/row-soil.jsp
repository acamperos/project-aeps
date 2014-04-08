<td><s:property value="num_rasta" /></td>
<td><s:property value="date" /></td>
<td><s:property value="pendant" /></td>
<td><s:property value="latitude" /></td>
<td><s:property value="length" /></td>
<td><s:property value="altitude" /></td>
<td><s:property value="ground" /></td>
<td><s:property value="position" /></td>
<td><s:property value="num_layer" /></td>
<td><s:property value="ph" /></td>
<td><s:property value="carbonates" /></td>
<td>
    <div class="btn-group">
        <a href="#" class="btn btn-small" title="Editar Rasta" onclick="viewForm('/aeps-plataforma-mvn/soil/showSoil.action?action=modify&page=<%=pageNow%>', 'idRasta', <s:property value ="id_ras" />, 'Editar Rasta', 1050, 550)"><i class="icon-pencil"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Rasta" onclick="showDialogDelete(this, 'confirm_dialog_lot', 'deleteSoil.action?idRasta=<s:property value ="id_ras" />', 'searchSoil.action?page=<%=pageNow%>', 'divRasta', '<%=divHide%>');"><i class="icon-trash"></i></a>
    </div>
</td>