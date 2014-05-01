<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableIrr = "display:none";%>
<% String labelIrr = "";%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userIrr  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrIrrDao = new UsersDao(); %>

<s:if test="listIrr.size() > 0">
    <% tableIrr = "";%>
    <% labelIrr = "display:none";%> 
</s:if>            

<div class="msgWin" id="divMessListIrr"></div>
<div id="divIrr" class="w-box">
    <fieldset>
        <legend>Lista de riegos</legend>
        <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/create")) { %>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/crop/showIrr.action?action=create', 'idCrop', '${idCrop}', 'Crear Riego', 1050, 550);">
                <i class="icon-plus"></i> Agregar Riego
            </button>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableIrr %>" id='tblIrr'>
            <thead>
                <tr>
                    <th>Aplica riego</th>
                    <th>Fecha del riego</th>                    
                    <th>Cantidad aportada por hectarea (mm/ha)</th>
                    <th>Tipo de riego</th>
                    <% if (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/modify") || (usrIrrDao.getPrivilegeUser(userIrr.getIdUsr(), "crop/delete"))) { %>
                        <th>Accion</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listIrr" var="irr">
                    <tr id="trIrr${idIrr}">
                        <%@ include file="row-irr.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelIrr %>"><%= "No se encuentra registrado ningun riego"%></label>
        <div class="hide">
            <div id="confirm_dialog_irr" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar este riego?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>