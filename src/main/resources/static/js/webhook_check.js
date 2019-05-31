function onWebhookDelete() {
    var csrfToken = document.getElementsByName("_csrf")[0].value;
    postAjax("/deleteWebhook", "_csrf=" + csrfToken, function (data) {
       location.reload();
    });
}

function onDeleteEvent(idx) {
    var event = document.getElementsByTagName("tr")[idx];
    document.getElementById("eventList").removeChild(event);
}

function onAddEvent() {
    var event = document.createElement("tr");
    var index = document.getElementById("sizeField").value;
    document.getElementById("sizeField").value = Number(index) + 1;
    event.innerHTML = "<td>Event name:</td>\n" +
        "                    <td><input type=\"text\" class=\"input-fields\"></td>\n" +
        "                    <td><a onclick=\"onDeleteEvent(" + index + ")\" class=\"button\"><i" +
        " class=\"fas" +
        " fa-trash-alt\"></i></a></td>"
    document.getElementById("eventList").appendChild(event);
}