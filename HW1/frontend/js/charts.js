import { indexed_metrics } from "./index.js";

var dates = [];  
var total_cases = []; var balance_cases = []; var critical_cases = []; var active_cases = []; var recovered_cases = []; var per_million_cases = []; 
var balance_deaths = []; var per_million_deaths = []; var total_deaths = [];
var total_tests = []; var per_million_tests = [];

var mainChart, caseChart, deathChart, testChart;

const pre_process = function(data) {
    dates = [];
    total_cases = []; balance_cases = []; critical_cases = []; recovered_cases = []; per_million_cases = []; active_cases = [];
    balance_deaths = []; per_million_deaths = []; per_million_deaths = [];
    total_tests = []; per_million_tests = [];


    data.reverse().forEach(function(item) {
        dates.push(item.day);

        total_cases.push(item.cases.total);
        balance_cases.push(item.cases.balance);
        active_cases.push(item.cases.active);
        critical_cases.push(item.cases.critical);
        recovered_cases.push(item.cases.recovered);
        per_million_cases.push(item.cases.per_million);

        total_deaths.push(item.deaths.total);
        per_million_deaths.push(item.deaths.per_million);
        balance_deaths.push(item.deaths.balance);

        total_tests.push(item.tests.total);
        per_million_tests.push(item.tests.per_million);
        indexed_metrics[item.day] = item;
    });
}


const renderLinearCharts = function() {
    var linearChartData = {
        labels: dates,
        datasets: [
            {
                label: 'Active Cases',
                borderColor: '#bf8c00',
                data: active_cases,
            },
            {
                label: 'Total Deaths',
                borderColor: '#962100',
                data: total_deaths
            },
            {
                label: 'Total Tests',
                borderColor: '#197300',
                data: total_tests
            }
        ]
    }

    if (mainChart == null) {
        mainChart = renderChart("#linear", linearChartData, {})
    }
    else {
        mainChart.config.data = $.extend(true, {},linearChartData);
        mainChart.update()
    }

    var linearChartData = {
        labels: dates,
        datasets: [
            {
                label: 'Active Cases',
                borderColor: '#bf8c00',
                data: active_cases
            },
            {
                label: 'New Cases',
                borderColor: '#939922',
                data: balance_cases
            }, 
            {
                label: 'Critical Cases',
                borderColor: '#7d1b20',
                data: critical_cases
            }, 
            {
                label: 'Recovered Cases',
                borderColor: '#2f8043',
                data: recovered_cases
            }, 
            {
                label: 'Per 1M Population',
                borderColor: '#310d94',
                data: per_million_cases
            },
        ]
    }



    if (caseChart == null) {
        caseChart = renderChart("#casesLinear", linearChartData, {})
    }
    else {
        caseChart.config.data = $.extend(true, {},linearChartData);
        caseChart.update()
    }


    var linearChartData = {
        labels: dates,
        datasets: [
            /*
            {
                label: 'Total Deaths',
                borderColor: '#bf8c00',
                data: total_deaths
            },
            */
            {
                label: 'New Deaths',
                borderColor: '#939922',
                data: balance_deaths
            }, 
            {
                label: 'Per 1M Population',
                borderColor: '#7d1b20',
                data: per_million_deaths
            }, 
        ]
    }

    if (deathChart == null) {
        deathChart = renderChart("#deathsLinear", linearChartData, {})
    } else {
        deathChart.config.data = $.extend(true, {},linearChartData);
        deathChart.update()
    }

    var linearChartData = {
        labels: dates,
        datasets: [
            {
                label: 'Total Tests',
                borderColor: '#bf8c00',
                data: total_tests
            },
            {
                label: 'Per 1M Population',
                borderColor: '#939922',
                data: per_million_tests
            }, 
        ]
    }

    if (testChart == null) {
        testChart = renderChart("#testsLinear", linearChartData, {})
    } else {
        testChart.config.data = $.extend(true, {},linearChartData);
        testChart.update()
    }
}

const renderChart = function(id, data, options) {
    var barChartCanvas = $(id).get(0).getContext('2d');
    var barChartData = $.extend(true, {}, data)

    var mode = 'index'
    var intersect = true
    var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'normal'
    }

    var barChartOptions = {
        responsive              : true,
        maintainAspectRatio     : false,
        datasetFill             : false,
        tooltips: {
            mode: mode,
            intersect: intersect
        },
        hover: {
            mode: mode,
            intersect: intersect
        },
        legend: {
            display: false
        },
        scales: {
            scaleStartValue: 0,
            yAxes: [{
                gridLines: {
                    display: true,
                    lineWidth: '1px',
                    color: 'rgba(0, 0, 0, .2)',
                    zeroLineColor: 'transparent'
                },
                ticks: {
                    beginAtZero: true
                }
            }],
            xAxes: [{
                display: true,
                gridLines: {
                    display: false
                },
                ticks: ticksStyle
            }],
           
        },
        plugins: {
            title: {
                display: true,
                text: 'Total Cases, Deaths and Tests'
            }
        }
    }

    var barChartOptions = Object.assign({}, barChartOptions, options);

    return new Chart(barChartCanvas, {
        type: 'line',
        data: barChartData,
        options: barChartOptions
    })
}

export {renderLinearCharts, pre_process}