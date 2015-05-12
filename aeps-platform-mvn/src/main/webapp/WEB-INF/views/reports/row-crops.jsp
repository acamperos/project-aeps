<td>
    <s:property value="getText('td.namefield.report')" />: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    <s:property value="getText('td.namefarm.report')" />: #<s:property value="num_farm" />-<s:property value="name_farm" />
</td>
<% if (!typeEnt.equals("2")) { %>
    <td>
        <s:property value="getText('td.nameproducer.report')" />: <s:property value="name_producer" />
        <s:property value="type_doc" />:<s:property value="num_doc" />l
    </td>
<% } %>
<td>
    <s:property value="nameCrop" />
</td>
<td>
    #<s:property value="num_crop" />
</td>
<s:date name="date_sowing" format="MM/dd/yyyy" var="dateTransformSow"/>
<td><s:property value="%{#dateTransformSow}" /></td>
<s:date name="date_harvest" format="MM/dd/yyyy" var="dateTransHar"/>
<td><s:property value="%{#dateTransHar}" /></td>
<td><s:property value="name_genotype" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<td>
    <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify")) { %>
        <div class="btn-group">
            <a class="btn btn-small" title="<s:property value="getText('link.yieldreport.report')" />" onclick="viewForm('getReportYield.action', 'idCrop', ${idCrop}, '<s:property value="getText('title.yieldreport.report')" />', 1050, 820);"><i class="icon-report"></i></a>
        </div>
    <% } %>
</td>