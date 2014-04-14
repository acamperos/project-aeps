<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<div class="panel">
    <div class="panel-body">
        <s:if test="listCrops.size() > 0">
            <% //table = "";%>
            <% //label = "display:none";%> 
        </s:if>                    
    <!--                        Informacion del Cultivo Maiz # 350

        Nombre del Productor: Juan Felipe Rodriguez 
        Cedula del Productor: 1130668332
        Nombre de la finca: La Poderosa
        Nombre del Lote: Esto es lo mio
        Objetivo de rendimiento (kg/ha):
        Cultivo anterior:
        Se hace drenaje en la parcela: 
        Cultivo: Maiz # 350 (Omitirlo)
        (Va a tocar modificarlos :( )-->
        <fieldset>
            <legend>Informacion del cultivo <s:property value="nameTypeCrop" /> # <s:property value="idCrop" /></legend>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <th style="width: 30%">Nombre del Productor</th>
                        <td><s:property value="name_producer" /></td>
                    </tr>
                    <tr>
                        <th>Documento del Productor</th>
                        <td><s:property value="type_doc" />: <s:property value="num_doc" /></td>
                    </tr>
                    <tr>
                        <th>Nombre de la finca</th>
                        <td><s:property value="name_farm" /></td>
                    </tr>
                    <tr>   
                        <th>Nombre del Lote</th>
                        <td><s:property value="nameField" /></td>
                    </tr>
                    <tr>
                        <th>Objetivo de rendimiento (kg/ha)</th>
                        <td><s:property value="performObj" /></td>
                    </tr>
                    <tr>
                        <th>Cultivo anterior</th>
                        <td><s:property value="lastTypeCrop" /></td>
                    </tr>
                    <tr>
                        <th>Se hace drenaje en la parcela</th>
                        <td><s:property value="nameDrainPlot" /></td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </div>       
</div>
<button type="button" class="btn btn-initial" onclick="viewForm('/aeps-plataforma-mvn/crop/showCrop.action?action=modify', 'idCrop', '${idCrop}', 'Modificar Evento Productivo', 1050, 700)">
    <i class="icon-pencil"></i> Editar Evento Productivo
</button>