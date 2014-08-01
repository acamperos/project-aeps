<% String valAsig = (String)value; %>
<% valAsig = (String) request.getAttribute("selected"); %>
<% if (valAsig != "producer") {%>    
    <% if (valAsig.equals("property") || valAsig.equals("lot") || valAsig.equals("crop")) {%>
        <td><img src="/img/check.ico"/></td>      
    <% } %>    
<% } %>
<s:set name="typeDoc" value="type_document"/>
<td>
    <s:property value="name" />
    <s:if test="%{#typeDoc.equals('NIT')}">
        <br>(Responsable: <s:property value="agentName" />)
    </s:if>
</td>
<td><s:property value="city" /></td>
<td><s:property value="phone" /></td>
<td><s:property value="cellphone" /></td>
<td><s:property value="e_mail_1" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/delete"))) { %>
    <% if (valAsig.equals("producer") || valAsig == "producer") {%>
        <td>
            <div class="btn-group">
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/modify")) { %>
                    <a class="btn btn-small btn-edit" title="Editar Productor" onclick="viewForm('/showProducer.action?action=modify&page=<%=pageNow%>', 'idPro', <s:property value ="id_producer" />, 'Editar Productor', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "producer/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Productor" onclick="showDialogDelete(this, 'confirm_dialog_producer', 'deleteProducer.action?idPro=<s:property value ="id_producer" />', 'searchProducer.action?page=<%=pageNow%>', 'divProducers', '<%=divHide%>');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>
