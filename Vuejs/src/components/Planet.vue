<script setup>
const props = defineProps({
    elements: {},
    widthScalingFactor: 1,
    heightScalingFactor: 1,
})

const elementStyles = (element) => {
    return {
        transform: `rotate(${element.direction.angle}deg)`,
        left: `${element.position.latitude * props.widthScalingFactor}px`,
        top: `${element.position.longitude * props.heightScalingFactor}px`,
    }
}

</script>

<template>
    <section class="planet">
        <div class="elements">
            <div v-for="element in elements"
                :key="element.id"
                :class="['element', element.type.toLowerCase()]"
                :style="elementStyles(element)">
            </div>
        </div>
        <div class="control">
            <button class="button" @click="$emit('turnLeft')">LEFT</button>
            <button class="button" @click="$emit('moveForward')">UP</button>
            <button class="button" @click="$emit('moveBackward')">DOWN</button>
            <button class="button" @click="$emit('turnRight')">RIGHT</button>
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
