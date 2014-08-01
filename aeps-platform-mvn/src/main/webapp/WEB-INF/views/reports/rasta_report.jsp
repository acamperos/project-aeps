<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>							
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
        <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="scripts/js/flot-charts/excanvas.min.js"></script><![endif]-->
		<!--<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.time.js"></script>-->    
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.stack.js"></script>
		<!--<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.symbol.js"></script>-->
		<!--<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.axislabels.js"></script>-->
		<script type="text/javascript" src="scripts/js/generals/report.js"></script>
        <link rel="stylesheet" href="scripts/css/generals/report.css">
	</head>
	<body>		
		<div class="row">			
			<div class="span7" style="padding-left: 30px;">
				<table border="1">
					<tr>
						<th>Productor</th>
						<td id="producerId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>PH</th>
						<td id="phId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Estructura</th>
						<td id="structureId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Exposición Solar</th>
						<td id="exposeId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Recubrimiento del Lote</th>
						<td id="coveringId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Drenaje Interno</th>
						<td id="drainIntId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Drenaje Externo</th>
						<td id="drainExtId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th>Profundidad  efectiva (cm)</th>
						<td id="depthId" class="infoGeneral"></td>
					</tr>
				</table>
			</div>		
			<div class="span3">
                <h5>Pendiente o inclinación del terreno</h5>
				<canvas id="canvas" width="160" height="160"></canvas>
				<p class="inclinationText" id="inclinationInfo"></p>
			</div>
		</div>
		<div class="row">
			<div class="span6">
                <h5>Perfil del suelo</h5>
				<!--<label style="color:red" class="infoGeneral" id="depthId"></label>-->
				<div id="flot-placeholder" style="width:350px;height:500px;"></div>		
			</div>
			<div class="span6">
                <s:hidden name="info"/>       
                <h5>Texturas</h5>
				<div id="divImages" style="margin-top: 100px">
					<div id="neutral" class="soils active">
						<img class="img-size" src="img/triangulo/Neutral.png" alt="">
					</div>
                    <div id="afar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/AFAr.png" alt="">
					</div>
                    <div id="flar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FLAr.png" alt="">
					</div>
					<div id="A" class="soils deactivate">
						<img class="img-size" src="img/triangulo/A.png" alt="">
					</div>					
					<div id="Ar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/Ar.png" alt="">
					</div>
					<div id="ArA" class="soils deactivate">
						<img class="img-size" src="img/triangulo/ArA.png" alt="">
					</div>
					<div id="ArL" class="soils deactivate">
						<img class="img-size" src="img/triangulo/ArL.png" alt="">
					</div>
					<div id="F" class="soils deactivate">
						<img class="img-size" src="img/triangulo/F.png" alt="">
					</div>
					<div id="AF" class="soils deactivate">
						<img class="img-size" src="img/triangulo/AF.png" alt="">
					</div>
					<div id="FAr" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FAr.png" alt="">
					</div>					
					<div id="FA" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FA.png" alt="">
					</div>
					<div id="FrL" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FrL.png" alt="">
					</div>
					<div id="L" class="soils deactivate">
						<img class="img-size" src="img/triangulo/L.png" alt="">
					</div>
				</div>
			</div>
		</div>
		<script>
            //alert($("#info").val());
            objResult  = JSON.parse($("#info").val());
            var penRad = objResult.valTable[0].valIn;     
            //var penDeg = dregreesToRadian(Math.atan(penRad));            
            //alert(penDeg)     
            //alert(Math.atan(1))
            var penRes = (150*penRad)/45;
            //alert(penRes)      
			//chargeInfographic(dataResult);
			chargeInfographic();
			chargeInfoTable();			
            generateInclination(150-penRes);
		</script>
	</body>
</html>