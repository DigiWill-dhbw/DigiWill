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
    console.log(response);
    if(!response) {
        document.getElementById("addressField").style = "border-color: red;";
    } else {
        document.getElementById("addressField").style = "border-color: #ddd;";
    }
}