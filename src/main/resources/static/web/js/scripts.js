// 데이터를 가져오는 함수
async function fetchData() {
    const response = await fetch('/static/data.json'); // 데이터가 저장된 파일 또는 API URL
    const data = await response.json();
    return data;
}

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

// totalMinutes를 업데이트하는 함수
function updateTotalMinutes(minutes) {
    const totalMinutesElement = $('#totalMinutes');
    totalMinutesElement.attr('data-count', minutes);

    $({ countNum: 0 }).animate({
        countNum: minutes
    }, {
        duration: 3000,
        easing: 'linear',
        step: function() {
            totalMinutesElement.text(Math.floor(this.countNum));
        },
        complete: function() {
            totalMinutesElement.text(this.countNum);
        }
    });
}



// 페이지가 로드되면 데이터를 가져와서 차트를 생성
document.addEventListener('DOMContentLoaded', async () => {
    const data = await fetchData();
    updateTotalMinutes(data.totalMinutes); // totalMinutes 업데이트
    createDonutChart('donutChart1', data.percent1, '#3F8BC9'); // 첫 번째 도넛의 색상
    createDonutChart('donutChart2', data.percent2, '#FF5733'); // 두 번째 도넛의 색상
    createDonutChart('donutChart3', data.percent3, '#33FF57'); // 세 번째 도넛의 색상
});