document.addEventListener('DOMContentLoaded', (event) => {
    // 스트레칭 버튼
    const button1 = document.getElementById('delayedButton1');
    function activateButton1() {
        button1.disabled = false;
    }

    function deactivateButton1() {
        button1.disabled = true;
        setTimeout(activateButton1, 1800 * 1000); // 30분 후에 버튼 활성화
    }

    setTimeout(activateButton1, 1800 * 1000); // 페이지 로드 후 30분 뒤 버튼 활성화
    button1.addEventListener('click', deactivateButton1);
    // 수분 보충 버튼
    const button2 = document.getElementById('delayedButton2');
    function activateButton2() {
        button2.disabled = false;
    }

    function deactivateButton2() {
        button2.disabled = true;
        setTimeout(activateButton2, 3600 * 1000); // 1시간 후에 버튼 활성화
    }

    setTimeout(activateButton2, 3600 * 1000); // 페이지 로드 후 1시간 뒤 버튼 활성화
    button2.addEventListener('click', deactivateButton2);
    // 마인드 컨트롤 버튼
    const button3 = document.getElementById('delayedButton3');
    function activateButton3() {
        button3.disabled = false;
    }

    function deactivateButton3() {
        button3.disabled = true;
        setTimeout(activateButton3, 2400 * 1000); // 40분 후에 버튼 활성화
    }

    setTimeout(activateButton3, 2400 * 1000); // 페이지 로드 후 40분 뒤 버튼 활성화
    button3.addEventListener('click', deactivateButton3);
});
