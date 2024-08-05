document.addEventListener('DOMContentLoaded', (event) => {
    // 스트레칭 버튼
    const button1 = document.getElementById('delayedButton1');
    const counter1 = document.getElementById('counter1'); // 숫자 표시할 요소

    let count1 = 0;

    function activateButton1() {
        button1.disabled = false;
    }

    function deactivateButton1() {
        button1.disabled = true;
        setTimeout(activateButton1, 1800 * 1000); // 30분 후에 버튼 활성화
    }

    setTimeout(activateButton1, 1800 * 1000); // 페이지 로드 후 30분 뒤 버튼 활성화
    button1.addEventListener('click', () => {
        count1 += 1;
        counter1.textContent = count1; // 숫자 업데이트
        deactivateButton1();
    });

    // 수분 보충 버튼
    const button2 = document.getElementById('delayedButton2');
    const counter2 = document.getElementById('counter2'); // 숫자 표시할 요소

    let count2 = 0;

    function activateButton2() {
        button2.disabled = false;
    }

    function deactivateButton2() {
        button2.disabled = true;
        setTimeout(activateButton2, 3600 * 1000); // 1시간 후에 버튼 활성화
    }

    setTimeout(activateButton2, 3600 * 1000); // 페이지 로드 후 1시간 뒤 버튼 활성화
    button2.addEventListener('click', () => {
        count2 += 1;
        counter2.textContent = count2; // 숫자 업데이트
        deactivateButton2();
    });

    // 마인드 컨트롤 버튼
    const button3 = document.getElementById('delayedButton3');
    const counter3 = document.getElementById('counter3'); // 숫자 표시할 요소

    let count3 = 0;

    function activateButton3() {
        button3.disabled = false;
    }

    function deactivateButton3() {
        button3.disabled = true;
        setTimeout(activateButton3, 2400 * 1000); // 40분 후에 버튼 활성화
    }

    setTimeout(activateButton3, 2400 * 1000); // 페이지 로드 후 40분 뒤 버튼 활성화
    button3.addEventListener('click', () => {
        count3 += 1;
        counter3.textContent = count3; // 숫자 업데이트
        deactivateButton3();
    });
});
