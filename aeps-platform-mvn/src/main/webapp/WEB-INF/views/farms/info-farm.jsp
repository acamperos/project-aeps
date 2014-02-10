<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page import="java.lang.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aepscolombia.platform.controllers.JavascriptHelper"%>
<% String table = "display:none";%>
<% String label = "";%>

<s:if test="listProperties.size() > 0">
    <% table = "";%>
    <% label = "display:none";%> 
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
    <% if (value.equals("property")) {%>
        <button type="button" class="btn btn-primary" onclick="viewForm('/aeps-plataforma-mvn/showFarm.action?action=create', 'idFar', '', 'Crear Finca', 1050, 550)">
            <span class="glyphicon glyphicon-plus-sign"></span>Agregar finca
        </button>
    <% }%>
    <table class="table table-bordered table-hover" style="<%= table%>" id='tblFarms'>
        <thead>
            <tr>
                <% if (value != "property") {%>
                    <% if (value.equals("lot") || value.equals("crop")) {%>
                        <th></th>
                    <% }%>
                <% }%>
                <!-- <th>#</th> -->
                <th>Nombre</th>
                <th>Direccion</th>
                <th>Departamento</th>
                <th>Municipio</th>
                <th>Vereda</th>
                <th>Latitud</th>
                <th>Longitud</th>
                <th>Altura</th>
                <% if (value.equals("property") || value == "property") { %>
                    <th>Accion</th>
                <% }%>
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
                <tr id="trProperty<s:property value="id_farm" />" onclick="<%= action%>">
                    <%@ include file="row-farm.jsp" %>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <label style="<%= label%>"><%= "No se encuentra registrada ninguna finca"%></label>
    <div class="hide">
        <div id="confirm_dialog_property" class="cbox_content">
            <div class="sepH_c"><strong>Desea borrar esta(s) finca(s)?</strong></div>
            <div>
                <a href="#" class="btn btn-small btn-primary confirm_yes">Si</a>
                <a href="#" class="btn btn-small confirm_no">No</a>
            </div>
        </div>
    </div>
</div>
<div>
    <% if (!value.equals("property")) {%>
        <button class="btn btn_per" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')">Atras</button>
    <% }%>
</div>
<div style="text-align:center; <%= table %>">
    <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/searchFarm.action?selected="+value, divHide, "", "", "formFarmSearch");%>    
    <%= result%>
</div>
<s:div id="divProperties" />