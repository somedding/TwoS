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
submitBtn.onclick = async function() {
    var friendEmail = document.getElementById("nameInput").value;

    try {
        const response = await fetch('/api/friends/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(friendEmail)
        });


        if (response.ok) {
            const jsonResponse = await response.json();
            console.log('Success:', jsonResponse);
            modal.style.display = "none";
        } else {
            console.error('Error:', response.statusText);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
