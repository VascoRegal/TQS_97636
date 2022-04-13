import { fetchAll, countries, byCountry } from "./data.js"
import { renderLinearCharts, pre_process } from "./charts.js"

var all_indexed_metrics = {}
var indexed_metrics = {}
var min_date, max_date;

$(document).ready(function() {
    $('#sDate').inputmask('yyyy/mm/dd', { 'placeholder': 'yyyy/mm/dd' })
    $('#eDate').inputmask('mm/dd/yyyy', { 'placeholder': 'yyyy/mm/dd' })
    updateView();
    $('#filter').click(() => {
        filterData();
    })
})


const updateView = function() {
    fetchAll((data) => {
        pre_process(data)
        renderLinearCharts();
        all_indexed_metrics = indexed_metrics;
        loadCardInfo(data);
    })

    countries((data) => {
        populateSelects(data);
    })
}



const loadCardInfo = function(data) {
    var today_data;
    var total_deaths = 0;
    var total_cases = 0;
    var active_cases = 0;
    var balance = 0;

    if (data.length > 0) {
        today_data = data[data.length - 1];

        balance = today_data.cases.balance
        total_deaths = today_data.deaths.total
        total_cases = today_data.cases.total
        active_cases = today_data.cases.active
        max_date = today_data.day;
        min_date = data[0].day;
    }

    $("#totalDeaths").text(balance);
    $("#totalCases").text(total_cases);
    $("#activeCases").text(active_cases);
    $("#today").text(" ( " + max_date + " )");
}

const populateSelects = function(data) {
    var options = [];
    options = options + '<option value="All" selected="selected">All</option>'
    data.forEach(element => {
        options = options + '<option value="'+ element.name +'">'+ element.name +'</option>'
    });
    $("#countrySelect").html(options);
}


const filterData = function() {
    var sDate = $('#sDate').val();
    var eDate = $('#eDate').val();
    var country = $('#countrySelect').val()

    var temp = [];
    if (min_date > sDate || sDate == "") {
        sDate = min_date;
    }
    if (eDate > max_date || eDate == "") {
        eDate = max_date;
    }

    if (sDate > eDate) {

        console.log("invalid dates")      
    } else {

        byCountry(country, (data) => {
            pre_process(data)
            renderLinearCharts(data);

            Object.keys(indexed_metrics).reverse().forEach((item) => {  
                let val = all_indexed_metrics[item]; 
                if (val.day >= sDate && val.day <= eDate) {
                    temp.push(val);
                }
            })
            pre_process(temp);
            renderLinearCharts(temp);
            indexed_metrics = all_indexed_metrics;
        })
    }
}

export {indexed_metrics}