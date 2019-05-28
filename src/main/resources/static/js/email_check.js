var currentDeleteIdx;

function checkIfEmailInString(text) {
    var re = /(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/;
    return re.test(text);
}

function checkEmail(email) {
    var emailArray = email.split(" ");
    var response = true;
    emailArray.forEach(function(e) {
        if (e !== "") {
            response = response && checkIfEmailInString(e);
        }
    });
    if(!response) {
        document.getElementById("addressField").style = "border-color: red;";
    } else {
        document.getElementById("addressField").style = "border-color: #ddd;";
    }
}
function onDelete(idx) {
    // Get the <span> element that closes the modal
    modal.style.display = "block";
    if(typeof(idx) !== "undefined") {
        currentDeleteIdx = idx;
        document.getElementById("indexField").value = idx;
    }
}
function onConfirmDelete() {
    var csrfToken = document.getElementsByName("_csrf")[0].value;
    postAjax("/deleteEmail", "_csrf=" + csrfToken + "&idx=" + currentDeleteIdx, function (data) {
        window.location.href = "/getEmails";
    });
}