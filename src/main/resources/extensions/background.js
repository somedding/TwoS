let usageData = {};
let activeTabId = null;
let activeTabStartTime = null;

// 로그인 후 액세스 토큰을 저장하는 함수
function saveAccessToken(token) {
  chrome.storage.local.set({ accessToken: token }, () => {
    console.log('Access Token 저장 완료:', token);
  });
}

// 로그인 성공 후 호출
async function onLoginSuccess(token) {
  saveAccessToken(token);
  // 이후 서버에 데이터 전송을 위한 작업 수행
}

// 탭 업데이트 및 전환 처리
chrome.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
  if (changeInfo.status === 'complete' && tab.active) {
    chrome.tabs.query({ active: true, currentWindow: true }, tabs => {
      if (tabs.length > 0) {
        switchTab(tabs[0].id, tabs[0].url);
      }
    });
  }
});

chrome.tabs.onActivated.addListener(activeInfo => {
  chrome.tabs.get(activeInfo.tabId, tab => {
    switchTab(activeInfo.tabId, tab.url);
  });
});

// Focus 상태에 따른 탭 전환 처리
chrome.windows.onFocusChanged.addListener(windowId => {
  if (windowId === chrome.windows.WINDOW_ID_NONE) {
    handleInactive();
  } else {
    chrome.tabs.query({ active: true, windowId: windowId }, tabs => {
      if (tabs.length > 0) {
        switchTab(tabs[0].id, tabs[0].url);
      }
    });
  }
});

function switchTab(tabId, url) {
  if (activeTabId !== null) {
    recordTime();
  }
  activeTabId = tabId;
  activeTabStartTime = Date.now();
}

function recordTime() {
  if (activeTabId === null || activeTabStartTime === null) return;

  let duration = (Date.now() - activeTabStartTime) / 1000;
  chrome.tabs.get(activeTabId, tab => {
    if (!tab.url) return;
    let domain = (new URL(tab.url)).hostname;
    if (!usageData[domain]) {
      usageData[domain] = 0;
    }
    usageData[domain] += duration;
    chrome.storage.local.set({ usageData: usageData });
  });

  activeTabStartTime = Date.now();
}

function handleInactive() {
  if (activeTabId !== null) {
    recordTime();
  }
  activeTabId = null;
}

// 일정 간격으로 기록
setInterval(() => {
  if (activeTabId !== null) {
    recordTime();
  }
}, 1000);

// 데이터 초기화 및 서버 전송
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === 'reset') {
    usageData = {};
    chrome.storage.local.set({ usageData: {} }, () => {
      sendResponse({ status: 'reset' });
    });
    return true;
  }
});

// 사용자의 활동 시간을 서버에 전송하는 함수
async function sendUsageDataToServer() {
  const userInfo = await getUserInfo();
  const accessToken = await getAccessToken(); // 액세스 토큰 가져오기

  const postData = {
    userId: userInfo.id,
    usageData: usageData,
    updatedAt: new Date().toISOString()
  };

  try {
    const response = await fetch('http://twos.gay:808//api/usage/upload', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
      },
      body: JSON.stringify(postData)
    });

    if (!response.ok) {
      throw new Error(`서버 응답 오류: ${response.status}`);
    }

    const result = await response.json();
    console.log('서버 응답:', result);
    // 성공 알림
    chrome.notifications.create({
      type: 'basic',
      iconUrl: 'icon.png',
      title: '데이터 전송 성공',
      message: '사용자 활동 데이터가 성공적으로 전송되었습니다.'
    });
  } catch (error) {
    console.error('데이터 전송 오류:', error);
    // 오류 알림
    chrome.notifications.create({
      type: 'basic',
      iconUrl: 'icon.png',
      title: '데이터 전송 실패',
      message: '사용자 활동 데이터 전송에 실패했습니다.'
    });
  }
}

// 액세스 토큰을 가져오는 함수
async function getAccessToken() {
  return new Promise((resolve, reject) => {
    chrome.storage.local.get(['accessToken'], result => {
      if (chrome.runtime.lastError) {
        return reject(chrome.runtime.lastError);
      }
      resolve(result.accessToken);
    });
  });
}

// 사용자 정보를 가져오는 함수
async function getUserInfo() {
  return new Promise((resolve, reject) => {
    chrome.storage.local.get(['userId', 'userName'], result => { // 수정된 부분
      if (chrome.runtime.lastError) {
        return reject(chrome.runtime.lastError);
      }
      resolve({
        id: result.userId,
        name: result.userName
      });
    });
  });
}

// 테스트용 1초간격 데이터 전송
// setInterval(sendUsageDataToServer, 1000)
// 서버에 데이터 전송 간격 설정
setInterval(sendUsageDataToServer, 60 * 1000);