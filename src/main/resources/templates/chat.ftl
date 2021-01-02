<!doctype html>
<html lang="en" class="h-100">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.0/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"
            integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g=="
            crossorigin="anonymous"></script>

    <style>
        .messages-block {
            overflow-x: hidden;
            overflow-y: scroll;
            height: 80%;
        }

        .participants-list {
            right: 0;
        }

        ::-webkit-scrollbar {
            width: 0;
            background: transparent;
        }

        [v-cloak] {
            visibility: hidden
        }
    </style>
</head>
<body class="h-100">
<div id="app" class="h-100" v-cloak>
    <div v-if="!showParticipants" class="position-fixed m-4 participants-list">
        <div @click="toggleShowParticipants" class="btn btn-primary btn-sm"><<</div>
    </div>
    <div v-if="showParticipants"
         class="position-fixed rounded m-4 bg-info p-4 col-md-4 overflow-scroll participants-list">
        <div @click="toggleShowParticipants" class="btn btn-primary btn-sm mb-2">>></div>
        <div class="fw-bold">Online:</div>
        <ul v-for="participant in participants">
            <li>{{ participant.username }}</li>
        </ul>
    </div>
    <div class="col-md-10 mx-auto bg-light h-100">
        <div class="messages-block">
            <div v-for="message in messages" class="m-3 py-2 px-4 bg-white text-wrap text-break rounded">
                <div class="fw-bold"> {{ message.sender }}</div>
                <div>
                    {{ message.content }}
                </div>
            </div>
        </div>
        <div class="p-3 m-2 rounded bg-white">
            <div class="mb-3">
                <input @keyup.enter="sendMessage" v-model="inputMessage" type="text" class="form-control" id="username"
                       placeholder="Type here...">
            </div>
            <div class="d-grid gap-2 col-6 mx-auto">
                <div @click="sendMessage" class="btn btn-primary btn-lg">Send</div>
            </div>
        </div>
    </div>
</div>

<script>

    let stompClient;

    new Vue({
        el: '#app',
        data: {
            messages: [],
            participants: [
                {
                    username: "Ihor",
                },
                {
                    username: "Somebody"
                }
            ],
            showParticipants: false,
            inputMessage: ''
        },
        mounted: function () {
            const socket = new SockJS('/chat-messaging');
            stompClient = Stomp.over(socket);

            let vm = this;

            stompClient.connect({}, function (frame) {
                console.log("connected: " + frame);
                stompClient.subscribe('/chat/messages', function (response) {
                    vm.messages.push(JSON.parse(response.body));
                });
            });
        },
        methods: {
            sendMessage: function () {

                if (!this.inputMessage) {
                    return;
                }

                stompClient.send("/app/message", {}, JSON.stringify({'content': this.inputMessage}));
                this.inputMessage = '';
            },
            toggleShowParticipants: function () {
                this.showParticipants = !this.showParticipants;
            }
        }
    })
</script>

</body>
</html>