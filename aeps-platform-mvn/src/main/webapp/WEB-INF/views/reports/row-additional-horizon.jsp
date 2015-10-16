<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String val  = String.valueOf(request.getAttribute("numRows")); %>
<% int numRows = Integer.parseInt(val); %>
<% Integer valHorizon = 0; %>
<% if(numRows==0) { %>
    <% numRows = Integer.parseInt(String.valueOf(request.getParameter("numRows"))); %>
<% } %>
<% if(numRows==0) { %>
    <% valHorizon = numRows+1; %>
<% } else { %>
    <% valHorizon = numRows; %>
<% } %>
<% request.setAttribute("formDoc", "additionalsAtrib["+(numRows-1)+"]"); %>
<% request.setAttribute("formDocId", "additionalsAtrib_"+(numRows-1)); %>
<tr value="<%= numRows %>" id="RowAddit_<%= numRows %>">
    <td style="padding: 3px 0.5em 0px 30px;">
        <s:hidden name="%{#attr.formDoc}.idHorRas"/>
        <%= valHorizon %>
    </td>
    <td style="padding: 3px 0.5em;">
        <s:label cssClass="form-control" name="%{#attr.formDoc}.espesorHorRas" value="%{#attr.espesorHorRas}"/>
    </td>
    <td style="padding: 3px 0.5em;">
        <s:label cssClass="form-control" name="%{#attr.formDoc}.colorSecoHorRas" value="%{#attr.colorSecoHorRas}"/>
    </td>
    <td style="padding: 3px 0.5em;">
        <s:label cssClass="form-control" name="%{#attr.formDoc}.colorHumedoHorRas" value="%{#attr.colorHumedoHorRas}"/>
    </td>
    <td style="padding: 3px 0.5em;">
        <s:label
            cssClass="select"
            name="%{#attr.formDoc}.textures.idTex"
            value="%{#attr.textures.nameTex}" />
    </td>
    <td style="padding: 3px 0.5em;">
        <s:label
            cssClass="select"
            name="%{#attr.formDoc}.resistenciasRompimiento.idResRom"
            value="%{#attr.resistenciasRompimiento.nombreResRom}" />
    </td>    
</tr>