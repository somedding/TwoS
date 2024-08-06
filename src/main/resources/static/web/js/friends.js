document.addEventListener('DOMContentLoaded', async function() {
    const cardContainer = document.getElementById('cardContainer');
    const addButton = document.getElementById('myBtn');
    const userEmail = 'leeys2413@gmail.com'; // 실제 로그인한 사용자의 이메일을 얻는 로직으로 대체

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
        console.log(`Filtered usage data for ${email}:`, userUsageData);

        const totalDurationInSeconds = userUsageData.reduce((sum, data) => sum + data.duration, 0);
        console.log(`Total duration for ${email} in seconds: ${totalDurationInSeconds}`);

        const totalDurationInMinutes = (Math.round((totalDurationInSeconds / 60) * 100) / 100).toFixed(2);
        console.log(`Total duration for ${email} in minutes: ${totalDurationInMinutes}`);

        return totalDurationInMinutes;
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
                await createFriendCards();
            } else {
                console.error('Error removing friend:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }


    async function createFriendCards() {
        const allData = await fetchAllData(userEmail);
        console.log('All data:', allData);

        if (allData) {
            const friendsList = allData.friends.friendsList;
            const users = allData.allUsers;
            const usageData = allData.usageData;

            cardContainer.innerHTML = '';

            if (friendsList && friendsList.trim()) {
                const friendsArray = friendsList.split(',');
                console.log('Friends array:', friendsArray);

                friendsArray.forEach(friendEmail => {
                    console.log(`Processing friend with email: ${friendEmail.trim()}`);

                    const user = users.find(user => user.email.trim() === friendEmail.trim());
                    console.log('User found:', user);

                    if (!user) {
                        console.error(`No user found for email: ${friendEmail.trim()}`);
                        return;
                    }

                    const friendName = user.name;
                    const friendPicture = user.picture;

                    const totalUsage = calculateTotalUsage(friendEmail, usageData);
                    console.log(`Total usage for ${friendEmail.trim()}: ${totalUsage}`);

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
                });
            }

            cardContainer.appendChild(addButton);
        } else {
            console.error('Error: Missing data from all.json.');
        }
    }

    createFriendCards();

    window.createFriendCards = createFriendCards;
});