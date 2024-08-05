document.addEventListener('DOMContentLoaded', function() {
    const updateInterval = 3600000; // 1시간 (밀리초 단위)
    const maxDataPoints = 30; // 최대 데이터 포인트 수 (예: 한 달 기준)

    let chart;
    let dataPoints = {
        daily: [],
        weekly: [],
        monthly: []
    };
    let labels = {
        daily: [],
        weekly: [],
        monthly: []
    };

    // 초기 데이터 로드
    function fetchData() {
        return fetch('data.json')
            .then(response => response.json())
            .catch(error => console.error('Error loading the data:', error));
    }

    // 시간 단위 설정
    function generateLabels(period, length) {
        const labels = [];
        switch (period) {
            case 'daily':
                for (let i = 0; i < length; i++) {
                    labels.push(`${i}:00`);
                }
                break;
            case 'weekly':
                for (let i = 0; i < length; i++) {
                    labels.push(`Day ${i + 1}`);
                }
                break;
            case 'monthly':
                for (let i = 0; i < length; i++) {
                    labels.push(`Day ${i + 1}`);
                }
                break;
        }
        return labels;
    }

    // 차트 생성 및 초기 설정
    function createChart() {
        const ctx = document.getElementById('lineChart').getContext('2d');
        chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: 'Usage Time',
                    data: [],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    fill: false
                }]
            },
            options: {
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Time / Day'
                        },
                        grid: {
                            display: false // x축 격자선 제거
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Minutes'
                        },
                        grid: {
                            display: false // y축 격자선 제거
                        }
                    }
                }
            }
        });
    }

    // 차트 데이터 업데이트
    function updateChart(period) {
        fetchData().then(data => {
            const totalMinutes = data.totalMinutes;
            const length = period === 'daily' ? 24 : period === 'weekly' ? 7 : 30;
            const newLabels = generateLabels(period, length);

            // 데이터 포인트 추가
            dataPoints[period].push(totalMinutes / length);
            if (dataPoints[period].length > length) {
                dataPoints[period].shift(); // 가장 오래된 데이터 삭제
            }

            chart.data.labels = newLabels.slice(0, dataPoints[period].length);
            chart.data.datasets[0].data = dataPoints[period];
            chart.update();

            // 버튼 활성화 상태 업데이트
            document.querySelectorAll('.button').forEach(button => {
                button.classList.remove('active');
            });
            document.querySelector(`button[onclick="showChart('${period}')"]`).classList.add('active');
        });
    }

    // 차트 전환 함수
    window.showChart = function(period) {
        const length = period === 'daily' ? 24 : period === 'weekly' ? 7 : 30;
        chart.data.labels = generateLabels(period, length);
        chart.data.datasets[0].data = dataPoints[period];
        chart.update();

        // 버튼 활성화 상태 업데이트
        document.querySelectorAll('.button').forEach(button => {
            button.classList.remove('active');
        });
        document.querySelector(`button[onclick="showChart('${period}')"]`).classList.add('active');
    };

    // 초기 차트 생성 및 설정
    createChart();
    showChart('daily');

    // 주기적으로 데이터 갱신
    setInterval(() => {
        const activePeriod = document.querySelector('.button.active').getAttribute('onclick').split("'")[1];
        updateChart(activePeriod);
    }, updateInterval);
});