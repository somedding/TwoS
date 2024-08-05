// 모달 열기
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];
var submitBtn = document.getElementById("submitBtn");

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 이름 제출
submitBtn.onclick = function() {
    var name = document.getElementById("nameInput").value;
    alert("Hello, " + name + "!");
    modal.style.display = "none";
}
