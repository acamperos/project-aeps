<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <div id="dialog-form"></div>
        <div class="container">
            <ul id="breadcrumbs">
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><span>Recolectar datos</span></li>
            </ul>
        </div>
        <div class="container">			
            <!-- Example row of columns -->
            <div class="row">
                <div class="span4">
                    <img id="img_farmer" src="img/farmer.jpg"/>
                    <h3>Productores</h3>
                    <p>Encargado de la toma de datos para los productores o agricultores</p>
                    <%--<s:url id="ajaxTest" value="/AjaxTest.action"/>--%>
                    <%--<sj:a id="link1" href="%{ajaxTest}" targets="div1">--%>
                    <p><s:a cssClass="btn btn-primary" href="listProducer.action" role="button" targets="divBodyLayout">Ir »</s:a></p>
                </div>
                <div class="span4">
                    <img id="img_farmer" src="img/property.jpg"/>
                    <h3>Fincas</h3>
                    <p>Encargado de la toma de datos para las fincas</p>
                    <p><s:a cssClass="btn btn-primary" href="listFarm.action" role="button" targets="divBodyLayout">Ir »</s:a></p>
                </div>
                <div class="span4">
                    <img id="img_farmer" src="img/lot.jpg"/>
                    <h3>Lotes</h3>
                    <p>Encargado de la toma de datos para los lotes</p>
                    <p><s:a cssClass="btn btn-primary" href="listField.action" role="button" targets="divBodyLayout">Ir »</s:a></p>
                </div>
            </div>
            <div class="row">
                <div class="span4">
                    <img id="img_farmer" src="img/culture.jpg"/>
                    <h3>Cultivos</h3>
                    <p>Encargado de la toma de datos para los cultivos</p>
                </div>
                <div class="span4">
                    <img id="img_farmer" src="img/suelos.jpg"/>
                    <h3>Suelos</h3>
                    <p>Encargado de la toma de datos para los suelos</p>
                </div>
                <div class="span4">
                    <img id="img_farmer" src="img/climate.png"/>
                    <h3>Clima</h3>
                    <p>Encargado de la toma de datos para el clima</p>
                </div>
            </div>
        </div>
    </body>
</html>
