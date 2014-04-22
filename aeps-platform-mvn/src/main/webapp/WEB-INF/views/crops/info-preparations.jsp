<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>            
<% String table = "display:none";%>
<% String label = "";%>

<s:if test="listPrep.size() > 0">
    <% table = "";%>
    <% label = "display:none";%> 
</s:if>            

<div class="msgWin" id="divMessListPrep"></div>
<div id="divPrep" class="w-box">
    <fieldset>
        <legend>Lista de preparaciones</legend>
        <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/aeps-plataforma-mvn/crop/showPrep.action?action=create', 'idCrop', '${idCrop}', 'Crear Preparaci�n', 1050, 550);">
            <i class="icon-plus"></i> Agregar Preparaci�n
        </button>
        <table class="table table-bordered table-hover" style="<%= table %>" id='tblPrep'>
            <thead>
                <tr>
                    <th>Fecha de trabajo</th>
                    <th>Profundidad del trabajo (cm)</th>
                    <th>Tipo de preparaci�n</th>
                    <th>Otro tipo de preparaci�n</th>
                    <th>Manejo de rastrojos</th>
                    <th>Otro manejo de rastrojos</th>
                    <th>Accion</th>
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
        <label style="<%= label%>"><%= "No se encuentra registrada ninguna preparaci�n"%></label>
        <div class="hide">
            <div id="confirm_dialog_prep" class="cbox_content">
                <div class="sepH_c"><strong>Desea borrar esta preparaci�n?</strong></div>
                <div>
                    <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                    <a href="#" class="btn btn-small confirm_no">No</a>
                </div>
            </div>
        </div>
    </fieldset>
</div>