<td>
    # <s:property value="num_crop" />
</td>
<td><s:property value="name_producer" /></td>
<td>#<s:property value="num_farm" />-<s:property value="name_farm" /></td>
<td>#<s:property value="num_field" />-<s:property value="name_field" /></td>
<td><s:property value="date_sowing" /></td>
<td><s:property value="name_genotype" /></td>
<td>
    <div class="btn-group">
        <s:a href="/aeps-plataforma-mvn/crop/dataCrop.action?idCrop=%{param.id_crop}&typeCrop=%{param.typeCrop}&page=%{#pageNow}" class="btn btn-small" title="Ver Evento Productivo"><i class="icon-eye-open"></i></s:a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Evento Productivo" onclick="showDialogDelete(this, 'confirm_dialog_crop', 'deleteCrop.action?idCrop=<s:property value="id_crop" />', 'searchCrops.action?page=<%=pageNow%>', 'divCrops', '<%=divHide%>');"><i class="icon-trash"></i></a>
    </div>
</td>