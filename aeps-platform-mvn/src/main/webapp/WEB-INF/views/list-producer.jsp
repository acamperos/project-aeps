<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<html>
    <head></head>
    <body>
        <div class="container">
            <%@page import="org.apache.commons.lang.StringUtils"%>
            <%@page import="java.lang.*"%>
            <%@page import="java.util.HashMap"%>
            <%@page import="org.aepscolombia.platform.controllers.JavascriptHelper"%>
            <%@page import="com.opensymphony.xwork2.ActionContext" %>
            <%@page import="com.opensymphony.xwork2.util.ValueStack" %>
            <% String table = "display:none";%>
            <% String label = "";%>

            <s:if test="listProducers.size() > 0">
                <% table = "";%>
                <% label = "display:none";%> 
            </s:if>
            <%-- <% int pos = request.getParameter("additionals").indexOf("selected"); %> --%>
            <% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
            <% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
            <% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
            <% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
            <% HashMap add = (HashMap) request.getAttribute("additionals");%>
            <% String value = (String) (add.get("selected"));%>
            <% String valId = (String) (request.getAttribute("valId"));%>
            <% String valName = (String) (request.getAttribute("valName"));%>
            <% //System.out.println("datos->"+value); %>
            <% String divShow = "";%>
            <% String divHide = "";%>
            <% if (value.equals("property")) {
                    divShow = "divFincasForm";
                    divHide = "divListFincasForm";
                } else if (value.equals("lot")) {
                    divShow = "divLotesForm";
                    divHide = "divListLotesForm";
    }%>
            <div id="divProducers" class="w-box">
                <button type="button" class="btn btn-primary btn-lg active" onclick="viewForm('/aeps-plataforma-mvn/showProducer.action?action=create', 'idPro', '', 'Crear Productor', 1050, 550)">
                    <span class="glyphicon glyphicon-plus-sign"></span>Agregar productor
                </button>
                <table class="table table-bordered table-hover" style="<%= table%>" id='tblProducers'>
                    <thead>
                        <tr>
                            <% if (value != "producer") {%>    
                            <th></th>
                                <% }%>
                            <!-- <th>#</th> -->
                            <th>Cedula</th>
                            <th>Nombre</th>
                            <th>Telefono</th>
                            <th>Celular</th>
                            <th>Correo</th>
                            <th>Direccion</th>
                            <th>Municipio</th>
                                <% if (value == "producer") {%>    
                            <th>Accion</th>
                                <% }%>
                        </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="listProducers" var="producer" status="proPos">
                        <!--                <tr>
                                            <td>
                        <%--<s:property value="#producer.tipo_cedula"/>--%>
                    </td>
                </tr>-->
                        <% String action = "";%> 
                        <% if (!value.equals("producer") || value != "producer") {
                                if (value.equals("property") || value == "property") {
                                    action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name") + "', '" + request.getAttribute("id_producer") + "', '" + divShow + "', '" + divHide + "')";
                                } else if (value.equals("lot") || value == "lot") {
                                    action = "viewInfo('/aeps-plataforma-mvn/searchProducer.action?action=modify&idProducer=" + request.getAttribute("id_producer") + "&selected=" + add.get("selected") + "', 'Listado de Fincas', 'divProducers', 'divProperties')";
                        }
                    }%>     
                        <tr id="trProducer<s:property value="id_producer" />" onclick="<%=action%>">
                            <% if (value != "producer") {%>    
                            <td><img src="/aeps-plataforma-mvn/img/check.ico" onclick="<%=action%>" /></td>               
                                <% }%>    
                            <td><s:property value="type_document" />: <s:property value="cedula" /></td>
                        <td><s:property value="name" /></td>
                        <td><s:property value="phone" /></td>
                        <td><s:property value="cellphone" /></td>
                        <td><s:property value="e_mail_1" /></td>
                        <td><s:property value="direction" /></td>
                        <td><s:property value="city" /></td>
                        <% if (value == "producer") {%>
                        <td>
                            <div class="btn-group">
                                <a href="#" class="btn btn-mini" title="Editar Productor" onclick="viewForm('/aeps-plataforma-mvn/showProducer.action', 'idPro', <s:property value ="id_producer" />, 'Editar Productor', 1050, 550)"><i class="icon-pencil"></i></a>
                                <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Productor" onclick="showDialogDelete(this, 'confirm_dialog_producer');"><i class="icon-trash"></i></a>
                            </div>
                        </td>
                        <% }%>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>    
                <label style="<%= label%>"><%= "No se encuentra registrado ningun productor"%></label>
                <div class="hide">
                    <div id="confirm_dialog_producer" class="cbox_content">
                        <div class="sepH_c"><strong>Desea borrar este(os) productor(es)?</strong></div>
                        <div>
                            <a href="#" class="btn btn-small btn-beoro-3 confirm_yes">Si</a>
                            <a href="#" class="btn btn-small confirm_no">No</a>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <% if (value != "producer") {%>
                <button class="btn btn_per bt_cancel_producer" onclick="toggleAndClean('<%=divShow%>', '<%=divHide%>')">Atras</button>
                <% }%>
            </div>
            <s:div style="text-align:center">
                <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/searchProducer.action", "divBodyLayout", "", "");%>    
                <%= result%>
            </s:div>
            <s:div id="divProperties" />
        </div>        
    </body>
</html>