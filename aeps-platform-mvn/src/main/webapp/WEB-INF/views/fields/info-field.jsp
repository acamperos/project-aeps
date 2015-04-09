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
                <div class="btn btn-group btn-space" onclick="clickSelAll('chkSelectAll', 'chkNumber', 'btnDelFie');">
                    <input type="checkbox" class="chkSelectAll textFloat" />
                    <label class="textFloat" style="padding-left: 7px; margin-bottom: 0;"><s:property value="getText('label.selectall.field')" /></label>
                </div>
                <button type="button" id="btnDelFie" disabled="disabled" class="btn btn-initial btn-space btnGetAll disabled" onclick="showDialogDeleteAll(this, 'chkNumber', 'confirm_dialog_lot', 'deleteAllField.action', 'searchField.action?page=<%=pageNow%>', 'divFields', '<%=divHide%>');">
                    <i class="icon-trash"></i> <s:property value="getText('button.deletesel.field')" />
                </button>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/showField.action?action=create&viewInfo=${viewInfo}', 'idField', '', '<s:property value="getText('title.createfield.field')" />', 1050, 550)">
                    <i class="icon-plus"></i> <s:property value="getText('button.addfarm.field')" />
                </button>
            <% } %>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table %>" id='tblFields'>
        <thead>
            <tr>
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
                    <% if (value == "lot" || value.equals("lot")) {%>
                        <th></th>
                    <% }%>
                <% } %>
                <% if (!value.equals("lot")) {%>
                    <% if (value.equals("crop") || value.equals("rasta")) {%>
                        <th></th>
                    <% }%>
                <% }%>
                <% if (entTypeFieId==3) { %>    
                    <th><s:property value="getText('tr.agronomist.field')" /></th>
                <% } %>
                <th><s:property value="getText('tr.namefield.field')" /></th>
                <th><s:property value="getText('tr.fieldtype.field')" /></th>
                <th><s:property value="getText('tr.area.field')" /></th>
                <th><s:property value="getText('tr.latitude.field')" /></th>
                <th><s:property value="getText('tr.longitude.field')" /></th>
                <th><s:property value="getText('tr.altitude.field')" /></th>
                <th><s:property value="getText('tr.datecreated.field')" /></th>
                <% if (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/modify") || (usrFieDao.getPrivilegeUser(userFie.getIdUsr(), "field/delete"))) { %>                
                    <% if (value == "lot" || value.equals("lot")) {%>
                        <th><s:property value="getText('tr.action.field')" /></th>
                    <% }%>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listLot" var="lot">
                <% String action = "";%>
                <% 
                    if (value.equals("crop")) { 
                        action = "selectItem('formCrop_nameField', 'formCrop_idField', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("id_lot") + "','" + divShow + "', '" + divHide + "');";
                    } 
                    if (value.equals("rasta")) {
                        action = "selectItem('formRasta_nameField', 'formRasta_idField', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("id_lot") + "', '" + divShow + "', '" + divHide + "')";
                    }                 
                %>
                <tr onclick="<%= action%>" class="selectVal" id="trLot${id_lot}">
                    <%@ include file="row-field.jsp" %>                                
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><s:property value="getText('label.nofounddata.farm')" /></label>
    <div class="hide">
        <div id="confirm_dialog_lot" class="cbox_content">
            <div class="sepH_c"><s:text name="%{getText('area.deletefarm.field')}" /></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
            </div>
        </div>
    </div>
</div>
<div>
    <% if (!value.equals("lot")) {%>
        <button class="btn btn_per" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')"><i class="icon-arrow-left"></i> <s:property value="getText('button.backoption')" /></button>
    <% }%>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchField.action?selected="+value+"&valId="+valId+"&valName="+valName, divHide, "", "", "formFieldSearch");%>    
    <%= result%>
</div>