<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String table = "display:none";%>
<% String label = "";%>

<s:if test="listCrops.size() > 0">
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
<% divHide = "divConListCrops"; %>    

<div class="msgWin" id="messageWin"></div>
<div id="divCrops" class="w-box">
    <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/crop/showCrop.action?action=create', 'idCrop', '', 'Crear Evento Productivo', 1050, 700)">
        <i class="icon-plus"></i> Agregar Evento Productivo
    </button>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblCrops'>
        <thead>
            <tr>
                <th>Numero del Cultivo</th>
                <th>Nombre del productor</th>
                <th>Nombre de la finca</th>
                <th>Nombre del lote</th>
                <th>Fecha de siembra</th>
                <th>Material genetico</th>
                <% if (value == "crop" || value.equals("crop")) {%>
                    <th>Accion</th>
                <% }%>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listCrops" var="crop">
                <% String action = "";%>
                <% if (value != "crop") { %>
                <%--<s:if test="!value.equals('crop')">--%>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_crop") + "', '" + request.getAttribute("idCrop") + "');";%>
                    <!--<?php $action = "selectItem('".$additionals['valName']."', '".$additionals['valId']."', '".$crop['nombre']."', '".$crop['id_crop']."'); " ?>-->
                <%--</s:if>--%>
                <% } %>
                <s:if test="value.equals('crop')">
                    <% //action += "getInfo('/aeps-plataforma-mvn/cultivosAsociados.action?idProd=" + idProducer + "','idCrop','" + valId + "','divDataCrop','divMessage');";%>
                </s:if>
                <s:if test="value.equals('crop')">
                    <% //action += "getInfo('/aeps-plataforma-mvn/GetRastasXRasta.action','idCrop','" + valId + "','divDataCrops','divMessage');";%>
                </s:if>
                <tr onclick="<%= action%>" id="trCrop<s:property value="id_crop" />>">
                    <%@ include file="row-crops.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrado ningun evento productivo"%></label>
    <div class="hide">
        <div id="confirm_dialog_crop" class="cbox_content">
            <div class="sepH_c"><strong>Desea borrar este evento productivo?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                <a href="#" class="btn btn-small confirm_no">No</a>
            </div>
        </div>
    </div>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/crop/searchCrop.action?selected="+value, divHide, "", "", "formCropSearch");%>    
    <%= result%>
<div>