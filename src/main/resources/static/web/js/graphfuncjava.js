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

// 차트를 업데이트하는 함수
async function updateChart() {
    const userData = await fetchAllData();
    if (!userData) {
        return; // 사용자 데이터를 불러오지 못하면 함수를 종료
    }

    const dailyAverageUsage = userData.dailyAverageUsage;

    const labels = dailyAverageUsage.map(entry => entry.date);
    const data = dailyAverageUsage.map(entry => entry.averageUsageTime/60);

    const chartData = {
        labels: labels,
        datasets: [{
            data: data,
            label: "Daily Average Usage",
            borderColor: "#3e95cd",
            fill: false
        }]
    };

    myChart.data = chartData;
    myChart.update();
}

const ctx = document.getElementById("line-chart").getContext("2d");
const myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [],  // Initialize with empty data
        datasets: []
    },
    options: {
        title: {
            display: true,
            text: 'Daily Average Internet Usage'
        }
    }
});

updateChart();