let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    console.log("Trying to connect...");
    let socket = new SockJS("/message-websocket");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log("Connected:" + frame);
        setConnected(true)

        $.get("/global_chat/history", function (messages) {
            if (messages.length !== 0) {
                messages.forEach(function (item) {
                    showMsg(item.sender + ": " + item.text)
                })
            }
        })

        stompClient.subscribe("/topic/message", function (message) {
            let msg = JSON.parse(message.body);
            showMsg(msg.sender + ": " + msg.text);
        })
    })
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected")
}

function sendMsg() {
    let name = $("#name").val();
    let message = $("#message").val()
    showMsg(name + ": " + message);
    stompClient.send("/app/message", {}, JSON.stringify({'text': message, 'sender': name}));
}

function showMsg(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>")
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").on('click', function () {
        connect();
    })
    $("#disconnect").on('click', function () {
        disconnect();
    })
    $("#send").on('click', function () {
        sendMsg();
    })
    $("#group").on('click', function () {
        location.replace("/group_chats")
    })
})
