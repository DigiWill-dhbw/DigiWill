function launchToast(errorText) {
    // errorText is optional
    var x = document.getElementById("toast");
    if (errorText) {
        document.getElementById("desc").appendChild(document.createTextNode(errorText));
    }
    x.className = "show";
}
function closeToast() {
    var x = document.getElementById("toast");
    x.className = "disappear";
}