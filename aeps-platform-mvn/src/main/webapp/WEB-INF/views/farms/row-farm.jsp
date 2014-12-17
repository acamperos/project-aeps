<% //HashMap add   = (HashMap) request.getAttribute("additionals");%>
<% //String value  = (String) (add.get("selected"));%>
<% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify") || (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete"))) { %>
    <% if (value.equals("property") || value == "property") {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_farm}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelFar')"/>
        </td>
    <% } %>
<% } %>
<% if (value != "property") {%>
    <% if (value.equals("lot")) {%>
        <td><img src="/img/check.ico"/></td>
    <% } else if (value.equals("crop")) {%>
        <td><img src="/img/check.ico"/></td>
    <% }%>                        
<% }%>
<% if (entTypeFarId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
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
<% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify") || (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete"))) { %>
    <% if (value.equals("property") || value == "property") {%>
        <td>
            <div class="btn-group">
                <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify")) { %>
                    <a class="btn btn-small btn-edit" title="Editar Finca" onclick="viewForm('/showFarm.action?action=modify&page=<%=pageNow%>', 'idFar', <s:property value ="id_farm" />, 'Editar Finca', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Finca" onclick="showDialogDelete(this, 'confirm_dialog_property', 'deleteFarm.action?idFar=<s:property value ="id_farm" />', 'searchFarm.action?page=<%=pageNow%>', 'divFarms', '<%=divHide%>'); ga('send', 'event', 'Farms', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>