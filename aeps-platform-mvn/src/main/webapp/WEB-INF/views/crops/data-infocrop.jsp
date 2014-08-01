<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<div class="panel">
    <div class="panel-body w-box">
        <s:if test="listCrops.size() > 0">
            <% //table = "";%>
            <% //label = "display:none";%> 
        </s:if>                    
        <fieldset>
            <legend><h3>Informacion del cultivo 
                <s:if test="%{typeCrop==1}">
                    <s:property value="nameTypeCrop" /> Mecanizado # <s:property value="idCrop" /></h3>
                </s:if>
                <s:else>
                    <s:property value="nameTypeCrop" /> # <s:property value="idCrop" /></h3>
                </s:else>
                    
            </legend>
            <table class="table table-bordered">
                <tbody>
                    <s:if test="%{typeEnt!=2}">
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
                    </s:if>
                    <s:else>
                        <tr>
                            <th style="width: 30%">Nombre de la finca</th>
                            <td><s:property value="name_farm" /></td>
                        </tr>
                    </s:else>    
                    <tr>   
                        <th>Nombre del Lote</th>
                        <td><s:property value="nameField" /></td>
                    </tr>
                    <tr>
                        <th>Cultivo anterior</th>
                        <td><s:property value="lastTypeCrop" /></td>
                    </tr>
<!--                    <tr>
                        <th>Se hace drenaje en la parcela</th>
                        <td><s:property value="nameDrainPlot" /></td>
                    </tr>-->
                </tbody>
            </table>
        </fieldset>
    </div>       
</div>
<% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify")) { %>
    <button type="button" class="btn btn-initial" onclick="viewForm('/crop/showCrop.action?action=modify', 'idCrop', '${idCrop}', 'Modificar Evento Productivo', 1050, 700)">
        <i class="icon-pencil"></i> Editar Evento Productivo
    </button>
<% } %>
<button type="button" class="btn btn-default" onclick="document.location='/crop/listCrop.action';">
    <i class="icon-arrow-left"></i> Volver al listado de cultivos
</button>