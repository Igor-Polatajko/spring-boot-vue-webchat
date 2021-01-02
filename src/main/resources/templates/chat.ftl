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
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>

    <style>
        .messages-block {
            overflow-x: hidden;
            overflow-y: scroll;
            height: 80%;
            scrollbar-
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
    <div class="col-md-8 mx-auto bg-light h-100">
        <div class="messages-block">
            <div v-for="message in messages" class="ml-4 m-3 py-2 px-4 bg-white text-wrap text-break rounded">
                <div class="fw-bold"> {{ message.sender }} </div>
                <div class="pl-4">
                    {{ message.content }}
                </div>
            </div>
        </div>
        <div class="p-3 m-2 rounded bg-white">
            <div class="mb-3">
                <input type="text" class="form-control" id="username" placeholder="Type here...">
            </div>
            <div class="d-grid gap-2 col-6 mx-auto">
                <button type="submit" class="btn btn-primary btn-block">Send</button>
            </div>
        </div>
    </div>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            messages: [
                {
                    sender: "Somebody",
                    content: "some message"
                },
                {
                    sender: "Ihor",
                    content: "great message"
                }
            ]
        }
    })
</script>

</body>
</html>