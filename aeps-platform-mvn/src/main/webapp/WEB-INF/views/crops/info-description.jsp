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
        <legend>Lista de observaciones</legend>
        <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/create")) { %>
            <% if (entTypeDesId!=3) { %>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/crop/showDescrip.action?action=create', 'idCrop', '${idCrop}', 'Crear Observación', 1050, 550);">
                    <i class="icon-plus"></i> Agregar Observacion
                </button>
            <% } %>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableDes %>" id='tblDes'>
            <thead>
                <tr>
                    <th>Fecha de observacion</th>
                    <th>Observacion</th>
                    <% if (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/modify") || (usrDesDao.getPrivilegeUser(userDes.getIdUsr(), "crop/delete"))) { %>
                        <th>Accion</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listDesPro" var="des">
                    <tr id="trDes${idDes}">
                        <%@ include file="row-des.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelDes%>"><%= "No se encuentra registrada ninguna observacion"%></label>
        <div class="hide">
            <div id="confirm_dialog_des" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar esta observacion?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>