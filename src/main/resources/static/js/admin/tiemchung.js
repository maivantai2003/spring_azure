$("#searchButton").click(function () {
    performSearch();
});

$("#searchInput").keypress(function (event) {
    if (event.which === 13) {
        event.preventDefault();
        performSearch();
    }
});

function performSearch() {
    var query = $("#searchInput").val();
    window.location.href = "/admin/tiemchung?query=" + query;
}