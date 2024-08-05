async function fetchScreenTimeData() {
    const response = await fetch('/static/data/screenTime.json'); // New function for screenTime data
    const data = await response.json();
    return data;
}

async function fetchData() {
    const response = await fetch('/static/data/someTime.json'); // Original data function
    const data = await response.json();
    return data;
}

async function fetchTotalMinutes() {
    const response = await fetch('/static/data/data.json');
    const data = await response.json();
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

fetchData().then(updateContent);
fetchTotalMinutes().then(updateContent);

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
    const data = await fetchData();
    const minData = await fetchTotalMinutes();
    const screenTimeData = await fetchScreenTimeData();

    const videoData = screenTimeData.find(item => item.category === 'Video');
    const searchData = screenTimeData.find(item => item.category === 'Search');
    const mediaData = screenTimeData.find(item => item.category === 'Media');
    const etcData = screenTimeData.find(item => item.category === 'etc');

    updateTotalMinutes(minData[0].totalMinutes / 60);
    createDonutChart('donutChart1', videoData.percentage, 'c9b3d8');
    createDonutChart('donutChart2', searchData.percentage, 'f6cadc');
    createDonutChart('donutChart3', mediaData.percentage, 'f2b3cb');
    createDonutChart('donutChart4', etcData.percentage, 'c0dffb');
    updateContent(data);
});