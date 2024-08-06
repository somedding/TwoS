// script.js
document.addEventListener('DOMContentLoaded', function() {
    // 첫 번째 차트
    const ctx1 = document.getElementById('myChart1').getContext('2d');
    const data1 = {
        labels: chartData1.labels,
        datasets: [{
            label: '첫 번째 차트',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 2,
            data: chartData1.values,
            fill: false,
        }]
    };
    new Chart(ctx1, {
        type: 'line',
        data: data1,
        options: {
            scales: {
                x: {
                    grid: {
                        display: false  // x축의 격자 선 숨기기
                    }
                },
                y: {
                    grid: {
                        display: false  // y축의 격자 선 숨기기
                    },
                    beginAtZero: true
                }
            },
            elements: {
                line: {
                    tension: 0  // 선의 곡선을 없애고 직선으로 표시
                }
            }
        }
    });

    // 두 번째 차트
    const ctx2 = document.getElementById('myChart2').getContext('2d');
    const data2 = {
        labels: chartData2.labels,
        datasets: [{
            label: '두 번째 차트',
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 2,
            data: chartData2.values,
            fill: false,
        }]
    };
    new Chart(ctx2, {
        type: 'line',
        data: data2,
        options: {
            scales: {
                x: {
                    grid: {
                        display: false
                    }
                },
                y: {
                    grid: {
                        display: false
                    },
                    beginAtZero: true
                }
            },
            elements: {
                line: {
                    tension: 0
                }
            }
        }
    });

    // 세 번째 차트
    const ctx3 = document.getElementById('myChart3').getContext('2d');
    const data3 = {
        labels: chartData3.labels,
        datasets: [{
            label: '세 번째 차트',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
            data: chartData3.values,
            fill: false,
        }]
    };
    new Chart(ctx3, {
        type: 'line',
        data: data3,
        options: {
            scales: {
                x: {
                    grid: {
                        display: false
                    }
                },
                y: {
                    grid: {
                        display: false
                    },
                    beginAtZero: true
                }
            },
            elements: {
                line: {
                    tension: 0
                }
            }
        }
    });

    // 버튼 클릭 이벤트 처리
    document.getElementById('showChart1').addEventListener('click', function() {
        document.getElementById('chart1Container').style.display = 'block';
        document.getElementById('chart2Container').style.display = 'none';
        document.getElementById('chart3Container').style.display = 'none';
    });

    document.getElementById('showChart2').addEventListener('click', function() {
        document.getElementById('chart2Container').style.display = 'block';
        document.getElementById('chart1Container').style.display = 'none';
        document.getElementById('chart3Container').style.display = 'none';
    });

    document.getElementById('showChart3').addEventListener('click', function() {
        document.getElementById('chart3Container').style.display = 'block';
        document.getElementById('chart1Container').style.display = 'none';
        document.getElementById('chart2Container').style.display = 'none';
    });
});

