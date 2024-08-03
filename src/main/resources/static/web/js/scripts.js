async function fetchData(email) {
    try {
        // 이메일을 URL에 포함시켜 요청
        const response = await fetch(`http://localhost:8080/api/statistics/some-times?email=${encodeURIComponent(email)}`);
        if (!response.ok) {
            throw new Error('네트워크 응답이 좋지 않습니다: ' + response.statusText);
        }
        const data = await response.json();
        return data; // 전체 데이터 반환
    } catch (error) {
        console.error('데이터를 가져오는 중 오류 발생:', error);
        return {}; // 오류 발생 시 빈 객체 반환
    }
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


function updateContent(data) {
    const counters = document.querySelectorAll('.detail-box h5:nth-child(2)');

    // 통계 항목을 객체로 변환
    const stats = {};
    data.forEach(item => {
        stats[item.stat_type] = parseFloat(item.value) || 0; // stat_type을 키로 사용하여 수치형으로 변환
    });

    const maxValues = [
        stats.minimumWage || 0,
        stats.ktxTrips || 0,
        stats.calorieConsumption || 0,
        stats.oppenheimerViews || 0,
        stats.harryPotterReads || 0,
        stats.handshakes || 0
    ];

    counters.forEach((counterElement, index) => {
        counterElement.innerText = Math.floor(maxValues[index]).toLocaleString(); // 정수로 변환하여 표시
    });
}


// 데이터를 가져와서 업데이트하는 함수
async function init() {
    const data = await fetchData(); // 이메일 없이 데이터 가져오기
    if (data) {
        updateContent(data);
    } else {
        console.error('데이터를 가져오는 데 실패했습니다.');
    }
}

// 초기화 함수 호출
init();

// totalMinutes를 업데이트하는 함수
function updateTotalMinutes(minutes) {
    const totalMinutesElement = document.getElementById('totalMinutes');
    totalMinutesElement.setAttribute('data-count', minutes);

    counter(totalMinutesElement, minutes);
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

// 페이지가 로드되면 데이터를 가져와서 차트를 생성하고 내용을 업데이트
document.addEventListener('DOMContentLoaded', async () => {
    const data = await fetchData();
    updateTotalMinutes(data.totalMinutes); // totalMinutes 업데이트
    createDonutChart('donutChart1', data.percent1, '#3F8BC9'); // 첫 번째 도넛의 색상
    createDonutChart('donutChart2', data.percent2, '#FF5733'); // 두 번째 도넛의 색상
    createDonutChart('donutChart3', data.percent3, '#33FF57'); // 세 번째 도넛의 색상
    updateContent(data); // HTML 내용 업데이트 및 카운트업 효과 추가
});