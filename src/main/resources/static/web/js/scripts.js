async function fetchData() {
    const response = await fetch('/static/data/someTime.json'); // 변경된 데이터 파일 또는 API URL
    const data = await response.json();
    return data;
}

// 숫자를 카운트업하는 함수
const counter = (counterElement, max) => {
    let now = 0;

    const handle = setInterval(() => {
        counterElement.innerHTML = Math.ceil(now).toLocaleString();

        if (now >= max) {
            clearInterval(handle);
        }

        const step = (max - now) / 10;
        now += step;
    }, 50);
};

// 데이터를 HTML에 적용하고 카운트업 효과를 추가하는 함수
function updateContent(data) {
    data.forEach(item => {
        const counterElement = document.querySelector(`[data-stat-type="${item.statType}"]`);
        if (counterElement) {
            setTimeout(() => counter(counterElement, item.value), 0);
        }
    });
}

// totalMinutes를 업데이트하는 함수
function updateTotalMinutes(minutes) {
    const totalMinutesElement = document.getElementById('totalMinutes');
    totalMinutesElement.setAttribute('data-count', minutes);

    counter(totalMinutesElement, minutes);
}

// 데이터 가져와서 업데이트하는 함수 실행
fetchData().then(updateContent);


// 도넛 차트를 생성하는 함수
function createDonutChart(id, percent, color) {
    const donut = document.getElementById(id);
    donut.dataset.percent = percent;

    const animateDonut = () => {
        let currentPercent = 0;
        const interval = setInterval(() => {
            if (currentPercent <= percent) {
                donut.style.background = `conic-gradient(${color} 0% ${currentPercent}%, #F2F2F2 ${currentPercent}% 100%)`;
                donut.dataset.percent = currentPercent;
                currentPercent++;
            } else {
                clearInterval(interval);
            }
        }, 10);
    };

    // 초기에 애니메이션 실행
    animateDonut();

    // 호버 이벤트 리스너 추가
    donut.addEventListener('mouseenter', animateDonut);
}

// 페이지가 로드되면 데이터를 가져와서 차트를 생성하고 내용을 업데이트
document.addEventListener('DOMContentLoaded', async () => {
    const data = await fetchData();
    updateTotalMinutes(data.totalMinutes); // totalMinutes 업데이트
    createDonutChart('donutChart1', data.percent1, '#3F8BC9'); // 첫 번째 도넛의 색상
    createDonutChart('donutChart2', data.percent2, '#FF5733'); // 두 번째 도넛의 색상
    createDonutChart('donutChart3', data.percent3, '#33FF57'); // 세 번째 도넛의 색상
    updateContent(data); // HTML 내용 업데이트 및 카운트업 효과 추가
});