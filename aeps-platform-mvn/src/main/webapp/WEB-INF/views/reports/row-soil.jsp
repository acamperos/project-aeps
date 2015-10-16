<td>
    <s:property value="getText('td.namefield.report')" />: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    <s:property value="getText('td.namefarm.report')" />: #<s:property value="num_farm" />-<s:property value="name_farm" />
    <% if (!typeEnt.equals("2")) { %>
        <s:property value="getText('td.nameproducer.report')" />: <s:property value="name_producer" />
    <% } %>
    
</td>
<s:date name="date" format="MM/dd/yyyy" var="dateTransformDateRas"/>
<td><s:property value="%{#dateTransformDateRas}" /></td>
<td><s:property value="getText('td.latitude.report')" />: <s:property value="latitude" /><br /><s:property value="getText('td.longitude.report')" />: <s:property value="length" /><br /><s:property value="getText('td.altitude.report')" />: <s:property value="altitude" /></td>
<td><s:property value="ground" /></td>
<td><s:property value="position" /></td>
<td><s:property value="num_layer" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/delete"))) { %>
    <td>
        <div class="btn-group">
            <a class="btn btn-small" title="<s:property value="getText('link.soilreport.report')" />" onclick="viewForm('/soil/generateInf.action', 'idRasta', ${id_ras}, '<s:property value="getText('title.soilreport.report')" />', 1050, 820);"><i class="icon-report"></i></a>
        </div>
    </td>
<% } %>