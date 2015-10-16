<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>		           
            <link rel="stylesheet" href="scripts/css/generals/report.css">
	</head>
	<body>		
		<div class="row w-box">			
			<div class="span7">
                <table border="1" class="table table-bordered">
                    <s:if test="typeEnt==1">                        
                        <tr>
                            <th><i class="icon-producer"></i><s:property value="getText('tr.nameproducer.reportsoil')" /></th>
                            <td id="producerId" class="infoGeneral"></td>
                        </tr>
                    </s:if> 
					<tr>
						<th><i class="icon-ph"></i><s:property value="getText('tr.totalph.reportsoil')" /></th>
						<td id="phId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th><i class="icon-struct"></i><s:property value="getText('tr.structure.reportsoil')" /></th>
						<td id="structureId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th><i class="icon-expose"></i><s:property value="getText('tr.sunexposure.reportsoil')" /></th>
						<td id="exposeId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th><i class="icon-cover"></i><s:property value="getText('tr.coatingfield.reportsoil')" /></th>
						<td id="coveringId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th><i class="icon-intdra"></i><s:property value="getText('tr.internaldrain.reportsoil')" /></th>
						<td id="drainIntId" class="infoGeneral"></td>
					</tr>
					<tr>
						<th><i class="icon-extdra"></i><s:property value="getText('tr.externaldrain.reportsoil')" /></th>
						<td id="drainExtId" class="infoGeneral"></td>
					</tr>
				</table>
			</div>		
			<div class="span3">
                            <h5><s:property value="getText('title.pendant.reportsoil')" /></h5>
                            <canvas id="canvas" width="160" height="160"></canvas>
                            <p class="inclinationText" id="inclinationInfo"></p>
			</div>
		</div>
		<div class="row">
			<div class="span6">
                            <h5><s:property value="getText('title.profilesoil.reportsoil')" /></h5>
                            <div id="flot-placeholder" style="width:350px;height:500px;"></div>		
			</div>
			<div class="span6">
                                <s:hidden name="info"/>       
                                <h5><s:property value="getText('title.textures.reportsoil')" /></h5>
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
                <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
                <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="scripts/js/flot-charts/excanvas.min.js"></script><![endif]-->
                <script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.stack.js"></script>
                <script type="text/javascript" src="scripts/js/generals/report.js"></script>
		<script>
                    //alert($("#info").val());
                    objResult  = JSON.parse($("#info").val());
                    var penRad = objResult.valTable[0].valIn;     
                    //var penDeg = dregreesToRadian(Math.atan(penRad));            
                    //alert(penDeg)     
                    //alert(Math.atan(1))
                    var penRes = (150*penRad)/45;
                    //alert(penRes)      
                    chargeInfographic();
                    chargeInfoTable();			
                    generateInclination(150-penRes);
		</script>
	</body>
</html>