<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
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
            <% int pageNow = (request.getParameter("page") != null) ? Integer.parseInt(String.valueOf(request.getParameter("page"))) : 1;%>
            <% //int pageNow     = Integer.parseInt(String.valueOf(request.getParameter("page")));  %>
            <% int countTotal = Integer.parseInt(String.valueOf(request.getAttribute("countTotal")));%>
            <% int maxResults = Integer.parseInt(String.valueOf(request.getAttribute("maxResults")));%>
            <% int idProducer = Integer.parseInt(String.valueOf(request.getAttribute("idProducer")));%>
            <% String valId = String.valueOf(request.getAttribute("valId"));%>
            <% String valName = String.valueOf(request.getAttribute("valName"));%>
            <% HashMap add = (HashMap) request.getAttribute("additionals");%>
            <% String value = (String) add.get("selected");%>
            <% //System.out.println("datos->"+request.getAttribute("listProperties"));   %>

            <div class="msgWin" id="messageWin"></div>
            <div id="divFields" class="w-box">
                <button type="button" class="btn btn-primary btn-lg active" onclick="viewForm('/aeps-plataforma-mvn/showField.action?action=create', 'idField', '', 'Crear Lote', 1050, 550)">
                    <span class="glyphicon glyphicon-plus-sign"></span>Agregar lote
                </button>
                <table class="table table-bordered table-hover" style="<%= table %>" id='tblFields'>
                    <thead>
                        <tr>
                            <s:if test="!value.equals('lot')">
                                <th></th>
                                </s:if>
                            <!-- <th>#</th> -->
                            <th>Nombre</th>
                            <th>Area</th>
                            <th>Latitud</th>
                            <th>Longitud</th>
                            <th>Altura</th>
                                <s:if test="value.equals('lot')">
                                <th>Accion</th>
                                </s:if>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listLot" var="lot">
                            <% String action = "";%>
                            <s:if test="!value.equals('lot')">
                                <% action = "selectItem('" + valName + "', '" + valId + "', '" + request.getAttribute("name_lot") + "', '" + request.getAttribute("idField") + "');";%>
                                <!--<?php $action = "selectItem('".$additionals['valName']."', '".$additionals['valId']."', '".$lot['nombre']."', '".$lot['id_lote']."'); " ?>-->
                            </s:if>
                            <s:if test="value.equals('crop')">
                                <% action += "getInfo('/aeps-plataforma-mvn/cultivosAsociados.action?idProd=" + idProducer + "','idField','" + valId + "','divDataCrop','divMessage');";%>
                            </s:if>
                            <s:if test="value.equals('rasta')">
                                <% action += "getInfo('/aeps-plataforma-mvn/GetRastasXField.action','idField','" + valId + "','divDataRastas','divMessage');";%>
                            </s:if>
                            <tr onclick="<%= action%>" id="trLot<s:property value="id_lot" />>">
                                <s:if test="!value.equals('lot')">
                                    <td><img src="/aeps-plataforma-mvn/img/check.ico"/></td>                     
                                    </s:if>
                                <td><s:property value="name_lot" /></td>
                                <td><s:property value="area_lot" /></td>
                                <td><s:property value="latitud_lot" /></td>
                                <td><s:property value="longitud_lot" /></td>
                                <td><s:property value="altitud_lot" /></td>
                                <s:if test="value.equals('lot')">
                                    <td>
                                        <div class="btn-group">
                                            <a href="#" class="btn btn-mini" title="Editar Lote" onclick="viewForm('/aeps-plataforma-mvn/showField.action', 'idField', <s:property value="id_lot" />, 'Editar Lote', 1600, 550)"><i class="icon-pencil"></i></a>
                                            <a href="#" class="btn btn-mini delete_rows_dt" title="Borrar Lote" onclick="showDialogDelete(this, 'confirm_dialog_lot');"><i class="icon-trash"></i></a>
                                        </div>
                                    </td>
                                </s:if>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
                <label style="<%= label%>"><%= "No se encuentra registrado ningun lote"%></label>
                <div class="hide">
                    <div id="confirm_dialog_lot" class="cbox_content">
                        <div class="sepH_c"><strong>Desea borrar este(s) lote(s)?</strong></div>
                        <div>
                            <a href="#" class="btn btn-small btn-beoro-3 confirm_yes">Si</a>
                            <a href="#" class="btn btn-small confirm_no">No</a>
                        </div>
                    </div>
                </div>
            </div>
            <s:div style="text-align:center">
                <% String result = JavascriptHelper.pager_params_ajax(pageNow, countTotal, maxResults, "/aeps-plataforma-mvn/searchField.action", "divBodyLayout", "", "");%>    
                <%= result%>
            </s:div>
            <%--<s:div id="divLots" />--%>
        </div>       
    </body>
</html>