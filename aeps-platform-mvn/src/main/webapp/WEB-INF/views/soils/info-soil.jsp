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
                <label class="textFloat" style="padding-left: 7px; margin-bottom: 0;"><s:property value="getText('label.selectall.soil')" /></label>
            </div>
            <button type="button" id="btnDelSoil" disabled="disabled" class="btn btn-initial btn-space btnGetAll disabled" onclick="showDialogDeleteAll(this, 'chkNumber', 'confirm_dialog_lot', '/soil/deleteAllSoil.action', '/soil/searchSoil.action?page=<%=pageNow%>', 'divRasta', '<%=divHide%>');">
                <i class="icon-trash"></i> <s:property value="getText('button.deletesel.soil')" />
            </button>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/soil/showSoil.action?action=create', 'idRasta', '', '<s:property value="getText('title.createsoil.soil')" />', 1050, 700)">
                <i class="icon-plus"></i> <s:property value="getText('button.addsoil.soil')" />
            </button>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblRasta'>
        <thead>
            <tr>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "rasta" || value.equals("rasta")) {%>
                        <th></th>
                    <% } %>
                <% } %>
                <% if (entTypeSoilId==3) { %>    
                    <th><s:property value="getText('tr.agronomist.soil')" /></th>
                <% } %>
                <th><s:property value="getText('tr.infosoil.soil')" /></th>
                <th><s:property value="getText('tr.daterasta.soil')" /></th>
                <th><s:property value="getText('tr.pendant.soil')" /></th>
                <th><s:property value="getText('tr.location.soil')" /></th>
                <th><s:property value="getText('tr.ground.soil')" /></th>
                <th><s:property value="getText('tr.position.soil')" /></th>
                <th><s:property value="getText('tr.numlayers.soil')" /></th>
                <th><s:property value="getText('tr.phvalue.soil')" /></th>
                <th><s:property value="getText('tr.carbonates.soil')" /></th>
                <th><s:property value="getText('tr.datecreated.soil')" /></th>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "rasta" || value.equals("rasta")) {%>
                        <th><s:property value="getText('tr.action.soil')" /></th>
                    <% } %>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listSoils" var="lot">
                <% String action = "";%>
                <% if (value != "lot") { %>
                    <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("id_ras") + "');";%>
                <% } %>
                <s:if test="value.equals('crop')">
                </s:if>
                <tr onclick="<%= action%>" id="trLot${id_ras}">
                    <%@ include file="row-soil.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><s:property value="getText('label.nofounddata.soil')" /></label>
    <div class="hide">
        <div id="confirm_dialog_lot" class="cbox_content">
            <div class="sepH_c"><strong><s:property value="getText('label.deleterasta.soil')" />?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
            </div>
        </div>
    </div>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/soil/searchSoil.action?selected="+value, divHide, "", "", "formRastaSearch");%>    
    <%= result%>
</div>