// JSON 파일에서 데이터를 불러오는 함수
async function fetchAllData() {
    const response = await fetch('/static/data/all.json'); // 경로는 실제 파일 위치로 수정
    const data = await response.json();
    console.log('All Data:', data); // 디버깅 출력
    return data;
}

const counter = (counterElement, max) => {
    let now = 0;
    const handle = setInterval(() => {
        counterElement.innerHTML = now.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
        if (now >= max) {
            counterElement.innerHTML = max.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
            clearInterval(handle);
        }
        const step = (max - now) / 10;
        now += step;
    }, 50);
};

function updateContent(data) {
    data.forEach(item => {
        const counterElement = document.querySelector(`[data-stat-type="${item.statType}"]`);
        if (counterElement) {
            setTimeout(() => counter(counterElement, item.value), 0);
        }
    });
}

function updateTotalMinutes(minutes) {
    const totalMinutesElement = document.getElementById('totalMinutes');
    totalMinutesElement.setAttribute('data-count', minutes);
    counter(totalMinutesElement, minutes);
}

function createDonutChart(id, percent, color) {
    const donut = document.getElementById(id);
    donut.dataset.percent = percent;

    const animateDonut = () => {
        let currentPercent = 0;
        const interval = setInterval(() => {
            if (currentPercent <= percent) {
                donut.style.background = `conic-gradient(#${color} 0% ${currentPercent}%, #F2F2F2 ${currentPercent}% 100%)`;
                donut.dataset.percent = currentPercent;
                currentPercent++;
            } else {
                clearInterval(interval);
            }
        }, 10);
    };

    animateDonut();
    donut.addEventListener('mouseenter', animateDonut);
}

document.addEventListener('DOMContentLoaded', async () => {
    const allData = await fetchAllData();
    const someTimeData = allData.someTimes;
    const totalMinutesData = allData.totalMinutes;
    const screenTimeData = allData.screenTime;

    const videoData = screenTimeData.find(item => item.category === 'Video');
    const searchData = screenTimeData.find(item => item.category === 'Search');
    const mediaData = screenTimeData.find(item => item.category === 'Media');
    const etcData = screenTimeData.find(item => item.category === 'etc');

    updateTotalMinutes(totalMinutesData.totalMinutes / 60);
    createDonutChart('donutChart1', videoData ? videoData.percentage : 0, 'c9b3d8');
    createDonutChart('donutChart2', searchData ? searchData.percentage : 0, 'f6cadc');
    createDonutChart('donutChart3', mediaData ? mediaData.percentage : 0, 'f2b3cb');
    createDonutChart('donutChart4', etcData ? etcData.percentage : 0, 'c0dffb');
    updateContent(someTimeData);
});
