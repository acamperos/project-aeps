var objResult;

function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}

function chargeInfographic(divId) {
    var dataTemp = eval(objResult.values);
    var valCat   = eval(objResult.categories);
    var valOut   = eval(objResult.outlier);
    $(function() {
        $('#'+divId).highcharts({
            chart: {
                type: 'boxplot'
            },
            title: {
                text: 'Rendimientos por producción'
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
                    text: 'Rendimientos'
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
            plotOptions: {
                boxplot: {
                    fillColor: '#F0F0E0',
                    lineWidth: 2,
                    medianColor: '#0C5DA5',
                    medianWidth: 3,
                    stemColor: '#A63400',
                    stemDashStyle: 'dot',
                    stemWidth: 1,
                    whiskerColor: '#3D9200',
                    whiskerLength: '20%',
                    whiskerWidth: 3 
                }
            },
            series: [
                    {
                        name: 'Observaciones',
                        data: dataTemp,
                        tooltip: {
                            headerFormat: '<em>Semestre {point.key}</em><br/>',
                            pointFormat:'<span style="color:{series.color}">●</span> <b> {series.name}</b><br/>Maximo: {point.high}<br/>Cuartil superior: {point.q3}<br/>Mediana: {point.median}<br/>Cuartil inferior: {point.q1}<br/>Minimo: {point.low}<br/>'
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
                    }
            ]

        });
    });
}