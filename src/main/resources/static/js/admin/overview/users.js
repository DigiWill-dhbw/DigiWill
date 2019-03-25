var span;
var modal;

function onDelete(usr) {
    // Get the <span> element that closes the modal
    modal.style.display = "block";
    document.getElementById("deleteButton").value =  usr;
}

function onLoad() {
    modal = document.getElementById('deleteModal');
    // When the user clicks on <span> (x), close the modal
    span = document.getElementById("closeButton");
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}