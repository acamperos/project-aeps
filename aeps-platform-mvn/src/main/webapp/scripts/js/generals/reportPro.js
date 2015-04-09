//var dataResult = '{ "detailProfiles" : [{"profile1" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile2" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>", "profile3" : "<center>Horizonte 1:</center><p>Espesor: 30cm, <br/>Resistencia al rompimiento : Friable, <br/>Materia organica : MEDIA</p>"}], "valTable" : [{"producerId" : "Jose Arana", "valIn" : "Inclinacion: 12%", "depthId" : "Profundidad Efectiva (cm) - 110", "phId" : "5", "structureId" : "Granular", "exposeId" : "La Mañana y Tarde", "coveringId" : "Bueno", "drainIntId" : "Bueno", "drainExtId" : "Lento"}], "data" : [{"point" :"[[1, -20]]", "color": "#0077FF"},{"point" :"[[1, -40]]", "color": "#7D0096"},{"point" :"[[1, -60]]", "color": "#DE000F"}], "infoProfile" : [{"pro1": "A","pro2": "F","pro3": "L"}] }';
//var dataResult;
//var objResult  = JSON.parse(dataResult);
var objResult;

function gd(year, month, day) {
    return new Date(month+"/"+day+"/"+year).getTime();
}
			
function chargeInfographic() {			
//    var dataTemp	 = [[gd(2013,7,30), 5384.0],[gd(2014,2,2), 9060.0]];
//    var dataTemp	 = [[1375160400000, 5384.0],[1391317200000, 9060.0]];
    var dataTemp = eval(objResult.values);
//    var dataTemp	 = [["2013/06/26", 7811.0]];
//    alert(dataTemp)
//    $.each(objResult.values, function(index, info) {
//        var pos = index+1;
//        if (index!=(objResult.values.length-1)) {
//            dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" },';		
//        } else {
//            dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" }]';	
//        }
//    });
//    for (var i = 0; i < dataTemp.length; ++i) {dataTemp[i][0] += 60 * 120 * 1000};
//    alert(new Date('7/30/2013').getTime())
    var dataset = [{ label: "Rendimientos", data: dataTemp}];
    var options = {
        series: {
            lines: {
                show: true
            },
            points: {
                radius: 3,
                fill: true,
                show: true
            }
        },
        xaxis: {
            mode: "time",
            tickSize: [30, "day"],
            timeformat: "%m/%Y",
//            timezone: "browser", // format string to use
//            useLocalTime: true,
//            timeZoneOffset: (new Date()).getTimezoneOffset(),
//            localTimezone: true,
//            labelWidth: "40",
//            mode: "time",
//            tickLength: 0,
            axisLabel: "Tiempo",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Istok Web',
            axisLabelPadding: 10
        },
        yaxes: {
            axisLabel: "Rendimientos",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Istok Web',
            axisLabelPadding: 3
        },
        zoom: {
            interactive: true
        },
        legend: {
            noColumns: 0,
            labelBoxBorderColor: "#000000",
            position: "nw"
        },
        grid: {
            hoverable: true,
            borderWidth: 2,
            borderColor: "#633200",
            backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
        },
        colors: ["#FF0000", "#0022FF"]
    };


    var previousPoint = null, previousLabel = null;
    var monthNames = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"];

    $.fn.UseTooltip = function () {
        $(this).bind("plothover", function (event, pos, item) {
            if (item) {
                if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                    previousPoint = item.dataIndex;
                    previousLabel = item.series.label;
                    $("#tooltip").remove();

                    var x = item.datapoint[0];
                    var y = item.datapoint[1];
                    
                    var text  = eval("objResult.format[0].set"+x);
//                    alert(text)
                    var color = item.series.color;
                    var day   = new Date(x).getDay();
                    var month = new Date(x).getMonth();
                    var year  = new Date(x).getFullYear();
                    showTooltip(item.pageX,
                            item.pageY,
                            color,
                            "<strong> Rendimiento </strong><br>"+monthNames[month]+" "+text+" : <strong>"+y+"</strong>");
//                    showTooltip(item.pageX, item.pageY, color, "<strong>" + x + " : <strong>" + y + "</strong>");
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });
    };
    
    $(document).ready(function () {
        $.plot($("#flot-placeholder"), dataset, options);    
        $("#flot-placeholder").UseTooltip();
    });
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
        'font-family': "Istok Web",
        opacity: 0.9
    }).appendTo("body").fadeIn();
}
