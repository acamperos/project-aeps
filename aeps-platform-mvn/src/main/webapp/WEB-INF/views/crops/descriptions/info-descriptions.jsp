<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableDes = "display:none;";%>
<% String labelDes = "";%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userDes  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrDesDao = new UsersDao(); %>
<% Integer entTypeDesId = new EntitiesDao().getEntityTypeId(userDes.getIdUsr()); %>

<s:if test="listDesPro.size() > 0">
    <% tableDes = "";%>
    <% labelDes = "display:none;";%> 
</s:if>            

<div class="msgWin" id="divMessListDes"></div>
<div id="divDes" class="w-box">
    <fieldset>
        <legend><s:property value="getText('title.observationlist.observation')" /></legend>
        <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/create")) { %>
            <% if (entTypeDesId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showDescrip.action?action=create', 'idCrop', '${idCrop}', '<s:property value="getText('title.addobservation.observation')" />', 1050, 550);">
                    <i class="icon-plus"></i> <s:property value="getText('button.addobservation.observation')" />
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableDes %>" id='tblDes'>
            <thead>
                <tr>
                    <th><s:property value="getText('tr.dateobs.observation')" /></th>
                    <th><s:property value="getText('tr.obsdetail.observation')" /></th>
                    <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/modify") || (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/delete"))) { %>
                        <th><s:property value="getText('tr.action.observation')" /></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listDesPro" var="des">
                    <tr id="trDes${idDes}">
                        <%@ include file="row-descriptions.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelDes%>"><s:property value="getText('label.nofounddata.observation')" /></label>
        <div class="hide">
            <div id="confirm_dialog_des" class="cbox_content">
                <div class="sepH_c"><strong><s:property value="getText('label.deleteobs.observation')" />?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes"><s:property value="getText('link.optyes')" /></a>
                    <a href="#" class="btn btn-small confirm_no"><s:property value="getText('link.optno')" /></a>
                </div>
            </div>
        </div>
    </fieldset>
</div>