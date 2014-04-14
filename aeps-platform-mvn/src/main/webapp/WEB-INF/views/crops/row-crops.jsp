<td>
    #<s:property value="num_crop" />
</td>
<td>
    <s:property value="type_doc" />:<s:property value="num_doc" />
</td>
<td>
    <s:property value="name_producer" /><br />
    #<s:property value="num_farm" />-<s:property value="name_farm" /><br />
    #<s:property value="num_field" />-<s:property value="name_field" /><br />
</td>
<td><s:property value="date_sowing" /></td>
<td><s:property value="name_genotype" /></td>
<td>
    <div class="btn-group">
        <a href="/aeps-plataforma-mvn/crop/dataCrop.action?idCrop=${idCrop}&page=<%=pageNow%>" class="btn btn-small" title="Ver Evento Productivo">Ver info<i class="icon-eye-open"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Evento Productivo" onclick="showDialogDelete(this, 'confirm_dialog_crop', 'deleteCrop.action?idCrop=<s:property value="idCrop" />', 'searchCrops.action?page=<%=pageNow%>', 'divCrops', '<%=divHide%>');"><i class="icon-trash"></i></a>
    </div>
</td>