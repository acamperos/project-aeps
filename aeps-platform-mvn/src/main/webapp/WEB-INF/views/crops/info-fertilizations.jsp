<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String tableFerChe = "display:none";%>
<% String labelFerChe = "";%>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<% Users userFer  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrFerDao = new UsersDao(); %>

<s:if test="listFert.get(0).get('chemical').size() > 0">
    <% tableFerChe = "";%>
    <% labelFerChe = "display:none";%> 
</s:if>            

<div class="msgWin" id="divFer"></div>
<fieldset>
    <legend><h3>Lista de fertilizaciones</h3></legend>     
    <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/create")) { %>
        <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/crop/showFer.action?action=create', 'idCrop', '${idCrop}', 'Crear Fertilizacion', 1050, 550);">
            <i class="icon-plus"></i> Agregar Fertilizacion
        </button>
    <% } %>
    <div id="divFerChe" class="w-box">
        <fieldset>
            <legend>Lista de fertilizaciones quimicas</legend>        
            <table class="table table-bordered table-hover" style="<%= tableFerChe %>" id='tblFerChe'>
                <thead>
                    <tr>
                        <th>Fecha de la aplicación</th>
                        <th>Cantidad de producto bruto (kg/ha)</th>
                        <th>Fertilizante empleado</th>
                        <th>Composición (%)</th>
                        <th>Cantidad por elemento</th>
                        <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
                            <th>Accion</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="listFert.get(0).get('chemical')" var="ferChe">
                        <tr id="trFerChe${idFerChe}">
                            <%@ include file="row-ferche.jsp" %>                                
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <label style="<%= labelFerChe %>"><%= "No se encuentra registrado ninguna fertilizacion quimica"%></label>
            <div class="hide">
                <div id="confirm_dialog_ferche" class="cbox_content">
                    <div class="sepH_c"><strong>Desea borrar esta fertilizacion quimica?</strong></div>
                    <div>
                        <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                        <a href="#" class="btn btn-small confirm_no">No</a>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <% String tableFerOrg = "display:none";%>
    <% String labelFerOrg = "";%>

    <s:if test="listFert.get(0).get('organic').size() > 0">
        <% tableFerOrg = "";%>
        <% labelFerOrg = "display:none";%> 
    </s:if>            

    <div id="divFerOrg" class="w-box">
        <fieldset>
            <legend>Lista de fertilizaciones organicas</legend>        
            <table class="table table-bordered table-hover" style="<%= tableFerOrg %>" id='tblFerOrg'>
                <thead>
                    <tr>
                        <th>Fecha de la aplicación</th>
                        <th>Cantidad de producto bruto (kg/ha)</th>
                        <th>Fertilizante empleado</th>
                        <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
                            <th>Accion</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="listFert.get(0).get('organic')" var="ferOrg">
                        <tr id="trFerOrg${idFerOrg}">
                            <%@ include file="row-ferorg.jsp" %>                            
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <label style="<%= labelFerOrg %>"><%= "No se encuentra registrado ninguna fertilizacion organica"%></label>
            <div class="hide">
                <div id="confirm_dialog_ferorg" class="cbox_content">
                    <div class="sepH_c"><strong>Desea borrar esta fertilizacion organica?</strong></div>
                    <div>
                        <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                        <a href="#" class="btn btn-small confirm_no">No</a>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <% String tableFerAme = "display:none";%>
    <% String labelFerAme = "";%>

    <s:if test="listFert.get(0).get('amendment').size() > 0">
        <% tableFerAme = "";%>
        <% labelFerAme = "display:none";%> 
    </s:if>            

    <div id="divFerAme" class="w-box">
        <fieldset>
            <legend>Lista de fertilizaciones de enmiendas</legend>        
            <table class="table table-bordered table-hover" style="<%= tableFerAme %>" id='tblFerAme'>
                <thead>
                    <tr>
                        <th>Fecha de la aplicación</th>
                        <th>Cantidad de producto bruto (kg/ha)</th>
                        <th>Fertilizante empleado</th>
                        <% if (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/modify") || (usrFerDao.getPrivilegeUser(userFer.getIdUsr(), "crop/delete"))) { %>
                            <th>Accion</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="listFert.get(0).get('amendment')" var="ferAme">
                        <tr id="trFerAme${idFerAme}">
                            <%@ include file="row-ferame.jsp" %>                                
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <label style="<%= labelFerAme %>"><%= "No se encuentra registrado ninguna fertilizacion de enmiendas"%></label>
            <div class="hide">
                <div id="confirm_dialog_ferame" class="cbox_content">
                    <div class="sepH_c"><strong>Desea borrar esta fertilizacion de enmienda?</strong></div>
                    <div>
                        <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                        <a href="#" class="btn btn-small confirm_no">No</a>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
</fieldset>