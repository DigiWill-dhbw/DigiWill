function openLogin(){
    window.history.replaceState(null,null,window.location.pathname+"?login");
    document.getElementById("login").style.display = "block";
}

function closeLogin(btnClick, event) {
    // Get the modal
    var modal = document.getElementById("login");
    if (btnClick || event.target === modal) {
        modal.style.display = "none";
        var elem = document.getElementById("loginError");
        if(elem!=null){
            elem.remove();
        }
        window.history.replaceState(null,null,window.location.pathname);
    }
}

document.addEventListener("DOMContentLoaded", function (ev) {
    var url = new URL(window.location.href);
    if (url.searchParams.get("login") != null) {
        document.getElementById("login").style.display = "block";
    }
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    closeLogin(false, event);
};