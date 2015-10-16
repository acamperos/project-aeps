<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="icon" type="image/ico" href="img/logoAEPS.ico">
		<title>AEPS</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width"> 							
        <sj:head jqueryui="false"/>
		<sb:head includeScripts="true" includeScriptsValidation="true"/>
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.min.js"></script>
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.time.js"></script>    
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.stack.js"></script>
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.symbol.js"></script>
		<script type="text/javascript" src="scripts/js/flot-charts/jquery.flot.axislabels.js"></script>
		<script>
		var dataResult = '{ "detailProfiles" : [{"profile1" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile2" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile3" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>"}], "valTable" : [{"producerId" : "Jose Arana", "depthId" : "Profundidad Efectiva (cm) - 110", "phId" : "5", "structureId" : "Granular", "exposeId" : "La Mañana y Tarde", "coveringId" : "Bueno", "drainIntId" : "Bueno", "drainExtId" : "Lento"}], "valIn" : "Inclinacion: 12%", "data" : [{"point" :"[[1, -20]]", "color": "#0077FF"},{"point" :"[[1, -40]]", "color": "#7D0096"},{"point" :"[[1, -60]]", "color": "#DE000F"}], "infoProfile" : [{"pro1": "a","pro2": "f","pro3": "l"}] }';
		var objResult  = JSON.parse(dataResult);
		
		function chargeInfographic(dataResult) {			
			$('#inclinationInfo').text(objResult.valIn);
			var dataTemp	 = '[';
			$.each(objResult.data, function(index, info) {
				var pos = index+1;
				if (index!=(objResult.data.length-1)) {
					dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" },';		
				} else {
					dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" }]';	
				}
			});
			var dataset = eval(dataTemp);

			var options1 = {
					series: {
							stack: true,
							bars: {
									show: true
							}
					},
					bars: {
							align: "center",
							barWidth: 124 * 60 * 60 * 2000,
							lineWidth: 5
					},
					xaxis: {
							//mode: "time",
							//tickSize: [3, "day"],
							tickLength: 1,
							show: false,
							color: "black",
							//axisLabel: "Date",
							axisLabelUseCanvas: true,
							axisLabelFontSizePixels: 12,
							axisLabelFontFamily: 'Verdana, Arial',
							axisLabelPadding: 10
					},
					yaxis: {
							tickLength: 1,
							color: "black",
							//axisLabel: "DNS Query Count",
							axisLabelUseCanvas: true,
							axisLabelFontSizePixels: 12,
							axisLabelFontFamily: 'Verdana, Arial',
							axisLabelPadding: 3
							/*,tickFormatter: function (v, axis) {
									return v;
							}*/
					},
					grid: {
							hoverable: true,
							borderWidth: 2,
							backgroundColor: { colors: ["#EDF5FF", "#ffffff"] }
					},
					colors:["#004078","#207800", "#613C00"]
			};

			$(document).ready(function () {
					$.plot($("#flot-placeholder"), dataset, options1);    
					$("#flot-placeholder").UseTooltip();
			});
			
			/*function gd(year, month, day) {
					return new Date(year, month - 1, day).getTime();
			}		
			var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
			*/

			var previousPoint = null, previousLabel = null;
			$.fn.UseTooltip = function () {
                $(this).bind("plothover", function (event, pos, item) {
                    if (item) {
                        if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                            previousPoint = item.dataIndex;
                            previousLabel = item.series.label;
                            $("#tooltip").remove();

                            var x = item.datapoint[0];
                            var y = item.datapoint[1];
                            var color = item.series.color;
                            /*var day = "Hola " + new Date(x).getDate();										
                            "<strong>" + item.series.label + "</strong><br>" + day
                                             + " : <strong>" + y +
                                             "</strong>(Count)"*/										
                            //var obj = JSON.parse(json1);										
                            var text = eval("objResult.detailProfiles[0].profile"+item.series.label);			
                            var soilSel = eval("objResult.infoProfile[0].pro"+item.series.label);										
                            findInfoSoil(soilSel);										
                            showTooltip(item.pageX, item.pageY, color, text);
                        }
                    } else {
                        $("#tooltip").remove();
                        previousPoint = null;
                        findInfoSoil("neutral");
                    }
                });
			};		
		}
		
		function showTooltip(x, y, color, contents) {
				$('<div id="tooltip">' + contents + '</div>').css({
						position: 'absolute',
						display: 'none',
						top: y - 40,
						left: x - 120,
						border: '2px solid ' + color,
						padding: '3px',
						'font-size': '9px',
						'border-radius': '5px',
						'background-color': '#fff',
						'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
						opacity: 0.9
				}).appendTo("body").fadeIn();
		}		
		
		</script>
		<style type="text/css">
			.active {
				display: block;
			}
			
			.deactivate {
				display: none;
			}
			
			.inclinationText {
				margin-top: 0px; 
				position: relative; 
				top: -50px; 
				left: 35px;
			}
			
			.img-size {
				width: 550px;
			}
		</style>
	<head>
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
				</table>
			</div>		
			<div class="span3">
				<canvas id="canvas" width="160" height="160"></canvas>
				<p class="inclinationText" id="inclinationInfo"></p>
			</div>
		</div>
		<div class="row">
			<div class="span6">
				<label style="color:red" class="infoGeneral" id="depthId"></label>
				<div id="flot-placeholder" style="width:450px;height:500px;"></div>		
			</div>
			<div class="span6">
				<div id="divImages" style="margin-top: 100px">
					<div id="neutral" class="soils active">
						<img class="img-size" src="img/triangulo/Neutral.png" alt="">
					</div>
					<div id="a" class="soils deactivate">
						<img class="img-size" src="img/triangulo/A.png" alt="">
					</div>
					<div id="afar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/AFAr.png" alt="">
					</div>
					<div id="ar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/Ar.png" alt="">
					</div>
					<div id="ara" class="soils deactivate">
						<img class="img-size" src="img/triangulo/ArA.png" alt="">
					</div>
					<div id="arl" class="soils deactivate">
						<img class="img-size" src="img/triangulo/ArL.png" alt="">
					</div>
					<div id="f" class="soils deactivate">
						<img class="img-size" src="img/triangulo/F.png" alt="">
					</div>
					<div id="fa" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FA.png" alt="">
					</div>
					<div id="far" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FAr.png" alt="">
					</div>
					<div id="flar" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FLAr.png" alt="">
					</div>
					<div id="fran" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FrAn.png" alt="">
					</div><!-- Cambio de FrA = fran -->
					<div id="frl" class="soils deactivate">
						<img class="img-size" src="img/triangulo/FrL.png" alt="">
					</div>
					<div id="l" class="soils deactivate">
						<img class="img-size" src="img/triangulo/L.png" alt="">
					</div>
				</div>
			</div>
		</div>
		<script>
			var ctx = $('#canvas')[0].getContext("2d");
			ctx.fillStyle = "#00A308";
			ctx.beginPath();
			ctx.moveTo(150,150);
			ctx.lineTo(150,140);
			ctx.lineTo(0,150);
			ctx.fill();			
			
			function chargeInfoTable() {
				var divInfo = document.getElementsByClassName('infoGeneral');
				$.each(divInfo, function(key, elem){
						var valInfo = eval("objResult.valTable[0]."+elem.id);
						$(elem).text(valInfo);
				});
			}
			
			chargeInfographic(dataResult);
			chargeInfoTable();			
			function findInfoSoil(soilSel) {
				var divChange = document.getElementsByClassName('soils');
				$('div.soils').removeClass("active").removeClass("deactivate");
				$.each(divChange, function(key, elem){
						if(elem.id==soilSel){
							$(elem).addClass("active");
						} else {
							$(elem).addClass("deactivate");
						}
				});
			}
		</script>
	</body>
</html>