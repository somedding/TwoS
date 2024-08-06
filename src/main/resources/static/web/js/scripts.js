// URL 파라미터에서 이메일 추출
function getEmailFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('email');
}

// JSON 파일에서 사용자 데이터를 불러오는 함수
async function fetchUserData(email) {
    try {
        const response = await fetch(`/static/data/all_${email}.json`); // 이메일에 맞는 JSON 파일 경로
        if (!response.ok) {
            throw new Error(`사용자 데이터를 불러오는 데 실패했습니다: ${response.status}`);
        }
        const data = await response.json();
        console.log('User Data:', data); // 디버깅 출력
        return data;
    } catch (error) {
        console.error('Error fetching user data:', error);
        return null; // 오류 발생 시 null 반환
    }
}

// 이메일을 포함한 URL로 리다이렉트하는 함수
function redirectWithEmail(url) {
    const email = getEmailFromUrl();
    if (email) {
        window.location.href = `${url}?email=${email}`;
    } else {
        window.location.href = url;
    }
}

// 모든 데이터를 불러오는 함수
async function fetchAllData() {
    const email = getEmailFromUrl(); // URL에서 이메일 추출
    if (!email) {
        console.error('Email parameter is missing in the URL');
        return null; // 이메일이 없으면 null 반환
    }

    return await fetchUserData(email); // 사용자 데이터 반환
}

// 카운터 함수
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

// 콘텐츠 업데이트 함수
function updateContent(data) {
    data.forEach(item => {
        const counterElement = document.querySelector(`[data-stat-type="${item.statType}"]`);
        if (counterElement) {
            setTimeout(() => counter(counterElement, item.value), 0);
        }
    });
}

// 총 분 업데이트 함수
function updateTotalMinutes(minutes) {
    const totalMinutesElement = document.getElementById('totalMinutes');
    totalMinutesElement.setAttribute('data-count', minutes);
    counter(totalMinutesElement, minutes);
}

// 도넛 차트 생성 함수
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

// DOMContentLoaded 이벤트 리스너
document.addEventListener('DOMContentLoaded', async () => {
    const allData = await fetchAllData(); // 사용자 데이터 가져오기
    if (!allData) {
        console.error('사용자 데이터를 가져오는 데 문제가 발생했습니다.');
        return; // 데이터가 없으면 종료
    }

    const someTimeData = allData.someTimes || [];
    const totalMinutesData = allData.totalMinutes || { totalMinutes: 0 };
    const screenTimeData = allData.screenTime || [];

    const videoData = screenTimeData.find(item => item.category === 'Video') || { percentage: 0 };
    const searchData = screenTimeData.find(item => item.category === 'Search') || { percentage: 0 };
    const mediaData = screenTimeData.find(item => item.category === 'Media') || { percentage: 0 };
    const etcData = screenTimeData.find(item => item.category === 'etc') || { percentage: 0 };

    updateTotalMinutes(totalMinutesData.totalMinutes / 60);
    createDonutChart('donutChart1', videoData.percentage, 'c9b3d8');
    createDonutChart('donutChart2', searchData.percentage, 'f6cadc');
    createDonutChart('donutChart3', mediaData.percentage, 'f2b3cb');
    createDonutChart('donutChart4', etcData.percentage, 'c0dffb');
    updateContent(someTimeData);
});
