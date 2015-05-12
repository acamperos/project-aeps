<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Users userFar  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrFarDao = new UsersDao(); %>
<% Integer entTypeFarId = new EntitiesDao().getEntityTypeId(userFar.getIdUsr()); %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>
<% String table = "display:none;";%>
<% String label = "";%>

<s:if test="listProperties.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
<% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
<% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% //System.out.println("datos->"+request.getAttribute("listProperties"));   %>
<% String divShow = "";%>
<% String divHide = "";%>
<% if (value.equals("lot")) {
        divShow = "divFieldsForm";
        divHide = "divListFieldsForm";
    } else {
        divHide = "divConListFarms";
    }            
%>    
<div class="msgWin" id="messageWin"></div>
<div id="divFarms" class="w-box">
    <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/create")) { %>
        <% if (entTypeFarId!=3) { %>    
            <% if (value.equals("property")) {%>
                <div class="btn btn-group btn-space" onclick="clickSelAll('chkSelectAll', 'chkNumber', 'btnDelFar');">
                    <input type="checkbox" class="chkSelectAll textFloat" />
                    <label class="textFloat" style="padding-left: 7px; margin-bottom: 0;"><s:property value="getText('label.selectall.farm')" /></label>
                </div>
                <button type="button" id="btnDelFar" disabled="disabled" class="btn btn-initial btn-space btnGetAll disabled" onclick="showDialogDeleteAll(this, 'chkNumber', 'confirm_dialog_property', 'deleteAllFarm.action', 'searchFarm.action?page=<%=pageNow%>', 'divFarms', '<%=divHide%>');">
                    <i class="icon-trash"></i> <s:property value="getText('button.deletesel.farm')" />
                </button>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/showFarm.action?action=create&viewInfo=${viewInfo}', 'idFar', '', '<s:property value="getText('title.createfarm.farm')" />', 1050, 550)">
                    <i class="icon-plus"></i> <s:property value="getText('button.addfarm.farm')" />
                </button>
            <% } %>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table%> max-width: 100%" id='tblFarms'>
        <thead>
            <tr>
                <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify") || (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete"))) { %>
                    <% if (value.equals("property") || value == "property") { %>
                        <th></th>
                    <% }%>
                <% } %>
                <% if (value != "property") {%>
                    <% if (value.equals("lot") || value.equals("crop")) {%>
                        <th></th>
                    <% }%>
                <% }%>
                <% if (entTypeFarId==3) { %>    
                    <th><s:property value="getText('tr.agronomist.farm')" /></th>
                <% } %>
                <th><s:property value="getText('tr.namefarm.farm')" /></th>
                <th><s:property value="getText('tr.lane.farm')" /></th>
                <th><s:property value="getText('tr.direction.farm')" /></th>
                <th><s:property value="getText('tr.department.farm')" /></th>
                <th><s:property value="getText('tr.municipality.farm')" /></th>
                <th><s:property value="getText('tr.latitude.farm')" /></th>
                <th><s:property value="getText('tr.longitude.farm')" /></th>
                <th><s:property value="getText('tr.altitude.farm')" /></th>
                <th><s:property value="getText('tr.datecreated.farm')" /></th>                                
                <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify") || (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete"))) { %>
                    <% if (value.equals("property") || value == "property") { %>
                        <th><s:property value="getText('tr.action.farm')" /></th>
                    <% }%>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listProperties" var="property">
                <% String action = "";%>
                <% if (value != "property") {
                        if (value.equals("lot")) {
                            action = "selectItem('formField_name_property_lot', 'formField_idFarm', '" + request.getAttribute("name_farm") + "', '" + request.getAttribute("id_farm") + "', '" + divShow + "', '" + divHide + "')";
                        }
                        if (value.equals("crop")) {
                            action = "selectItem('formFarm_name_producer', 'formFarm_idProducer', '" + request.getAttribute("name_farm") + "', '" + request.getAttribute("id_farm")  + "', '" + divShow + "', '" + divHide + "')";
                        }
                   }
                %>       
                <tr id="trProperty<s:property value="id_farm" />" class="selectVal" onclick="<%= action%>">
                    <%@ include file="row-farm.jsp" %>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><s:property value="getText('label.nofounddata.farm')" /></label>
    <div class="hide">
        <div id="confirm_dialog_property" class="cbox_content">
            <div class="sepH_c"><s:text name="%{getText('area.deletefarm.farm')}" /></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
            </div>
        </div>
    </div>
</div>
<div>
    <% if (!value.equals("property")) {%>
        <button class="btn btn_per" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')"><i class="icon-arrow-left"></i> <s:property value="getText('button.backoption')" /></button>
    <% }%>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchFarm.action?selected="+value, "divConListFarms", "", "", "formFarmSearch");%>    
    <%= result%>
</div>
<s:div id="divProperties" />