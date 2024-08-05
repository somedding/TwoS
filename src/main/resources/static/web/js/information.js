// information.js
document.addEventListener('DOMContentLoaded', () => {
    fetch('/static/data/userInfo.json') // JSON 파일 경로
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const avatarElement = document.querySelector('[data-type="avatar"]');
            const nameElement = document.querySelector('[data-type="name"]');

            // 이름과 프로필 사진 설정
            nameElement.textContent = data.name; // 이름 설정
            avatarElement.src = data.picture; // 프로필 사진 설정
            avatarElement.alt = `${data.name}'s Avatar`; // 대체 텍스트 설정
            avatarElement.style.width = '100px'; // 적절한 너비 설정 (예시)
            avatarElement.style.height = '100px'; // 적절한 높이 설정 (예시)
            avatarElement.style.borderRadius = '50%'; // 원형으로 만들기
        })
        .catch(error => console.error('Error fetching user info:', error));
});
