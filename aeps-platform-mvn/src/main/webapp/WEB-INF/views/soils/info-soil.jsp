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
<% Users userSoil  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrSoilDao = new UsersDao(); %>
<% Integer entTypeSoilId = new EntitiesDao().getEntityTypeId(userSoil.getIdUsr()); %>

<s:if test="listSoils.size() > 0">
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
<% divHide = "divConListRasta"; %>    

<div class="msgWin" id="messageWin"></div>
<div id="divRasta" class="w-box">
    <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/create")) { %>
        <% if (entTypeSoilId!=3) { %>
            <div class="btn btn-group btn-space" onclick="clickSelAll('chkSelectAll', 'chkNumber', 'btnDelSoil');">
                <input type="checkbox" class="chkSelectAll textFloat" />
                <label class="textFloat" style="padding-left: 7px; margin-bottom: 0;">Todos</label>
            </div>
            <button type="button" id="btnDelSoil" disabled="disabled" class="btn btn-initial btn-space btnGetAll disabled" onclick="showDialogDeleteAll(this, 'chkNumber', 'confirm_dialog_lot', 'deleteAllSoil.action', 'searchSoil.action?page=<%=pageNow%>', 'divRasta', '<%=divHide%>');">
                <i class="icon-trash"></i> Borrar selección
            </button>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/soil/showSoil.action?action=create', 'idRasta', '', 'Crear Rasta', 1050, 700)">
                <i class="icon-plus"></i> Agregar rasta
            </button>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblRasta'>
        <thead>
            <tr>
                <!-- <th>#</th> -->
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "rasta" || value.equals("rasta")) {%>
                        <th></th>
                    <% } %>
                <% } %>
                <% if (entTypeSoilId==3) { %>    
                    <th>Agronomo</th>
                <% } %>
                <th>Informacion</th>
                <th>Fecha</th>
                <th>Pendiente</th>
                <th>Ubicacion</th>
                <th>Terreno Circundante</th>
                <th>Posicion del terreno</th>
                <th>Numero de capas</th>
                <th>Ph</th>
                <th>Carbonatos</th>
                <th>Fecha de creación</th>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "rasta" || value.equals("rasta")) {%>
                        <th>Accion</th>
                    <% } %>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listSoils" var="lot">
                <% String action = "";%>
                <% if (value != "lot") { %>
                <%--<s:if test="!value.equals('lot')">--%>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("id_ras") + "');";%>
                    <!--<?php $action = "selectItem('".$additionals['valName']."', '".$additionals['valId']."', '".$lot['nombre']."', '".$lot['id_lote']."'); " ?>-->
                <%--</s:if>--%>
                <% } %>
                <s:if test="value.equals('crop')">
                    <% //action += "getInfo('/cultivosAsociados.action?idProd=" + idProducer + "','idRasta','" + valId + "','divDataCrop','divMessage');";%>
                </s:if>
                <s:if test="value.equals('rasta')">
                    <% //action += "getInfo('/GetRastasXRasta.action','idRasta','" + valId + "','divDataRastas','divMessage');";%>
                </s:if>
                <tr onclick="<%= action%>" id="trLot${id_ras}">
                    <%@ include file="row-soil.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrado ningun rasta"%></label>
    <div class="hide">
        <div id="confirm_dialog_lot" class="cbox_content">
            <div class="sepH_c"><strong>Desea borrar este rasta?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                <a href="#" class="btn btn-small confirm_no">No</a>
            </div>
        </div>
    </div>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/soil/searchSoil.action?selected="+value, divHide, "", "", "formRastaSearch");%>    
    <%= result%>
</div>