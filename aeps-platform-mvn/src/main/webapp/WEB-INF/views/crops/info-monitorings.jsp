<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableMon = "display:none";%>
<% String labelMon = "";%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userMon  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrMonDao = new UsersDao(); %>

<s:if test="listMont.size() > 0">
    <% tableMon = "";%>
    <% labelMon = "display:none";%> 
</s:if>            

<div class="msgWin" id="divMessListMon"></div>
<div id="divMon" class="w-box">
    <fieldset>
        <legend>Lista de monitoreos</legend>
        <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/create")) { %>
            <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/crop/showMon.action?action=create', 'idCrop', '${idCrop}', 'Crear Monitoreo', 1050, 550);">
                <i class="icon-plus"></i> Agregar Monitoreo
            </button>
        <% } %>
        <table class="table table-bordered table-hover" style="<%= tableMon %>" id='tblMon'>
            <thead>
                <tr>             
                    <th>Fecha de monitoreo</th>
                    <th>Monitorea Plaga</th>                    
                    <th>Monitorea Enfermedad</th>
                    <th>Monitorea Maleza</th>
                    <% if (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/modify") || (usrMonDao.getPrivilegeUser(userMon.getIdUsr(), "crop/delete"))) { %>
                        <th>Accion</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="listMont" var="Mon">
                    <tr id="trMon${idMon}">
                        <%@ include file="row-mon.jsp" %>                                
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <label style="<%= labelMon %>"><%= "No se encuentra registrado ningun monitoreo"%></label>
        <div class="hide">
            <div id="confirm_dialog_mon" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar este monitoreo?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>