import { fetchCache } from "./data.js"


$(document).ready(function() {
    updateView();
})


const updateView = function() {
    fetchCache((data) => {
        loadCards(data)
    })
}

const loadCards = function(data) {
    $("#total").text(data.total)
    $("#cached").text(data.cached)
    $("#avgNoCache").text(data.noCacheTIme)
    $("#avgCache").text(data.cacheTime)
}