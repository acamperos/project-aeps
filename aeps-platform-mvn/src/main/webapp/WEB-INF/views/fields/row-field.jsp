<% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
    <% if (value.equals("lot") || value == "lot") {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_lot}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelFie')"/>
        </td>
    <% }%>
<% } %>
<% if (value != "lot") {%>
    <% if (value.equals("crop") || value.equals("rasta") || value.equals("cropcheck")) { %>
        <td><i class="icon-ok main-color"></i></td>          
    <% } %>
<% }%>
<% if (entTypeFieId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<td>
    <s:property value="getText('td.namefield.field')" />: <s:property value="name_lot" />, <br />
    <s:property value="getText('td.namefarm.field')" />: <s:property value="name_far" />
    <% if (!typeEnt.equals("2")) { %>
        , <br />
        <s:property value="getText('td.nameproducer.field')" />: <s:property value="name_producer" />
    <% } %>
</td>
<td><s:property value="name_type_lot" /></td>
<td><s:property value="area_lot" /></td>
<td><s:property value="latitude_lot" /></td>
<td><s:property value="length_lot" /></td>
<td><s:property value="altitude_lot" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
    <% if (value.equals("lot") || value == "lot") {%>
        <td>
            <div class="btn-group">
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('button.fieldedit.field')" />" onclick="viewForm('/showField.action?action=modify&page=<%=pageNow%>', 'idField', <s:property value ="id_lot" />, '<s:property value="getText('title.fieldedit.field')" />', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('button.fielddel.field')" />" onclick="showDialogDelete(this, 'confirm_dialog_lot', '/deleteField.action?idField=<s:property value ="id_lot" />', '/searchField.action?page=<%=pageNow%>', 'divFields', '<%=divHide%>'); ga('send', 'event', 'Fields', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% }%>
<% } %>