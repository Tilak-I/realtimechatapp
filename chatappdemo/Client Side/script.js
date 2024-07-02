let stompClient = null;

function connect() {
    const socket = new SockJS('/ws'); 

    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/chatroom', function (message) {
            showMessage(JSON.parse(message.body));
        });
    }, function (error) {
        console.error('Connection error: ' + error);
    });
}

function showMessage(message) {
    const messageElement = document.createElement('div');
    messageElement.textContent = `${message.sender}: ${message.content}`;
    document.getElementById('messages').appendChild(messageElement);
}

function sendMessage() {
    const username = document.getElementById('username').value.trim();
    const messageContent = document.getElementById('message').value.trim();

    if (messageContent !== "" && username !== "") {
        if (stompClient && stompClient.connected) {
            const message = {
                sender: username,
                content: messageContent
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
            document.getElementById('message').value = "";
        }
    } else if (messageContent === "" && username === "") {
        alert("You have not entered a message or a username. Please do so.");
    } else if (username === "") {
        alert("You have not entered a username. Please do so.");
    } else if (messageContent === "") {
        alert("You have not entered a message. Please do so.");
    }
}

document.getElementById('send-button').addEventListener('click', sendMessage);

window.onload = connect;
