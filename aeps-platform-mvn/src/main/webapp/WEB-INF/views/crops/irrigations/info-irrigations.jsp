<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableIrr = "display:none;";%>
<% String labelIrr = "";%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userIrr  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrIrrDao = new UsersDao(); %>
<% Integer entTypeIrrId = new EntitiesDao().getEntityTypeId(userIrr.getIdUsr()); %>
<% String coCodeIrr   = (String) session.getAttribute(APConstants.COUNTRY_CODE); %>

<s:if test="listIrr.size() > 0">
    <% tableIrr = "";%>
    <% labelIrr = "display:none;";%> 
</s:if>            

<div class="msgWin" id="divMessListIrr"></div>
<div id="divIrr" class="w-box">
    <fieldset>
        <legend><s:property value="getText('title.irrigationlist.irrigation')" /></legend>
        <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/create")) { %>
            <% if (entTypeIrrId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showIrr.action?action=create', 'idCrop', '${idCrop}', '<s:property value="getText('title.addirrigation.irrigation')" />', 1050, 550);">
                    <i class="icon-plus"></i> <s:property value="getText('button.addirrigation.irrigation')" />
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableIrr %>" id='tblIrr'>
            <thead>
                <% if (coCodeIrr.equals("CO")) { %>
                    <tr>
                        <th><s:property value="getText('tr.irrigationdate.irrigation')" /></th>                    
                        <th><s:property value="getText('tr.amountirr.irrigation')" /></th>
                        <th><s:property value="getText('tr.irrtypes.irrigation')" /></th>
                        <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify") || (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete"))) { %>
                            <th><s:property value="getText('tr.action.irrigation')" /></th>
                        <% } %>
                    </tr>
                <% } else if (coCodeIrr.equals("NI")) { %>
                    <tr>
                        <th><s:property value="getText('tr.establishmentdate.irrigation')" /></th>                    
                        <th><s:property value="getText('tr.thickness.irrigation')" /></th>
                        <th><s:property value="getText('tr.wetdate.irrigation')" /></th>
                        <th><s:property value="getText('tr.duration.irrigation')" /></th>
                        <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify") || (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete"))) { %>
                            <th><s:property value="getText('tr.action.irrigation')" /></th>
                        <% } %>
                    </tr>
                <% } %>
            </thead>
            <tbody>
                <s:iterator value="listIrr" var="irr">
                    <tr id="trIrr${idIrr}">
                        <%@ include file="row-irrigations.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelIrr %>"><s:property value="getText('label.nofounddata.irrigation')" /></label>
        <div class="hide">
            <div id="confirm_dialog_irr" class="cbox_content">
                <div class="sepH_c"><strong><s:property value="getText('label.deleteirrigation.irrigation')" />?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                    <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
                </div>
            </div>
        </div>
    </fieldset>
</div>