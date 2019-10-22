import 'https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js'
import 'https://code.highcharts.com/highcharts.js'
import 'https://code.highcharts.com/modules/data.js'
import 'https://code.highcharts.com/modules/exporting.js'
import 'https://code.highcharts.com/modules/export-data.js'

function buildBillPlot(xArgs, yArgs) {
    Highcharts.chart('container', {
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: 'Transaction dinamic'
        },
        subtitle: {
            text: document.ontouchstart === undefined ?
                'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
        },
        xAxis: {
            categories: xArgs,
            crosshair: true
        },
        yAxis: {
            title: {
                text: 'Exchange rate'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        series: [{
            type: 'area',
            name: 'bill value',
            data: yArgs
        }]
    });
}