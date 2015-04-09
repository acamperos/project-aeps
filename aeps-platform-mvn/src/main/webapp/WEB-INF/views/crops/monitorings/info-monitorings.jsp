<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableMon = "display:none;";%>
<% String labelMon = "";%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userMon  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrMonDao = new UsersDao(); %>
<% Integer entTypeMonId = new EntitiesDao().getEntityTypeId(userMon.getIdUsr()); %>

<s:if test="listMont.size() > 0">
    <% tableMon = "";%>
    <% labelMon = "display:none;";%> 
</s:if>            

<div class="msgWin" id="divMessListMon"></div>
<div id="divMon" class="w-box">
    <fieldset>
        <legend><s:property value="getText('title.monitoringlist.monitoring')" /></legend>
        <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/create")) { %>
            <% if (entTypeMonId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showMon.action?action=create', 'idCrop', '${idCrop}', '<s:property value="getText('title.addmonitoring.monitoring')" />', 1050, 550);">
                    <i class="icon-plus"></i> <s:property value="getText('button.addmonitoring.monitoring')" />
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableMon %>" id='tblMon'>
            <thead>
                <tr>             
                    <th><s:property value="getText('tr.datemonitoring.monitoring')" /></th>
                    <th><s:property value="getText('tr.monitoringpest.monitoring')" /></th>                    
                    <th><s:property value="getText('tr.monitoringdisease.monitoring')" /></th>
                    <th><s:property value="getText('tr.monitoringweed.monitoring')" /></th>
                    <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify") || (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete"))) { %>
                        <th><s:property value="getText('tr.action.monitoring')" /></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listMont" var="Mon">
                    <tr id="trMon${idMon}">
                        <%@ include file="row-monitorings.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelMon %>"><s:property value="getText('label.nofounddata.monitoring')" /></label>
        <div class="hide">
            <div id="confirm_dialog_mon" class="cbox_content">
                <div class="sepH_c"><strong><s:property value="getText('label.deletemonitoring.monitoring')" />?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                    <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
                </div>
            </div>
        </div>
    </fieldset>
</div>