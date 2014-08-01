<td>
    Lote: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    Finca: #<s:property value="num_farm" />-<s:property value="name_farm" />
    <% if (!typeEnt.equals("2")) { %>
        Productor: <s:property value="name_producer" />
    <% } %>
    
</td>
<s:date name="date" format="dd/MM/yyyy" var="dateTransformDateRas"/>
<td><s:property value="%{#dateTransformDateRas}" /></td>
<td><s:property value="pendant" /></td>
<td>Latitud: <s:property value="latitude" /><br />Longitud: <s:property value="length" /><br />Altura: <s:property value="altitude" /></td>
<td><s:property value="ground" /></td>
<td><s:property value="position" /></td>
<td><s:property value="num_layer" /></td>
<td><s:property value="ph" /></td>
<td><s:property value="carbonates" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/delete"))) { %>
    <td>
        <div class="btn-group">
            <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify")) { %>
                <a class="btn btn-small btn-edit" title="Editar Rasta" onclick="viewForm('/soil/showSoil.action?action=modify&page=<%=pageNow%>', 'idRasta', <s:property value ="id_ras" />, 'Editar Rasta', 1050, 550)"><i class="icon-pencil"></i></a>
            <% } %>
            <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/delete")) { %>
                <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Rasta" onclick="showDialogDelete(this, 'confirm_dialog_lot', 'deleteSoil.action?idRasta=<s:property value ="id_ras" />', 'searchSoil.action?page=<%=pageNow%>', 'divRasta', '<%=divHide%>');"><i class="icon-trash"></i></a>
            <% } %>
        </div>
    </td>
<% } %>