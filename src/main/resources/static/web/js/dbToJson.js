/*
 *
 *
 * db 데이터 json 파일로 바꾸는 함수들 있는 자바스크립트 파일
 *
 *
 *
 */

// 함수 호출 시 /api/data-to-json/dailyUsageStatistics POST 호출
function dataToJsonDailyUsageStatistics() {
    saveJson('dailyUsageStatistics');
}

// 함수 호출 시 /api/data-to-json/monthlyUsageStatistics POST 호출
function dataToJsonMonthlyUsageStatistics() {
    saveJson('monthlyUsageStatistics');
}

// 함수 호출 시 /api/data-to-json/usageStatistics POST 호출
function dataToJsonUsageStatistics() {
    saveJson('usageStatistics')
}

// 함수 호출 시 /api/data-to-json/usageData.json POST 호출
function dataToJsonUsageData() {
    saveJson('usageData');
}

// 함수 호출 시 /api/data-to-json/users.json POST 호출
function dataToJsonUsers() {
    saveJson('users');
}

// 함수 호출 시 /api/data-to-json/userInfo.json POST 호출
function dataToJsonUserInfo() {
    saveJson('userInfo');
}

// 호출 로직
function saveJson(filePath) {
    fetch('/api/data-to-json/' + filePath, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: ({
        })
    })
        .then(response => {
            if (response.ok) {
                alert('Succeeded!');
            } else {
                alert('Failed.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}