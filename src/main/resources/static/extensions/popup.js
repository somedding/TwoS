document.addEventListener('DOMContentLoaded', () => {
  updateUsageData();
  setInterval(updateUsageData, 1000);

  document.getElementById('reset').addEventListener('click', () => {
    chrome.runtime.sendMessage({ action: 'reset' }, response => {
      if (response.status === 'reset') {
        updateUsageData();
      }
    });
  });
});

function updateUsageData() {
  chrome.storage.local.get(['usageData'], result => {
    let usageData = result.usageData || {};
    let tbody = document.getElementById('usage-table').getElementsByTagName('tbody')[0];
    tbody.innerHTML = ''; 

    for (let domain in usageData) {
      let timeSpent = formatTime(usageData[domain]);
      let row = tbody.insertRow();
      let cell1 = row.insertCell(0);
      let cell2 = row.insertCell(1);
      cell1.textContent = domain;
      cell2.textContent = timeSpent;
    }
  });
}

function formatTime(seconds) {
  let hours = Math.floor(seconds / 3600);
  let minutes = Math.floor((seconds % 3600) / 60);
  seconds = Math.floor(seconds % 60);
  return `${hours}h ${minutes}m ${seconds}s`;
}