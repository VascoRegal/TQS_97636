


const BASE_URI = "http://localhost:8080/api";

const getAll = function(callback) {
    const uri = BASE_URI + "/stats";

    $.ajax({
        url: uri,
        crossDomain: true,
        type: "GET",
        contentType: "application/json",
        success: function(data) {
            console.log(data);
            callback(data);
        },
        error: function() {
            console.log("smtn went wrong");
        }
    })
}

export { getAll }