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
    <s:property value="name_farm" />
    (<s:property value="name_producer" />)
</td>
<td><s:property value="dir_farm" /></td>
<td><s:property value="name_dep" /></td>
<td><s:property value="name_mun" /></td>
<td><s:property value="lane_farm" /></td>
<td><s:property value="latitude_farm" /></td>
<td><s:property value="length_farm" /></td>
<td><s:property value="altitude_farm" /></td>
<% if (value.equals("property") || value == "property") {%>
    <td>
        <div class="btn-group">
            <a href="#" class="btn btn-mini" title="Editar Finca" onclick="viewForm('/aeps-plataforma-mvn/showFarm.action?action=modify&page=<%=pageNow%>', 'idFar', <s:property value ="id_farm" />, 'Editar Finca', 1050, 550)"><i class="icon-pencil"></i></a>
            <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Finca" onclick="showDialogDelete(this, 'confirm_dialog_property', 'deleteFarm.action?idFar=<s:property value ="id_farm" />', 'searchFarm.action?page=<%=pageNow%>', 'divFarms', '<%=divHide%>');"><i class="icon-trash"></i></a>
        </div>
    </td>
<% }%>