<% //HashMap add   = (HashMap) request.getAttribute("additionals");%>
<% //String value  = (String) (add.get("selected"));%>
<% if (value != "property") {%>
    <% if (value.equals("lot")) {%>
        <td><img src="/aeps-plataforma-mvn/img/check.ico"/></td>
    <% } else if (value.equals("crop")) {%>
        <td><img src="/aeps-plataforma-mvn/img/check.ico"/></td>
    <% }%>                        
<% }%>
<td>
    Finca: <s:property value="name_farm" />
    <s:if test="%{typeEnt!=1}">
        , <br />
        Productor: <s:property value="name_producer" />
    </s:if>
</td>
<td><s:property value="lane_farm" /></td>
<td><s:property value="dir_farm" /></td>
<td><s:property value="name_dep" /></td>
<td><s:property value="name_mun" /></td>
<td><s:property value="latitude_farm" /></td>
<td><s:property value="length_farm" /></td>
<s:number name="altitude_farm" type="integer" var="altFarm" />
<td><s:property value="%{#altFarm}" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/delete"))) { %>
    <% if (value.equals("property") || value == "property") {%>
        <td>
            <div class="btn-group">
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/modify")) { %>
                    <a class="btn btn-small btn-edit" title="Editar Finca" onclick="viewForm('/aeps-plataforma-mvn/showFarm.action?action=modify&page=<%=pageNow%>', 'idFar', <s:property value ="id_farm" />, 'Editar Finca', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "farm/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Finca" onclick="showDialogDelete(this, 'confirm_dialog_property', 'deleteFarm.action?idFar=<s:property value ="id_farm" />', 'searchFarm.action?page=<%=pageNow%>', 'divFarms', '<%=divHide%>');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>