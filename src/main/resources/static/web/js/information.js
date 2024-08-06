document.addEventListener('DOMContentLoaded', () => {
    fetch('/static/data/userInfo.json') // JSON 파일 경로
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 메인 사용자 정보 설정
            const avatarElement = document.querySelector('.avatar[data-type="avatar"]'); // 메인 사용자 아바타
            const nameElement = document.querySelector('.name[data-type="name"]'); // 메인 사용자 이름

            // JSON 데이터에서 사용자 정보 가져오기
            nameElement.textContent = data.name; // 이름 설정
            avatarElement.src = data.picture; // 프로필 사진 설정
            avatarElement.alt = `${data.name}'s Avatar`; // 대체 텍스트 설정
            avatarElement.style.width = '100px'; // 적절한 너비 설정
            avatarElement.style.height = '100px'; // 적절한 높이 설정
            avatarElement.style.borderRadius = '50%'; // 원형으로 만들기

            // "Me" 부분의 사용자 아바타 설정
            const meAvatarElement = document.querySelector('.user.active .dot'); // "Me" 사용자의 아바타
            meAvatarElement.src = data.picture; // "Me" 사용자 프로필 사진 설정
            meAvatarElement.alt = "Me's Avatar"; // 대체 텍스트 설정
            meAvatarElement.style.width = '30px'; // dot의 너비 설정
            meAvatarElement.style.height = '30px'; // dot의 높이 설정
            meAvatarElement.style.borderRadius = '50%'; // 원형으로 만들기
        })
        .catch(error => console.error('Error fetching user info:', error));
});
