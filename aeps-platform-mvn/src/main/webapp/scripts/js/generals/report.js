//var dataResult = '{ "detailProfiles" : [{"profile1" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile2" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile3" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>"}], "valTable" : [{"producerId" : "Jose Arana", "valIn" : "Inclinacion: 12%", "depthId" : "Profundidad Efectiva (cm) - 110", "phId" : "5", "structureId" : "Granular", "exposeId" : "La Mañana y Tarde", "coveringId" : "Bueno", "drainIntId" : "Bueno", "drainExtId" : "Lento"}], "data" : [{"point" :"[[1, -20]]", "color": "#0077FF"},{"point" :"[[1, -40]]", "color": "#7D0096"},{"point" :"[[1, -60]]", "color": "#DE000F"}], "infoProfile" : [{"pro1": "A","pro2": "F","pro3": "L"}] }';
//var dataResult;
//var objResult  = JSON.parse(dataResult);
var objResult;

function dregreesToRadian(rad) {
    return rad * 180 / Math.PI; 
}

//function chargeInfographic(dataResult) {			
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
//            barWidth: 124 * 60 * 60 * 2000,
            barWidth: 124 * 60 * 60,
            lineWidth: 5
        },
        legend: {
            show: false
        },
        xaxis: {
            tickLength: 1,
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
            axisLabelPadding: 3
        },
        grid: {
            hoverable: true,
            borderWidth: 2,
            backgroundColor: { colors: ["#EDF5FF", "#ffffff"] }
        }
        //,colors:["#004078","#207800", "#613C00"]
    };   
//    $(".flot-text").css( "left", "-14px" );    

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
//                    alert(item.pageX)
//                    alert(item.pageY)
                    showTooltip(item.pageX, item.pageY, color, text);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
                findInfoSoil("neutral");
            }
        });
    };
    
//    $(document).ready(function () {
        $.plot($("#flot-placeholder"), dataset, options);    
        $("#flot-placeholder").UseTooltip();
//    });

//    $(".legendColorBox").addClass("hide");
//    $(".legendLabel").addClass("hide");
}

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        width: '250px',
        height: '120px',
        top: (y/2),
        left: (x/2)-80,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '17px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': "Istok Web",
        opacity: 0.9
    }).appendTo("#dialog-form").fadeIn();
}		

function generateInclination(val) {
    var ctx = $('#canvas')[0].getContext("2d");
    ctx.fillStyle = "#00A308";
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