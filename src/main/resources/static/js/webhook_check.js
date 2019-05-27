function onWebhookDelete() {
    var csrfToken = document.getElementsByName("_csrf")[0].value;
    postAjax("/deleteWebhook", "_csrf=" + csrfToken, function (data) {
       location.reload();
    });
}