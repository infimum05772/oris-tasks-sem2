let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#room_code").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    if (validateRoomCode()) {
        let code = $("#room_code").val()

        console.log("Trying to connect...");
        let socket = new SockJS("/message-websocket");
        stompClient = Stomp.over(socket)
        ;
        stompClient.connect({}, function (frame) {
            console.log("Connected:" + frame);
            setConnected(true)

            $.get("/group_chats/history/" + code, function (messages) {
                if (messages.length !== 0) {
                    messages.forEach(function (item) {
                        showMsg(item)
                    })
                }
            })

            stompClient.subscribe("/topic/room-message/" + code, function (message) {
                showMsg(JSON.parse(message.body));
            })
        })
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected")
}

function sendMsg() {
    if (validateRoomCode()) {
        let code = $("#room_code").val()
        let name = $("#name").val();
        let message = $("#message").val()

        stompClient.send("/app/room-message/" + code, {}, JSON.stringify({'text': message, 'sender': name}));
    }
}

function showMsg(message) {
    $("#messages").append("<tr><td>" + message.sender + ": " + message.text + "</td></tr>")
}

function validateRoomCode() {
    let roomCode = $("#room_code");
    if (roomCode.val().length === 0) {
        roomCode.addClass("is-invalid");
        return false;
    }
    roomCode.removeClass("is-invalid");
    return true;
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
    $("#global").on('click', function () {
        location.replace("/global_chat")
    })
    $("#room_code").on('keyup', function () {
        validateRoomCode()
    }).on('keypress', function (evt) {
        if (evt.which < 48 || evt.which > 57) {
            evt.preventDefault();
        }
    });
})
