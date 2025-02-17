<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script setup>
const url = ref('http://doc.sc2.vip/sc2-vue');

function goto() {
  window.open(url.value)
}
</script>