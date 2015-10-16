<% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>
    <% if (value == "soil" || value.equals("soil")) {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_soil}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelSoil')"/>
        </td>
    <% } %>
<% } %>

<% if (entTypeSoilId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<td>
    <s:property value="getText('td.namefield.soilanalysis')" />: <s:property value="name_field" />,<br />
    <s:property value="getText('td.namefarm.soilanalysis')" />: <s:property value="name_farm" />
    <% if (!typeEnt.equals("2")) { %>
        <s:property value="getText('td.nameproducer.soilanalysis')" />: <s:property value="name_producer" />
    <% } %>
    
</td>
<s:date name="date" format="MM/dd/yyyy" var="dateTransformDateSoil"/>
<td><s:property value="%{#dateTransformDateSoil}" /></td>
<td><s:property value="num_sampling" /></td>
<td><s:property value="name_crop" /></td>
<td><s:property value="texture_type" /></td>
<td><s:property value="ph" /></td>
<td><s:property value="cation" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>
    <% if (value == "soil" || value.equals("soil")) {%>
        <td>
            <div class="btn-group">
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('link.soiledit.soilanalysis')" />" onclick="viewForm('/soilchemical/showSoilChemical.action?action=modify&page=<%=pageNow%>', 'idSoil', <s:property value = "id_soil" />, '<s:property value="getText('title.soiledit.soilanalysis')" />', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('link.soildel.soilanalysis')" />" onclick="showDialogDelete(this, 'confirm_dialog_soil', '/soilchemical/deleteSoilChemical.action?idSoil=<s:property value = "id_soil" />', '/soilchemical/searchSoilChemical.action?page=<%=pageNow%>', 'divSoil', '<%=divHide%>'); ga('send', 'event', 'SoilsChemical', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>