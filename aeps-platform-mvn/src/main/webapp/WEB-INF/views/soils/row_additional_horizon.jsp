<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String val  = String.valueOf(request.getAttribute("numRows")); %>
<% int numRows = Integer.parseInt(val); %>
<% if(numRows==0) { %>
    <% numRows = Integer.parseInt(String.valueOf(request.getParameter("numRows"))); %>
<% } %>
<% request.setAttribute("formDoc", "additionalsAtrib["+(numRows-1)+"]"); %>
<% request.setAttribute("formDocId", "formRasta_additionalsAtrib_"+(numRows-1)); %>
<% //int newRow = Integer.parseInt(String.valueOf(request.getAttribute("newRow"))); %>
<tr value="<%= numRows %>" id="RowAddit_<%= numRows %>">
    <td style="padding: 3px 0.5em;">
        <s:hidden name="%{#attr.formDoc}.idHorRas"/>
        <div class="">
            <div class="">
                <s:textfield cssClass="form-control write_tiny" name="%{#attr.formDoc}.numeroHorizonteHorRas" value="%{#attr.numeroHorizonteHorRas}"/>
            </div>
        </div>
    </td>
    <td style="padding: 3px 0.5em 0px 30px;">
        <div class="">
            <div class="">
                <s:textfield cssClass="form-control write_tiny" name="%{#attr.formDoc}.espesorHorRas" value="%{#attr.espesorHorRas}"/>
            </div>
        </div>
    </td>
    <td style="padding: 3px 0.5em;">
        <div class="">
            <div class="">
                <s:textfield cssClass="form-control write_tiny" name="%{#attr.formDoc}.colorSecoHorRas" value="%{#attr.colorSecoHorRas}"/>
            </div>
        </div>
    </td>
    <td style="padding: 3px 0.5em;">
        <div class="">
            <div class="">
                <s:textfield cssClass="form-control write_tiny" name="%{#attr.formDoc}.colorHumedoHorRas" value="%{#attr.colorHumedoHorRas}"/>
            </div>
        </div>
    </td>
    <td style="padding: 3px 0.5em;">
        <div class="">
            <div class="">
                <s:select
                    cssClass="select"
                    name="%{#attr.formDoc}.textures.idTex"
                    value="%{#attr.textures.idTex}"
                    list="#{'1':'Arenoso (A)', '2':'Limoso (L)', '3':'Franco Limoso (FL)', '4':'Franco (F)', '5':'Franco Arenoso (FA)', '6':'Areno Franco (AF)', '7':'Franco Arcilloso (FAr)', '8':'Arcillo Arenoso (ArA)', '9':'Arcillo Limoso (ArL)', '10':'Arcilloso (Ar)'}"           
                    headerKey="-1" 
                    headerValue="---" />
            </div>
        </div>
    </td>
    <td style="padding: 3px 0.5em;">
        <div class="">
            <div class="">
                <s:select
                    cssClass="select"
                    name="%{#attr.formDoc}.resistenciasRompimiento.idResRom"
                    value="%{#attr.resistenciasRompimiento.idResRom}"
                    list="#{'1':'Friable', '2':'Firme', '3':'Extremadamente firme', '4':'Blando', '5':'Duro', '6':'Extremadamente duro', '7':'Plástico', '8':'Muy plástico'}"           
                    headerKey="-1" 
                    headerValue="---" />
            </div>
        </div>
    </td>                
    <td style="vertical-align: bottom ! important; padding: 0 0.5em;">
        <a class="btn btn-small delete_rows_dt" title="Quitar" style="margin-bottom:1.2em" onclick="$('#RowAddit_<%= numRows %>').remove();"><i class="icon-trash"></i></a>
    </td>
    
    <script>			
        var formId = '<%= request.getAttribute("formDocId") %>';
        $("#"+formId+"__numeroHorizonteHorRas").numeric({ decimal: false, negative: false });
        $("#"+formId+"__espesorHorRas").numeric({ negative: false });
        $("#"+formId+"__espesorHorRas").val(parsePointSeparated($("#"+formId+"__espesorHorRas").val()));
        $("#"+formId+"__colorSecoHorRas").numeric({ decimal: false, negative: false });
        $("#"+formId+"__colorHumedoHorRas").numeric({ decimal: false, negative: false });
    </script>
    <% //if (newRow==1) { %>
<!--        <td style="vertical-align: bottom ! important; padding: 0 0.5em;">
            <a class="btn btn-small delete_rows_dt" title="Quitar" style="margin-bottom:1.2em" onclick="$('#RowAddit_<%= numRows %>').remove();"><i class="icon-trash"></i></a>
        </td>-->
    <% //} else { %>
<!--        <td style="vertical-align: bottom ! important; padding: 0 0.5em;">     
            <a href="#" class="btn btn-small delete_rows_dt" title="Eliminar" onclick="showDialogDeleteSoil(this, 'confirm_dialog_producer', 'deleteHorizon.action?idPro=${param.id_producer}', 'RowAddit_<%= numRows %>', 'divMessage');"><i class="icon-trash"></i></a>
        </td>-->
    <% //} %>
</tr>