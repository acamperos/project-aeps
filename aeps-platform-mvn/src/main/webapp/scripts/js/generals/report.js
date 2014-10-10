//var objResult  = JSON.parse(dataResult);
var objResult;

function dregreesToRadian(rad) {
    return rad * 180 / Math.PI; 
}
	
function chargeInfographic() {			
    $('#inclinationInfo').text(objResult.valTable[0].valDes);
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
    var options = {
        series: {
            stack: false,
            bars: {
                show: true
            }
        },
        bars: {
            align: "center",
            barWidth: 124 * 60 * 60 * 2000,
            lineWidth: 5,
            fillColor: { colors: [ { opacity: 0.7 }, { opacity: 0.7 } ] }
        },
        legend: {
            show: false
        },
        xaxis: {
            tickLength: 20,
            show: false,
            color: "black",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: "Istok Web",
            axisLabelPadding: 10
        },
        yaxis: {
            tickLength: 50,
            color: "black",
            axisLabelUseCanvas: true,
            position: "left",
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: "Istok Web",
            axisLabelPadding: 3,
            autoscaleMargin: null
        },
        grid: {
            hoverable: true,
            borderWidth: 2,
            backgroundColor: { colors: ["#EDF5FF", "#ffffff"] }
        }
    };   

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
                    var text  = eval("objResult.detailProfiles[0].profile"+item.series.label);			
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
    
    var plot = $.plot($("#flot-placeholder"), dataset, options);    
    $("#flot-placeholder").UseTooltip();

    var series = plot.getData();
    var pos  = 0;
    var posX = 1;
    
    for (var i = 0; i < series.length; ++i) {
        var num = series[i].data;
        var str = num.toString();
        var val = str.split(",");
        pos   = pos + Math.abs(val[1]);
        var o = plot.pointOffset({ x: 1, y: -pos});
        var textColor = eval("objResult.detailColors[0].text"+posX);
        posX++;
        $("#flot-placeholder").append("<div style='position:absolute;left:135px;top:"+(o.top-30)+"px;color:#000;font-size:normal'>"+textColor+"</div>");
    }
    
    var depthId = parseInt(eval("objResult.valTable[0].depthId"));
    var o = plot.pointOffset({ x: 1, y: -depthId});
    $("#flot-placeholder").append("<div style='position:absolute;left:115px;top:"+(o.top-20)+"px;color:#B22222;font-size:small'><b>Profundidad Efectiva: "+depthId+"cm</b></div>");
    $("#flot-placeholder").append("<div style='position:absolute;width:285px;border: 2px dotted #B22222;left:55px;top:"+(o.top)+"px;'></div>");
}

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        width: '250px',
        height: '200px',
        top:  y-50,
        left: x+80,
        'z-index': 100,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '17px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': "Istok Web",
        opacity: 0.9
    }).appendTo("body").fadeIn();
}		

function generateInclination(val) {
    var ctx = $('#canvas')[0].getContext("2d");
    ctx.fillStyle = "#B4CA29";
    ctx.beginPath();
    ctx.moveTo(150,150);
    ctx.lineTo(150,val);
    ctx.lineTo(0,150);
    ctx.fill();			   
}

function chargeInfoTable() {
    var divInfo = document.getElementsByClassName('infoGeneral');
    $.each(divInfo, function(key, elem){
        var valInfo = eval("objResult.valTable[0]."+elem.id);
        $(elem).text(valInfo);
    });
}
		
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