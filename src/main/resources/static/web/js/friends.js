document.addEventListener('DOMContentLoaded', async function() {
    const cardContainer = document.getElementById('cardContainer');
    const addButton = document.getElementById('myBtn');

    // 주소창에서 email 파라미터 값 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const userEmail = urlParams.get('email'); // 로그인한 사용자의 이메일

    async function fetchAllData(email) {
        try {
            const fileName = `/static/data/all_${email}.json`;
            const response = await fetch(fileName);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log('All Data:', data);
            return data;
        } catch (error) {
            console.error('Error fetching all data:', error);
            return null; // 에러 발생 시 null 반환
        }
    }

    function calculateTotalUsage(email, usageData) {
        const userUsageData = usageData.filter(data => data.email.trim() === email.trim());
        const totalDurationInSeconds = userUsageData.reduce((sum, data) => sum + data.duration, 0);
        return (Math.round((totalDurationInSeconds / 60) * 100) / 100).toFixed(2);
    }

    async function removeFriend(friendEmail) {
        try {
            const response = await fetch('/api/friends/remove', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: friendEmail }) // friendEmail을 JSON 객체로 전달
            });

            if (response.ok) {
                console.log(`Friend ${friendEmail} removed successfully`);
                await createFriendCards(); // 친구 카드를 새로 고침
            } else {
                console.error('Error removing friend:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    async function createFriendCards() {
        if (!userEmail) {
            console.error('Error: Missing user email in URL parameters.');
            return;
        }

        const allData = await fetchAllData(userEmail);
        console.log('All data:', allData);

        if (allData) {
            const friendsList = allData.friends.friendsList;
            const users = allData.allUsers;
            const usageData = allData.usageData;

            cardContainer.innerHTML = ''; // 기존 카드 초기화

            if (friendsList && friendsList.trim()) {
                const friendsArray = friendsList.split(',');
                friendsArray.forEach(friendEmail => {
                    const user = users.find(user => user.email.trim() === friendEmail.trim());
                    if (user) {
                        const friendName = user.name;
                        const friendPicture = user.picture;
                        const totalUsage = calculateTotalUsage(friendEmail, usageData);

                        const card = document.createElement('div');
                        card.className = 'card';

                        const avatar = document.createElement('div');
                        avatar.className = 'avatar';
                        if (friendPicture) {
                            avatar.style.backgroundImage = `url(${friendPicture})`;
                            avatar.style.backgroundSize = 'cover';
                            avatar.style.backgroundPosition = 'center';
                        }

                        const name = document.createElement('p');
                        name.textContent = friendName;

                        const usage = document.createElement('p');
                        usage.textContent = `${totalUsage} min`;

                        const removeButton = document.createElement('button');
                        removeButton.className = 'remove-button';
                        removeButton.textContent = 'x';
                        removeButton.onclick = function() {
                            if (confirm(`Are you sure you want to remove ${friendName}?`)) {
                                removeFriend(friendEmail.trim());
                            }
                        };

                        card.appendChild(avatar);
                        card.appendChild(name);
                        card.appendChild(usage);
                        card.appendChild(removeButton);

                        cardContainer.appendChild(card);
                    } else {
                        console.error(`No user found for email: ${friendEmail.trim()}`);
                    }
                });
            }

            cardContainer.appendChild(addButton); // 추가 버튼 추가
        } else {
            console.error('Error: Missing data from all.json.');
        }
    }

    // 페이지가 로드될 때마다 친구 카드 생성
    createFriendCards();

    // 새로고침 시에도 카드 생성
    window.createFriendCards = createFriendCards;
});
