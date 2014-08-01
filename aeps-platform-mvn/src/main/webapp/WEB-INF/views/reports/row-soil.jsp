<td>
    Lote: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    Finca: #<s:property value="num_farm" />-<s:property value="name_farm" />
    <% if (!typeEnt.equals("2")) { %>
        Productor: <s:property value="name_producer" />
    <% } %>
    
</td>
<s:date name="date" format="dd/MM/yyyy" var="dateTransformDateRas"/>
<td><s:property value="%{#dateTransformDateRas}" /></td>
<!--<td><s:property value="pendant" /></td>-->
<td>Latitud: <s:property value="latitude" /><br />Longitud: <s:property value="length" /><br />Altura: <s:property value="altitude" /></td>
<td><s:property value="ground" /></td>
<td><s:property value="position" /></td>
<td><s:property value="num_layer" /></td>
<!--<td><s:property value="ph" /></td>-->
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/delete"))) { %>
    <td>
        <div class="btn-group">
            <a class="btn btn-small" title="Reporte de Suelo" onclick="viewForm('/soil/generateInf.action', 'idRasta', ${id_ras}, 'Reporte de Suelo', 1050, 820);"><i class="icon-report"></i></a>
        </div>
    </td>
<% } %>