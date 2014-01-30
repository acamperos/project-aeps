<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head></head>
    <body>
        <div class="container">
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
            <% HashMap add = (HashMap) request.getAttribute("additionals");%>
            <% String value = (String) add.get("selected");%>
            <% String valId = String.valueOf(request.getAttribute("valId"));%>
            <% String valName = String.valueOf(request.getAttribute("valName"));%>
            <% //System.out.println("datos->"+request.getAttribute("listProperties"));   %>

            <div class="msgWin" id="messageWin"></div>
            <div id="divFarms" class="w-box">
                <button type="button" class="btn btn-primary btn-lg active" onclick="viewForm('/aeps-plataforma-mvn/showFarm.action?action=create', 'idFar', '', 'Crear Finca', 1050, 550)">
                    <span class="glyphicon glyphicon-plus-sign"></span>Agregar finca
                </button>
                <table class="table table-bordered table-hover" style="<%= table%>" id='tblFarms'>
                    <thead>
                        <tr>
                            <% if (value != "property") {%>
                            <th></th>
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
                                <% if (value == "property") {%>
                            <th>Accion</th>
                                <% }%>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listProperties" var="property">
                            <% String action = "";%>
                            <% if (value != "property") {
                                    if (value == "lot" || value == "crop") {
                                        action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_farm") + "', '" + request.getAttribute("id_farm") + "')";
                    }
                }%>       
                            <%--<s:else>--%>
                            <!--<tr id="trPropertys:property value="id_finca" />">-->
                            <%--</s:else>--%>
                            <tr id="trProperty<s:property value="id_farm" />" onclick="<%= action%>">
                                <% if (value != "property") {%>
                                <% if (value == "lot") {%>
                                <td><img src="/aeps-plataforma-mvn/img/check.ico" onclick="<%= action%>"/></td>
                                    <% } else if (value == "crop") {%>
                                <td><img src="/aeps-plataforma-mvn/img/check.ico" onclick="<%= action%>"/></td>
                                    <% }%>                        
                                    <% }%>
                                <td><s:property value="name_farm" /></td>
                                <td><s:property value="dir_farm" /></td>
                                <td><s:property value="name_dep" /></td>
                                <td><s:property value="name_mun" /></td>
                                <td><s:property value="lane_farm" /></td>
                                <td><s:property value="latitude_farm" /></td>
                                <td><s:property value="longitud_farm" /></td>
                                <td><s:property value="altitude_farm" /></td>
                                <% if (value == "property") {%>
                                <td>
                                    <div class="btn-group">
                                        <a href="#" class="btn btn-mini" title="Editar Finca" onclick="viewForm('/aeps-plataforma-mvn/showFarm.action', 'idFar', <s:property value="id_farm" />, 'Editar Finca', 1600, 550)"><i class="icon-pencil"></i></a>
                                        <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Finca" onclick="showDialogDelete(this, 'confirm_dialog_property');"><i class="icon-trash"></i></a>
                                    </div>
                                </td>
                                <% }%>
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
            <s:div style="text-align:center">
                <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/searchFarm.action", "divBodyLayout", "", "");%>    
                <%= result%>
            </s:div>
            <s:div id="divProperties" />
        </div>        
    </body>
</html>