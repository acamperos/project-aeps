<% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
    <% if (value.equals("lot") || value == "lot") {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_lot}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelFie')"/>
        </td>
    <% }%>
<% } %>
<% if (value != "lot") {%>
    <% if (value.equals("crop")) { %>
        <td><img src="/img/check.ico"/></td>          
    <% } else if (value.equals("rasta")) {%>
        <td><img src="/img/check.ico"/></td>
    <% }%>
<% }%>
<% if (entTypeFieId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<td>
    Lote: <s:property value="name_lot" />, <br />
    Finca: <s:property value="name_far" />
    <% if (!typeEnt.equals("2")) { %>
        , <br />
        Productor: <s:property value="name_producer" />
    <% } %>
</td>
<td><s:property value="name_type_lot" /></td>
<td><s:property value="area_lot" /></td>
<td><s:property value="latitude_lot" /></td>
<td><s:property value="length_lot" /></td>
<td><s:property value="altitude_lot" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
    <% if (value.equals("lot") || value == "lot") {%>
        <td>
            <div class="btn-group">
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify")) { %>
                    <a class="btn btn-small btn-edit" title="Editar Lote" onclick="viewForm('/showField.action?action=modify&page=<%=pageNow%>', 'idField', <s:property value ="id_lot" />, 'Editar Lote', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Lote" onclick="showDialogDelete(this, 'confirm_dialog_lot', 'deleteField.action?idFar=<s:property value ="id_lot" />', 'searchField.action?page=<%=pageNow%>', 'divFields', '<%=divHide%>'); ga('send', 'event', 'Fields', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% }%>
<% } %>