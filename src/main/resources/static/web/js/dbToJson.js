/*
 *
 *
 * db 데이터 json 파일로 바꾸는 함수들 있는 자바스크립트 파일
 *
 *
 *
 */

function dataToJsonAll() {
    saveJson('all');
}

function deleteAll() {
    deleteJson('all');
}

// 함수 호출 시 /api/data-to-json/friends POST 호출
function dataToJsonFriends() {
    saveJson('friends');
}

// 함수 호출 시 /api/data-to-json/data POST 호출
function dataToJsonData() {
    saveJson('data');
}

// 함수 호출 시 /api/data-to-json/screenTime POST 호출
function dataToJsonScreenTime() {
    saveJson('screenTime');
}

// 함수 호출 시 /api/data-to-json/someTime POST 호출
function dataToJsonSomeTime() {
    saveJson('someTime');
}

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

function deleteUserInfo() {
    deleteJson('userInfo');
}

function deleteUsers() {
    deleteJson('users');
}

function deleteUsageStatistics() {
    deleteJson('usageStatistics');
}

function deleteUsageData() {
    deleteJson('usageData');
}

function deleteSomeTime() {
    deleteJson('someTime');
}

function deleteScreenTime() {
    deleteJson('screenTime');
}

function deleteData() {
    deleteJson('data');
}

function deleteFriends() {
    deleteJson('friends');
}

function deleteDailyAverageUsage() {
    deleteJson('dailyAverageUsage');
}

function deleteMonthlyAverageUsage() {
    deleteJson('monthlyAverageUsage');
}

// 함수 controller 호출 *저장
function saveJson(filePath) {
    fetch('/api/data-to-json/' + filePath, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: ({
        })
    })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 함수 controller 호출 *삭제
function deleteJson(filePath) {
    fetch('/api/delete-json/' + filePath, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: ({
        })
    })
        .catch(error => {
            console.error('Error:', error);
        });
}