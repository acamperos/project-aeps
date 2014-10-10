<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String table = "display:none;";%>
<% String label = "";%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Users userFie  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrFieDao = new UsersDao(); %>
<% Integer entTypeFieId = new EntitiesDao().getEntityTypeId(userFie.getIdUsr()); %>

<s:if test="listLot.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>            
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
<% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
<% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
<% int idProducer = Integer.parseInt(String.valueOf(request.getAttribute("idProducer")));%>
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% String typeEnt = String.valueOf(request.getAttribute("typeEnt"));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String divShow = "";%>
<% String divHide = "";%>
<%  if (value.equals("crop")) {
        divShow = "divCropForm";
        divHide = "divListCropForm";
    } else if (value.equals("rasta")) {
        divShow = "divRastaForm";
        divHide = "divListRastaForm";
    } else {
        divHide = "divConListFields";
    }            
%>    

<div class="msgWin" id="messageWin"></div>
<div id="divFields" class="w-box">
    <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/create")) { %>   
        <% if (entTypeFieId!=3) { %>
            <% if (value.equals("lot")) {%>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/showField.action?action=create&viewInfo=${viewInfo}', 'idField', '', 'Crear Lote', 1050, 550)">
                    <i class="icon-plus"></i> Agregar lote
                </button>
            <% } %>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblFields'>
        <thead>
            <tr>
                <% if (!value.equals("lot")) {%>
                    <% if (value.equals("crop") || value.equals("rasta")) {%>
                        <th></th>
                    <% }%>
                <% }%>
                <!-- <th>#</th> -->
                <% if (entTypeFieId==3) { %>    
                    <th>Agronomo</th>
                <% } %>
                <th>Nombre</th>
                <th>Tipo lote</th>
                <th>Area</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Altura</th>
                <th>Municipio</th>
                <th>Fecha de creación</th>
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
                    <% if (value == "lot" || value.equals("lot")) {%>
                        <th>Accion</th>
                    <% }%>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listLot" var="lot">
                <% String action = "";%>
                <% if (value != "lot") { %>
                <%--<s:if test="!value.equals('lot')">--%>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("id_lot") + "','" + divShow + "', '" + divHide + "');";%>
                    <!--<?php $action = "selectItem('".$additionals['valName']."', '".$additionals['valId']."', '".$lot['nombre']."', '".$lot['id_lote']."'); " ?>-->
                <%--</s:if>--%>
                <% } %>
                <s:if test="%{value.equals('crop')}">
                    <% //action += "getInfo('/cultivosAsociados.action?idProd=" + idProducer + "','idField','" + valId + "','divDataCrop','divMessage');";%>
                </s:if>
                <s:if test="%{value.equals('rasta')}">
                    <% action = "selectItem('formRasta_nameField', 'formField_idField', '" + request.getAttribute("name_far") + "', '" + request.getAttribute("id_lot") + "', '" + divShow + "', '" + divHide + "')"; %>
                    <% //action += "getInfo('/GetRastasXField.action','idField','" + valId + "','divDataRastas','divMessage');";%>
                </s:if>
                <tr onclick="<%= action%>" class="selectVal" id="trLot<s:property value="id_lot" />>">
                    <%@ include file="row-field.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrado ningun lote"%></label>
    <div class="hide">
        <div id="confirm_dialog_lot" class="cbox_content">
            <div class="sepH_c"><strong>Desea borrar este(s) lote(s), <br>
                    al momento de confirmar todas las dependencias también van a desaparecer?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                <a href="#" class="btn btn-small confirm_no">No</a>
            </div>
        </div>
    </div>
</div>
<div>
    <% if (!value.equals("lot")) {%>
        <button class="btn btn_per" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')"><i class="icon-arrow-left"></i> Atras</button>
    <% }%>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchField.action?selected="+value+"&valId="+valId+"&valName="+valName, divHide, "", "", "formFieldSearch");%>    
    <%= result%>
</div>