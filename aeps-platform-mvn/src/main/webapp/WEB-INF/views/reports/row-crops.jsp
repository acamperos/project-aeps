<td>
    Lote: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    Finca: #<s:property value="num_farm" />-<s:property value="name_farm" />
</td>
<% if (!typeEnt.equals("2")) { %>
    <td>
        Productor: <s:property value="name_producer" />
        <s:property value="type_doc" />:<s:property value="num_doc" />l
    </td>
<% } %>
<td>
    <s:property value="nameCrop" />
</td>
<td>
    #<s:property value="num_crop" />
</td>
<s:date name="date_sowing" format="dd/MM/yyyy" var="dateTransformSow"/>
<td><s:property value="%{#dateTransformSow}" /></td>
<s:date name="date_harvest" format="dd/MM/yyyy" var="dateTransHar"/>
<td><s:property value="%{#dateTransHar}" /></td>
<td><s:property value="name_genotype" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<td>
    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify")) { %>
        <div class="btn-group">
            <a class="btn btn-small" title="Reporte de Productividad" onclick="viewForm('getReportYield.action', 'idCrop', ${idCrop}, 'Reporte de Productividad', 1050, 820);"><i class="icon-report"></i></a>
        </div>
    <% } %>
</td>