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

<s:if test="listSoils.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>            
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
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
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblRasta'>
        <thead>
            <tr>
                <th><s:property value="getText('tr.info.reportsoil')" /></th>
                <th><s:property value="getText('tr.datemake.reportsoil')" /></th>
                <th><s:property value="getText('tr.location.reportsoil')" /></th>
                <th><s:property value="getText('tr.terreno.reportsoil')" /></th>
                <th><s:property value="getText('tr.position.reportsoil')" /></th>
                <th<s:property value="getText('tr.numlayers.reportsoil')" /></th>
                <th><s:property value="getText('tr.datecreated.reportsoil')" /></th>
                <% if (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/modify") || (usrDao.getPrivilegeUser(user.getIdUsr(), "soil/delete"))) { %>                
                    <% if (value == "rasta" || value.equals("rasta")) {%>
                        <th><s:property value="getText('tr.reporttype.reportsoil')" /></th>
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
                <tr onclick="<%= action%>" id="trLot${id_ras}">
                    <%@ include file="row-soil.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><s:property value="getText('label.nofounddata.reportsoil')" /></label>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchSoilRep.action?selected="+value, divHide, "", "", "formRastaSearch");%>    
    <%= result%>
</div>