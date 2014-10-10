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
<% Users user  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDao = new UsersDao(); %>

<s:if test="listCrops.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>            
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
<% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% String typeEnt = String.valueOf(request.getAttribute("typeEnt"));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String divShow = "";%>
<% String divHide = "";%>
<% divHide = "divConListCrop"; %>    

<div class="msgWin" id="messageWin"></div>
<div id="divCrops" class="w-box">    
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblCrops'>
        <thead>
            <tr>
                <th>Informacion</th>                
                <s:if test="%{typeEnt!=2}">
                    <th>Nombre del Productor</th>
                </s:if>
                <th>Que cultivo es</th>
                <th>Numero del Cultivo</th>
                <th>Fecha de siembra</th>
                <th>Fecha de cosecha</th>
                <th>Material genetico</th>
                <th>Fecha de creación</th>
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "crop/delete"))) { %>
                    <% if (value == "crop" || value.equals("crop")) {%>
                        <th>Reporte</th>
                    <% }%>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listCrops" var="crop">
                <% String action = "";%>
                <% if (value != "crop") { %>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_crop") + "', '" + request.getAttribute("idCrop") + "');";%>
                <% } %>
                <tr onclick="<%= action%>" id="trCrop${id_crop}>">
                    <%@ include file="row-crops.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrado ningun evento productivo"%></label>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchCropRep.action?selected="+value, divHide, "", "", "formCropSearch");%>    
    <%= result%>
</div>