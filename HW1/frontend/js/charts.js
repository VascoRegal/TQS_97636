import { getAll } from "./data.js"


$(document).ready(function() {
    updateView();
})


const updateView = function() {
    getAll((data) => {
        renderLinearChart(data);
        loadCardInfo(data);
    })
}



const loadCardInfo = function(data) {
    var today_data;
    var total_deaths = 0;
    var total_cases = 0;
    var active_cases = 0;
    var today = "";

    if (data.length > 0) {
        today_data = data[data.length - 1];

        total_deaths = today_data.deaths.total
        total_cases = today_data.cases.total
        active_cases = today_data.cases.active
        today = today_data.day;
    }

    $("#totalDeaths").text(total_deaths);
    $("#totalCases").text(total_cases);
    $("#activeCases").text(active_cases);
    $("#today").text(" ( " + today + " )");
}
 
const renderLinearChart = function(data) {
    var dates = []; var cases = []; var deaths = []; var tests = [];

    data.reverse().forEach(function(item) {
        dates.push(item.day);
        cases.push(item.cases.total);
        deaths.push(item.deaths.total);
        tests.push(item.tests.total);
    });

    var linearChartData = {
        labels: dates,
        datasets: [
            {
                label: 'Total Cases',
                borderColor: '#bf8c00',
                data: cases
            },
            {
                label: 'Total Deaths',
                borderColor: '#962100',
                data: deaths
            },
            {
                label: 'Total Tests',
                borderColor: '#197300',
                data: tests
            }
        ]
    }

    var barChartCanvas = $("#linear").get(0).getContext('2d');
    var barChartData = $.extend(true, {}, linearChartData)
    var mode = 'index'
    var intersect = true
    var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
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
                    lineWidth: '2px',
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
                text: 'Total Cases, Deaths and Tests (Worldwide)'
            }
        }
    }

    new Chart(barChartCanvas, {
        type: 'line',
        data: barChartData,
        options: barChartOptions
    })
}