<s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
<td><s:property value="%{#dateTransformRowFer}" /></td>
<td><s:property value="amountUsed" /></td>
<s:set name="useFerTyp" value="%{infoAme.get('idFerTypAme')}"/>
<s:if test="%{#useFerTyp!=null}">
    <td><s:property value="%{infoAme.get('nameFerTypAme')}" /></td>
</s:if> 
<s:elseif test="%{#useFerTyp==null}">
    <td><s:property value="%{infoAme.get('otherProductAme')}" /></td>
</s:elseif>                        
<td>
    <div class="btn-group">
        <a href="#" class="btn btn-small" title="Editar Fertilizacion de Enmiendas" onclick="viewForm('/aeps-plataforma-mvn/crop/showFer.action?action=modify&idCrop=${idCrop}', 'idFer', ${idFer}, 'Editar Fertilizacion de Enmiendas', 1050, 550);"><i class="icon-pencil"></i></a>
        <a href="#" class="btn btn-small delete_rows_dt" title="Borrar Fertilizacion de Enmiendas" onclick="showDialogDelete(this, 'confirm_dialog_ferche', 'deleteFer.action?idFer=${idFer}', 'searchFer.action?idCrop=${idCrop}', 'divFerAme', 'divListFer');"><i class="icon-trash"></i></a>
    </div>
</td>