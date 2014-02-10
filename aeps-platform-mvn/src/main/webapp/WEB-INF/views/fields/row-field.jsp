<% if (value != "lot") {%>
    <% if (value.equals("crop")) { %>
        <td><img src="/aeps-plataforma-mvn/img/check.ico"/></td>                     
    <% }%>
<% }%>
<td>
    <s:property value="name_lot" />
    (<s:property value="name_producer" />-<s:property value="name_far" />)
</td>
<td><s:property value="name_type_lot" /></td>
<td><s:property value="area_lot" /></td>
<td><s:property value="latitude_lot" /></td>
<td><s:property value="length_lot" /></td>
<td><s:property value="altitude_lot" /></td>
<% if (value.equals("lot") || value == "lot") {%>
    <td>
        <div class="btn-group">
            <a href="#" class="btn btn-mini" title="Editar Lote" onclick="viewForm('/aeps-plataforma-mvn/showField.action?action=modify&page=<%=pageNow%>', 'idField', <s:property value ="id_lot" />, 'Editar Lote', 1050, 550)"><i class="icon-pencil"></i></a>
            <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Lote" onclick="showDialogDelete(this, 'confirm_dialog_lot', 'deleteField.action?idFar=<s:property value ="id_lot" />', 'searchField.action?page=<%=pageNow%>', 'divFields', '<%=divHide%>');"><i class="icon-trash"></i></a>
        </div>
    </td>
<% }%>