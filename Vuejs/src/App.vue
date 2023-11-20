<script setup>
import { ref, onMounted } from 'vue'
import Planet from './components/Planet.vue'

const url = 'ws://localhost:8080/api/websocket'

let elements = ref({})
let widthScalingFactor = ref(1)
let heightScalingFactor = ref(1)
let connection = null
let sessionId = null

onMounted(() => {
    widthScalingFactor.value = document.getElementById("app").offsetWidth / 600
    heightScalingFactor.value = document.getElementById("app").offsetHeight / 500
    document.addEventListener('keydown', onKeyPress)
    sessionId = getSessionId()
    connect()
})

const getSessionId = () => {
    const sessionId = localStorage.getItem("session-id") || crypto.randomUUID()
    localStorage.setItem("session-id", sessionId)
    return sessionId
}

const connect = () => {
    if (connection != null) { return }
    connection = new WebSocket(url);
    connection.addEventListener('open', () => sendCommand('START'));
    connection.addEventListener('message', (message) => onMessageReceived(message));
}

const sendCommand = (command) => {
    connection.send(JSON.stringify({
        elementId: sessionId,
        command: command
    }))
}

const onMessageReceived = (message) => {
    const element = JSON.parse(message.data);
    elements.value[element.id] = element;
}

const onKeyPress = (event) => {
    if (event.key === 'ArrowLeft') { turnLeft(); }
    if (event.key === 'ArrowRight') { turnRight(); }
    if (event.key === 'ArrowUp') { moveForward(); }
    if (event.key === 'ArrowDown') { moveBackward(); }
}

const turnLeft = () => { sendCommand('TURN_LEFT') }
const turnRight = () => { sendCommand('TURN_RIGHT') }
const moveForward = () => { sendCommand('MOVE_FORWARD') }
const moveBackward = () => { sendCommand('MOVE_BACKWARD') }
</script>

<template>
    <Planet :elements="elements"
            :widthScalingFactor="widthScalingFactor"
            :heightScalingFactor="heightScalingFactor"
            @turn-left="turnLeft"
            @turn-right="turnRight"
            @move-forward="moveForward"
            @move-backward="moveBackward"
    />
</template>
