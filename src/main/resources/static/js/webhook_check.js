function onWebhookDelete() {
    var csrfToken = document.getElementsByName("_csrf")[0].value;
    postAjax("/deleteWebhook", "_csrf=" + csrfToken, function (data) {
       location.reload();
    });
}

function onAddEvent() {
    var event = document.createElement("tr");
    event.innerHTML = "<td>Event name:</td>\n" +
        "                    <td><input type=\"text\" class=\"input-fields\"></td>\n";
    document.getElementById("eventList").appendChild(event);
}

function onSetWebhook() {
    var csrfToken = document.getElementsByName("_csrf")[0].value;
    var events = document.getElementsByTagName("tr");
    events = Array.from(events);
    var eventNames = [];
    events.forEach(function(event) {
        eventNames.push(event.getElementsByTagName("td")[1].getElementsByClassName("input-fields")[0].value);
    });
    var eventString = eventNames.join(";");
    var apiKey = document.getElementById("apikey").value;
    postAjax("/webhook", "_csrf=" + csrfToken + "&apiKey=" + apiKey + "&eventNames=" + eventString, function (data) {
        location.reload();
    });
}