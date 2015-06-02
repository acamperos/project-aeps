<% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>
    <% if (value == "rasta" || value.equals("rasta")) {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_ras}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelSoil')"/>
        </td>
    <% } %>
<% } %>
<% if (entTypeSoilId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<td>
    <s:property value="getText('td.namefield.soil')" />: <s:property value="name_field" />,<br />
    <s:property value="getText('td.namefarm.soil')" />: <s:property value="name_farm" />
    <% if (!typeEnt.equals("2")) { %>
        <s:property value="getText('td.nameproducer.soil')" />: <s:property value="name_producer" />
    <% } %>
    
</td>
<s:date name="date" format="MM/dd/yyyy" var="dateTransformDateRas"/>
<td><s:property value="%{#dateTransformDateRas}" /></td>
<td><s:property value="pendant" /></td>
<td><s:property value="getText('td.latitude.soil')" />: <s:property value="latitude" /><br /><s:property value="getText('td.longitude.soil')" />: <s:property value="length" /><br /><s:property value="getText('td.altitude.soil')" />: <s:property value="altitude" /></td>
<td><s:property value="ground" /></td>
<td><s:property value="position" /></td>
<td><s:property value="num_layer" /></td>
<td><s:property value="ph" /></td>
<td><s:property value="carbonates" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>
    <% if (value == "rasta" || value.equals("rasta")) {%>
        <td>
            <div class="btn-group">
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.rastaedit.soil')" />" onclick="viewForm('/soil/showSoil.action?action=modify&page=<%=pageNow%>', 'idRasta', <s:property value ="id_ras" />, '<s:property value="getText('title.rastaedit.soil')" />', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.rastadel.soil')" />" onclick="showDialogDelete(this, 'confirm_dialog_lot', '/soil/deleteSoil.action?idRasta=<s:property value ="id_ras" />', '/soil/searchSoil.action?page=<%=pageNow%>', 'divRasta', '<%=divHide%>'); ga('send', 'event', 'Soils', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>