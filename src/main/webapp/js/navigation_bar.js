function responsiveTopNavBar(){
    var x = document.getElementById("topnavbar");
    if (x.className ==="navbar"){
        x.className += " responsive";
    } else {
        x.className = "navbar";
    }
}

function dropListExpand() {
    document.getElementById("dropdownlist").classList.toggle("show");

}

window.onclick = function (e) {
    if (!e.target.matches(".dropbtn")){
        var dropDown = document.getElementById("dropdownlist");
        if (dropDown.classList.contains('show')){
            dropDown.remove('show');
        }
    }
}
