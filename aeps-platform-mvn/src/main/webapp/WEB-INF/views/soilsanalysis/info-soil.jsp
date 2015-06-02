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
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% String typeEnt = String.valueOf(request.getAttribute("typeEnt"));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String divShow = "";%>
<% String divHide = "";%>
<% divHide = "divConListSoil"; %>    

<div class="msgWin" id="messageWin"></div>
<div id="divSoil" class="w-box">
    <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/create")) { %>
        <% if (entTypeSoilId!=3) { %>
            <div class="btn btn-group btn-space" onclick="clickSelAll('chkSelectAll', 'chkNumber', 'btnDelSoil');">
                <input type="checkbox" class="chkSelectAll textFloat" />
                <label class="textFloat" style="padding-left: 7px; margin-bottom: 0;"><s:property value="getText('label.selectall.soilanalysis')" /></label>
            </div>
            <button type="button" id="btnDelSoil" disabled="disabled" class="btn btn-initial btn-space btnGetAll disabled" onclick="showDialogDeleteAll(this, 'chkNumber', 'confirm_dialog_soil', '/soilchemical/deleteAllSoilChemical.action', '/soilchemical/searchSoilChemical.action?page=<%=pageNow%>', 'divSoil', '<%=divHide%>');">
                <i class="icon-trash"></i> <s:property value="getText('button.deletesel.soilanalysis')" />
            </button>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/soilchemical/showSoilChemical.action?action=create', 'idSoil', '', '<s:property value="getText('title.createsoil.soilanalysis')" />', 1050, 700)">
                <i class="icon-plus"></i> <s:property value="getText('button.addsoil.soilanalysis')" />
            </button>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblSoil'>
        <thead>
            <tr>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "soil" || value.equals("soil")) {%>
                        <th></th>
                    <% } %>
                <% } %>
                <% if (entTypeSoilId==3) { %>    
                    <th><s:property value="getText('tr.agronomist.soilanalysis')" /></th>
                <% } %>
                <th><s:property value="getText('tr.infosoil.soilanalysis')" /></th>
                <th><s:property value="getText('tr.datesoil.soilanalysis')" /></th>
                <th><s:property value="getText('tr.numbersample.soilanalysis')" /></th>
                <th><s:property value="getText('tr.crop.soilanalysis')" /></th>
                <th><s:property value="getText('tr.texture.soilanalysis')" /></th>
                <th><s:property value="getText('tr.phvalue.soilanalysis')" /></th>
                <th><s:property value="getText('tr.cicesoil.soilanalysis')" /></th>
                <th><s:property value="getText('tr.datecreated.soilanalysis')" /></th>
                <% if (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/modify") || (usrSoilDao.getPrivilegeUser(userSoil.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "soil" || value.equals("soil")) {%>
                        <th><s:property value="getText('tr.action.soilanalysis')" /></th>
                    <% } %>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listSoils" var="soil">
                <% String action = "";%>
                <tr onclick="<%= action%>" id="trSoil${id_soil}">
                    <%@ include file="row-soil.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><s:property value="getText('label.nofounddata.soilanalysis')" /></label>
    <div class="hide">
        <div id="confirm_dialog_soil" class="cbox_content">
            <div class="sepH_c"><strong><s:property value="getText('label.deletesoil.soilanalysis')" />?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
            </div>
        </div>
    </div>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/soilchemical/searchSoilChemical.action?selected="+value, divHide, "", "", "formSoilSearch");%>    
    <%= result%>
</div>