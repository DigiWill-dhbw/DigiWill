function launchToast(errorText, errorLbl) {
    // errorText is optional
    var x = document.getElementById("toast");
    if (errorText) {
        document.getElementById("desc").appendChild(document.createTextNode(errorText));
    }
    if (errorLbl) {
        document.getElementById("img").innerText = errorLbl;
    }
    x.className = "show";
}
function closeToast() {
    var x = document.getElementById("toast");
    x.className = "disappear";
}