function checkEmail(email) {
    email = email.split(" ");
    response = true;
    email.forEach(function(e) {
        if (e !== "") {
            response = response && checkIfEmailInString(e);
        }
    });
    console.log(response);
    if(!response) {
        document.getElementById("adressfield").style = "border-color: red;";
    } else {
        document.getElementById("adressfield").style = "border-color: #ddd;";
    }
}
function checkIfEmailInString(text) {
    var re = /(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/;
    return re.test(text);
}