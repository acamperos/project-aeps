<% String valAsig = (String)value; %>
<% valAsig = (String) request.getAttribute("selected"); %>
<% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/modify") || (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/delete"))) { %>
    <% if (valAsig.equals("producer") || valAsig == "producer") {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${id_entity}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelPro')"/>
        </td>
    <% } %>
<% } %>
<% if (valAsig != "producer") {%>    
    <% if (valAsig.equals("property") || valAsig.equals("lot") || valAsig.equals("crop")) {%>
        <td><i class="icon-ok main-color"></i></td>      
    <% } %>    
<% } %>
<% if (entTypeProId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<s:set name="typeDoc" value="type_document"/>
<td>
    <s:property value="name" />
    <s:if test="%{#typeDoc.equals('NIT')}">
        <br>(<s:property value="getText('td.nameagent.producer')" />: <s:property value="agentName" />)
    </s:if>
</td>
<td><s:property value="city" /></td>
<td><s:property value="phone" /></td>
<td><s:property value="cellphone" /></td>
<td><s:property value="e_mail_1" /></td>
<s:date name="dateLog" format="MM/dd/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/modify") || (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/delete"))) { %>
    <% if (valAsig.equals("producer") || valAsig == "producer") {%>
        <td>
            <div class="btn-group">
                <% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/modify")) { %>
                    <a class="btn btn-small btn-edit" title="<s:property value="getText('button.produceredit.producer')" />" onclick="viewForm('/showProducer.action?action=modify&page=<%=pageNow%>', 'idPro', <s:property value ="id_producer" />, '<s:property value="getText('title.produceredit.producer')" />', 1050, 550)"><i class="icon-pencil"></i></a>
                <% } %>
                <% if (usrProDao.getPrivilegeUser(userPro.getIdUsr(), "producer/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="<s:property value="getText('button.producerdelete.producer')" />" onclick="showDialogDelete(this, 'confirm_dialog_producer', '/deleteProducer.action?idPro=<s:property value ="id_producer" />', '/searchProducer.action?page=<%=pageNow%>', 'divProducers', '<%=divHide%>'); ga('send', 'event', 'Producers', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>
