<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String table = "display:none";%>
<% String label = "";%>

<s:if test="listSoils.size() > 0">
    <% table = "";%>
    <% label = "display:none";%> 
</s:if>            
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
<% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
<% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
<% int idProducer = Integer.parseInt(String.valueOf(request.getAttribute("idProducer")));%>
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String divShow = "";%>
<% String divHide = "";%>
<% divHide = "divConListRasta"; %>    

<div class="msgWin" id="messageWin"></div>
<div id="divRasta" class="w-box">
    <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/soil/showSoil.action?action=create', 'idRasta', '', 'Crear Rasta', 1050, 700)">
        <i class="icon-plus"></i> Agregar rasta
    </button>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblRasta'>
        <thead>
            <tr>
                <!-- <th>#</th> -->
                <th>Numero</th>
                <th>Fecha</th>
                <th>Pendiente</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Altura</th>
                <th>Terreno Circundante</th>
                <th>Posicion del terreno</th>
                <th>Numero de capas</th>
                <th>Ph</th>
                <th>Carbonatos</th>
                <% if (value == "rasta" || value.equals("rasta")) {%>
                    <th>Accion</th>
                <% }%>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listSoils" var="lot">
                <% String action = "";%>
                <% if (value != "lot") { %>
                <%--<s:if test="!value.equals('lot')">--%>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("idRasta") + "');";%>
                    <!--<?php $action = "selectItem('".$additionals['valName']."', '".$additionals['valId']."', '".$lot['nombre']."', '".$lot['id_lote']."'); " ?>-->
                <%--</s:if>--%>
                <% } %>
                <s:if test="value.equals('crop')">
                    <% //action += "getInfo('/aeps-plataforma-mvn/cultivosAsociados.action?idProd=" + idProducer + "','idRasta','" + valId + "','divDataCrop','divMessage');";%>
                </s:if>
                <s:if test="value.equals('rasta')">
                    <% //action += "getInfo('/aeps-plataforma-mvn/GetRastasXRasta.action','idRasta','" + valId + "','divDataRastas','divMessage');";%>
                </s:if>
                <tr onclick="<%= action%>" id="trLot<s:property value="id_lot" />>">
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
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/soil/searchRasta.action?selected="+value, divHide, "", "", "formRastaSearch");%>    
    <%= result%>
<div>