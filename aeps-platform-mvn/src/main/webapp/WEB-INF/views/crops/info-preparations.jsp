<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String table = "display:none;";%>
<% String label = "";%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userPrp  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrPrpDao = new UsersDao(); %>
<% Integer entTypePrpId = new EntitiesDao().getEntityTypeId(userPrp.getIdUsr()); %>

<s:if test="listPrep.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>            

<div class="msgWin" id="divMessListPrep"></div>
<div id="divPrep" class="w-box">
    <fieldset>
        <legend>Lista de preparaciones</legend>
        <% if (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/create")) { %>
            <% if (entTypePrpId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showPrep.action?action=create', 'idCrop', '${idCrop}', 'Crear Preparación', 1050, 550);">
                    <i class="icon-plus"></i> Agregar Preparación
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= table %>" id='tblPrep'>
            <thead>
                <tr>
                    <th>Fecha de trabajo</th>
                    <th>Profundidad del trabajo (cm)</th>
                    <th>Tipo de preparación</th>
                    <th>Otro tipo de preparación</th>
                    <% if (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/modify") || (usrPrpDao.getPrivilegeUser(userPrp.getIdUsr(), "crop/delete"))) { %>
                        <th>Accion</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listPrep" var="prep">
                    <tr id="trPrep${idPrep}">
                        <%@ include file="row-prep.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= label%>"><%= "No se encuentra registrada ninguna preparación"%></label>
        <div class="hide">
            <div id="confirm_dialog_prep" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar esta preparación?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>