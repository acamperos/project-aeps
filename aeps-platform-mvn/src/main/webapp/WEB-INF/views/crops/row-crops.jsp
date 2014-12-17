<% if (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/modify") || (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/delete"))) { %>
    <% if (value == "crop" || value.equals("crop")) {%>
        <td>
            <input type="checkbox" class="chkNumber" value="${idCrop}" onclick="clickSelOne('chkSelectAll', 'chkNumber', 'btnDelCrop')"/>
        </td>
    <% } %>
<% } %>
<% if (entTypeCropId==3) { %>    
    <td><s:property value="nameAgro" /></td>
<% } %>
<td>
    Lote: #<s:property value="num_field" />-<s:property value="name_field" />,<br />
    Finca: #<s:property value="num_farm" />-<s:property value="name_farm" />
</td>
<% if (!typeEnt.equals("2")) { %>
    <td>
        Productor: <s:property value="name_producer" />
        <s:property value="type_doc" />:<s:property value="num_doc" />
    </td>
<% } %>
<td>
    <s:property value="nameCrop" />
</td>
<td>
    #<s:property value="num_crop" />
</td>
<s:date name="date_sowing" format="dd/MM/yyyy" var="dateTransformSow"/>
<td><s:property value="%{#dateTransformSow}" /></td>
<td><s:property value="name_genotype" /></td>
<s:date name="dateLog" format="dd/MM/yyyy" var="dateLog"/>
<td><s:property value="%{#dateLog}" /></td>
<% if (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/modify") || (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/delete"))) { %>
    <% if (value == "crop" || value.equals("crop")) {%>
        <td>
            <div class="btn-group">
                <% if (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/modify")) { %>
                    <a href="/crop/dataCrop.action?idCrop=${idCrop}&page=<%=pageNow%>" class="btn btn-small" title="Ver Evento Productivo">Actualizar información <i class="icon-eye-open"></i></a>
                <% } %>
                <% if (usrCropDao.getPrivilegeUser(userCrop.getIdUsr(), "crop/delete")) { %>
                    <a class="btn btn-small delete_rows_dt btn-delete" title="Borrar Evento Productivo" onclick="showDialogDelete(this, 'confirm_dialog_crop', 'deleteCrop.action?idCrop=<s:property value="idCrop" />', 'searchCrop.action?page=<%=pageNow%>', 'divCrops', '<%=divHide%>'); ga('send', 'event', 'Crops', 'click', 'Delete');"><i class="icon-trash"></i></a>
                <% } %>
            </div>
        </td>
    <% } %>
<% } %>