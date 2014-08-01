var objResult;

function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}

function chargeInfographic(divId) {
    var dataTemp = eval(objResult.values);
    var valCat   = eval(objResult.categories);
    var valOut   = eval(objResult.outlier);
//    $.each(objResult.values, function(index, info) {
//        var pos = index+1;
//        if (index!=(objResult.values.length-1)) {
//            dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" },';		
//        } else {
//            dataTemp += '{ label: "'+pos+'", data: '+info.point+', color: "'+info.color+'" }]';	
//        }
//    });

//    var dataset = [{label: "Rendimientos", data: dataTemp}];
    $(function() {
        $('#'+divId).highcharts({
            chart: {
                type: 'boxplot'
            },
            title: {
                text: 'Rendimientos por produccion'
            },
            legend: {
                enabled: false
            },
            xAxis: {
                categories: valCat,
                title: {
                    text: 'Semestres'
                }
            },
            yAxis: {
                title: {
                    text: 'Observaciones'
                }
                /*,
                plotLines: [{
                        value: 932,
                        color: 'red',
                        width: 1,
                        label: {
                            text: 'Theoretical mean: 932',
                            align: 'center',
                            style: {
                                color: 'gray'
                            }
                        }
                    }]*/
            },
            series: [{
                    name: 'Observaciones',
                    data: dataTemp,
                    tooltip: {
                        headerFormat: '<em>Semestre {point.key}</em><br/>'
                    }
                }, {
                    name: 'Atipicos',
                    color: Highcharts.getOptions().colors[0],
                    type: 'scatter',
                    data: valOut,
                    marker: {
                        fillColor: 'white',
                        lineWidth: 1,
                        lineColor: Highcharts.getOptions().colors[0]
                    },
                    tooltip: {
                        pointFormat: 'Valor: {point.y}'
                    }
                }]

        });
    });
}