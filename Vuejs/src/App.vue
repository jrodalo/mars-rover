<script setup>
import { ref, onMounted } from 'vue'

const url = 'ws://localhost:8080/api/websocket'
const elements = ref({})

var connection = null
var sessionId = null
var widthScalingFactor = 1
var heightScalingFactor = 1

onMounted(() => {
    document.addEventListener('keydown', onKeyPress)
    widthScalingFactor = document.getElementById("app").offsetWidth / 600
    heightScalingFactor = document.getElementById("app").offsetHeight / 500
    sessionId = getSessionId()
    connect()
})

const connect = () => {
    if (connection != null) { return }
    connection = new WebSocket(url);
    connection.addEventListener('open', () => sendCommand('START'));
    connection.addEventListener('message', (message) => onMessageReceived(message));
}

const getSessionId = () => {
    const sessionId = localStorage.getItem("session-id") || crypto.randomUUID()
    localStorage.setItem("session-id", sessionId)
    return sessionId
}

const onKeyPress = (event) => {
    if (event.key === 'ArrowLeft') turnLeft();
    if (event.key === 'ArrowUp') moveForward();
    if (event.key === 'ArrowRight') turnRight();
    if (event.key === 'ArrowDown') moveBackward();
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

const turnLeft = () => { sendCommand('TURN_LEFT') }
const turnRight = () => { sendCommand('TURN_RIGHT') }
const moveForward = () => { sendCommand('MOVE_FORWARD') }
const moveBackward = () => { sendCommand('MOVE_BACKWARD') }

</script>

<template>
    <section class="planet">
        <div v-for="element in elements" :key="element.id" :class="['element', element.type.toLowerCase()]" :style="{
            transform: 'rotate(' + element.direction.angle + 'deg)',
            left: element.position.latitude * widthScalingFactor + 'px',
            top: element.position.longitude * heightScalingFactor + 'px',
        }">
        </div>
        <div class="control">
            <button class="button" @click="turnLeft">LEFT</button>
            <button class="button" @click="moveForward">UP</button>
            <button class="button" @click="moveBackward">DOWN</button>
            <button class="button" @click="turnRight">RIGHT</button>
        </div>
    </section>
</template>

<style scoped>
.planet {
    width: 100%;
    height: 100%;
    position: relative;
    background: #BC523F;
    border-radius: 10px;
    display: flex;
}

.control {
    background: rgba(0, 0, 0, .5);
    padding: 1rem;
    border-radius: 5px;
    display: flex;
    margin: 20px auto;
    align-self: end;
    gap: 1rem;
    z-index: 1;
}

.button {
    color: #fff;
    border-radius: 8px;
    border: 1px solid transparent;
    padding: 0.6em 1.2em;
    font-size: 1em;
    font-weight: 500;
    font-family: inherit;
    background-color: #1a1a1a;
    transition: border-color 0.25s;
}

.button:hover {
    border-color: #a39494;
}

.element {
    position: absolute;
    transition: 300ms ease-out all;
}

.rock {
    background: url("rock.svg");
    width: 20px;
    height: 20px;
}

.rover {
    background: url("rover.svg");
    width: 50px;
    height: 50px;
}
</style>
