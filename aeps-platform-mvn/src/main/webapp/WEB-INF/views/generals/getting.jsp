<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/ico" href="img/logoAEPS.ico">
    </head>
    <body>
        <div id="dialog-form"></div>
        <div class="container">
            <ul id="breadcrumbs">
                <s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
                <li><s:a href="dashboard.action" targets="divBodyLayout"><i class="icon-home"></i>Inicio</s:a></li>
                <li><span>Recolectar datos</span></li>
            </ul>
        </div>
        <div class="container">			
            <!-- Example row of columns -->
            <div class="row">                
                <div class="span6 thumbnail custom-thumb">
					<img class="img-responsive hidden-xs" src="img/producers.jpg" alt="">
					<div class="caption">
						<h3>Productores</h3>
                        <p>Encargado de la toma de datos para los productores o agricultores</p>
                        <%--<s:url id="ajaxTest" value="/AjaxTest.action"/>--%>
                        <%--<sj:a id="link1" href="%{ajaxTest}" targets="div1">--%>
                        <p><s:a cssClass="btn btn-initial" href="listProducer.action" role="button" targets="divBodyLayout">Ir <i class="icon-white icon-double-angle-right"></i></s:a></p>
					</div>
				</div>
                <div class="span6 thumbnail custom-thumb" style="margin-left: 0">
					<img class="img-responsive hidden-xs" src="img/crops.jpg" alt="">
					<div class="caption">
						<h3>Cultivos</h3>
                        <p>Encargado de la toma de datos para los cultivos</p>                        
                        <p><s:a cssClass="btn btn-initial" href="%{contextPath}/crop/listCrop.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a></p>
					</div>
				</div>
            </div>
            <div class="row">                
                <div class="span6 thumbnail custom-thumb">
					<img class="img-responsive hidden-xs" src="img/farms.jpg" alt="">
					<div class="caption">
						<h3>Fincas</h3>
                        <p>Encargado de la toma de datos para las fincas</p>
                        <p><s:a cssClass="btn btn-initial" href="listFarm.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a></p>
					</div>
				</div>
                <div class="span6 thumbnail custom-thumb" style="margin-left: 0">
					<img class="img-responsive hidden-xs" src="img/soils.jpg" alt="">
					<div class="caption">
						<h3>Suelos</h3>
                        <p>Encargado de la toma de datos para los suelos</p>
                        <p><s:a cssClass="btn btn-initial" href="%{contextPath}/soil/listSoil.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a></p>
					</div>
				</div>
            </div>
            <div class="row">                
                <div class="span6 thumbnail custom-thumb">
					<img class="img-responsive hidden-xs" src="img/fields.jpg" alt="">
					<div class="caption">
						<h3>Lotes</h3>
                        <p>Encargado de la toma de datos para los lotes</p>
                        <p><s:a cssClass="btn btn-initial" href="listField.action" role="button" targets="divBodyLayout">Ir <i class="icon-double-angle-right"></i></s:a></p>
					</div>
				</div>
                <div class="span6 thumbnail custom-thumb" style="margin-left: 0">
					<img class="img-responsive hidden-xs" src="img/climate.jpg" alt="">
					<div class="caption">
						<h3>Clima</h3>
                        <p>Encargado de la toma de datos para el clima</p>
					</div>
				</div>
            </div>
        </div>
    </body>
</html>
