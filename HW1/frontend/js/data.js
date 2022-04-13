


const BASE_URI = "http://localhost:8080/api";

var metrics;

const fetchAll = function(callback) {
    const uri = BASE_URI + "/stats";

    $.ajax({
        url: uri,
        crossDomain: true,
        type: "GET",
        contentType: "application/json",
        success: function(data) {
            callback(data);
        },
        error: function() {
            console.log("smtn went wrong");
        }
    })
}

const byCountry = function(country, callback) {
    const uri = BASE_URI + "/stats/country?country=" + country;

    $.ajax({
        url: uri,
        crossDomain: true,
        type: "GET",
        contentType: "application/json",
        success: function(data) {
            callback(data);
        },
        error: function() {
            console.log("smtn went wrong");
        }
    })
}

const countries = function(callback) {
    const uri = BASE_URI + "/countries";

    $.ajax({
        url: uri,
        crossDomain: true,
        type: "GET",
        contentType: "application/json",
        success: function(data) {
            callback(data);
        },
        error: function() {
            console.log("smtn went wrong");
        }
    })
}


export { fetchAll, countries, byCountry }