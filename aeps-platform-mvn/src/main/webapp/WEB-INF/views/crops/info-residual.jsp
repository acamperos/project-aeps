<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableRes = "display:none";%>
<% String labelRes = "";%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userRes  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrResDao = new UsersDao(); %>

<s:if test="listResMan.size() > 0">
    <% tableRes = "";%>
    <% labelRes = "display:none";%> 
</s:if>            

<div class="msgWin" id="divMessListRes"></div>
<div id="divRes" class="w-box">
    <fieldset>
        <legend>Lista de rastrojos</legend>
        <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/create")) { %>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showResidual.action?action=create', 'idCrop', '${idCrop}', 'Crear Rastrojo', 1050, 550);">
                <i class="icon-plus"></i> Agregar Manejo de Rastrojos
            </button>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableRes %>" id='tblRes'>
            <thead>
                <tr>
                    <th>Fecha del rastrojo</th>
                    <th>Manejo de rastrojos</th>
                    <th>Otro manejo de rastrojos</th>
                    <% if (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/modify") || (usrResDao.getPrivilegeUser(userRes.getIdUsr(), "crop/delete"))) { %>
                        <th>Accion</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listResMan" var="res">
                    <tr id="trRes${idRes}">
                        <%@ include file="row-res.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelRes%>"><%= "No se encuentra registrada ningun rastrojo"%></label>
        <div class="hide">
            <div id="confirm_dialog_res" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar este rastrojo?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>