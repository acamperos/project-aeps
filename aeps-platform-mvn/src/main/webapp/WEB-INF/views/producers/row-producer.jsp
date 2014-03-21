<% String valAsig = (String)value; %>
<% valAsig = (String) request.getAttribute("selected"); %>
<% if (valAsig != "producer") {%>    
    <% if (valAsig.equals("property") || valAsig.equals("lot") || valAsig.equals("crop")) {%>
        <td><img src="/aeps-plataforma-mvn/img/check.ico"/></td>      
    <% } %>    
<% } %>    
<td><s:property value="type_document" />: <s:property value="document" /></td>
<td><s:property value="name" /></td>
<td><s:property value="phone" /></td>
<td><s:property value="cellphone" /></td>
<td><s:property value="e_mail_1" /></td>
<td><s:property value="direction" /></td>
<td><s:property value="city" /></td>
<% if (valAsig.equals("producer") || valAsig == "producer") {%>
    <td>
        <div class="btn-group">
            <a href="#" class="btn btn-mini" title="Editar Productor" onclick="viewForm('/aeps-plataforma-mvn/showProducer.action?action=modify&page=<%=pageNow%>', 'idPro', <s:property value ="id_producer" />, 'Editar Productor', 1050, 550)"><i class="icon-pencil"></i></a>
            <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Productor" onclick="showDialogDelete(this, 'confirm_dialog_producer', 'deleteProducer.action?idPro=<s:property value ="id_producer" />', 'searchProducer.action?page=<%=pageNow%>', 'divProducers', '<%=divHide%>');"><i class="icon-trash"></i></a>
        </div>
    </td>
<% }%>
