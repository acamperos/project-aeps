<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="org.aepscolombia.platform.models.entity.Users"%>
<%@page import="org.aepscolombia.platform.models.dao.UsersDao"%>
<%@page import="org.aepscolombia.platform.util.APConstants"%>
<%@page import="org.aepscolombia.platform.models.dao.EntitiesDao"%>
<% Users userFar  = (Users) session.getAttribute(APConstants.SESSION_USER); %>
<% UsersDao usrFarDao = new UsersDao(); %>
<% Integer entTypeFarId = new EntitiesDao().getEntityTypeId(userFar.getIdUsr()); %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.util.JavascriptHelper"%>
<% String table = "display:none;";%>
<% String label = "";%>

<s:if test="listProperties.size() > 0">
    <% table = "";%>
    <% label = "display:none;";%> 
</s:if>
<%-- <% int pos = request.getParameter("additionals").indexOf("selected"); %> --%>
<% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
<% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
<% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
<% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
<% HashMap add    = (HashMap) request.getAttribute("additionals");%>
<% String value   = (String) add.get("selected");%>
<% String valId   = String.valueOf(request.getAttribute("valId"));%>
<% String valName = String.valueOf(request.getAttribute("valName"));%>
<% //System.out.println("datos->"+request.getAttribute("listProperties"));   %>
<% String divShow = "";%>
<% String divHide = "";%>
<% if (value.equals("lot")) {
        divShow = "divFieldsForm";
        divHide = "divListFieldsForm";
    } else {
        divHide = "divConListFarms";
    }            
%>    
<div class="msgWin" id="messageWin"></div>
<div id="divFarms" class="w-box">
    <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/create")) { %>
        <% if (entTypeFarId!=3) { %>    
            <% if (value.equals("property")) {%>
                <button type="button" class="btn btn-initial btn-space" onclick="viewForm('/showFarm.action?action=create&viewInfo=${viewInfo}', 'idFar', '', 'Crear Finca', 1050, 550)">
                    <i class="icon-plus"></i> Agregar finca
                </button>
            <% } %>
        <% } %>
    <% } %>
    <table class="table table-bordered table-hover" style="<%= table%> max-width: 100%" id='tblFarms'>
        <thead>
            <tr>
                <% if (value != "property") {%>
                    <% if (value.equals("lot") || value.equals("crop")) {%>
                        <th></th>
                    <% }%>
                <% }%>
                <!-- <th>#</th> -->
                <% if (entTypeFarId==3) { %>    
                    <th>Agronomo</th>
                <% } %>
                <th>Nombre</th>
                <th>Vereda</th>
                <th>Indicación (Como llegar)</th>
                <th>Departamento</th>
                <th>Municipio</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Altura</th>
                <th>Fecha de creación</th>                                
                <% if (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/modify") || (usrFarDao.getPrivilegeUser(userFar.getIdUsr(), "farm/delete"))) { %>
                    <% if (value.equals("property") || value == "property") { %>
                        <th>Accion</th>
                    <% }%>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="listProperties" var="property">
                <% String action = "";%>
                <% if (value != "property") {
                        if (value.equals("lot")) {
                            action = "selectItem('formField_name_property_lot', 'formField_idFarm', '" + request.getAttribute("name_farm") + "', '" + request.getAttribute("id_farm") + "', '" + divShow + "', '" + divHide + "')";
                        }
                        if (value.equals("crop")) {
                            action = "selectItem('formFarm_name_producer', 'formFarm_idProducer', '" + request.getAttribute("name_farm") + "', '" + request.getAttribute("id_farm")  + "', '" + divShow + "', '" + divHide + "')";
                        }
                   }
                %>       
                <%--<s:else>--%>
                <!--<tr id="trPropertys:property value="id_finca" />">-->
                <%--</s:else>--%>
                <tr id="trProperty<s:property value="id_farm" />" class="selectVal" onclick="<%= action%>">
                    <%@ include file="row-farm.jsp" %>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrada ninguna finca"%></label>
    <div class="hide">
        <div id="confirm_dialog_property" class="cbox_content">
            <div class="sepH_c"><strong>Desea borrar esta(s) finca(s), <br>
                    al momento de confirmar todas las dependencias también van a desaparecer?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-initial confirm_yes">Si</a>
                <a href="#" class="btn btn-small confirm_no">No</a>
            </div>
        </div>
    </div>
</div>
<div>
    <% if (!value.equals("property")) {%>
        <button class="btn btn_per" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')"><i class="icon-arrow-left"></i> Atras</button>
    <% }%>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/searchFarm.action?selected="+value, divHide, "", "", "formFarmSearch");%>    
    <%= result%>
</div>
<s:div id="divProperties" />